/**
 * 20 oct. 2015
 * QCM V1.0
 * fr.eni_ecole.qcm.dal
 */
package fr.eni_ecole.qcm.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import fr.eni_ecole.qcm.bean.Inscription;
import fr.eni_ecole.qcm.bean.Test;
import fr.eni_ecole.qcm.bean.Utilisateur;
import fr.eni_ecole.qcm.tool.ManipDate;
import fr.eni_ecole.qcm.util.AccesBase;

/**
 * @author d1408davidj
 * 20 oct. 2015
 */
public class DALInscription {

	/**
	 * Méthode en charge de récupérer l'inscription d'un candidat à un test 
	 * 28 oct. 2015
	 * @param test Test passé
	 * @param user Candidat concerné
	 * @return L'inscription
	 * @throws SQLException
	 */
	public static Inscription getInscriptionByUserTest(Test test,Utilisateur user) throws SQLException{
		Connection cnx = null;
		PreparedStatement cmd = null;
		Inscription inscription = null;
		String sql ="SELECT * FROM INSCRIPTION WHERE idUtilisateur=? AND idTest=?";
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, user.getId());
			cmd.setInt(2, test.getId());
			ResultSet res = cmd.executeQuery();
			
			if(res.next()){
				Timestamp debut = res.getTimestamp(("date_debut"));
				if(res.wasNull())
					debut = null;
				Timestamp fin = res.getTimestamp(("date_fin"));
				if(res.wasNull())
					fin = null;
				
				inscription = new Inscription(res.getInt("idInscription"), test, user,
						debut==null ? null : df.format(debut),
						fin == null ? null : df.format(fin),
						res.getInt("temps_restant"));
			}
			
		} finally{
			if(cmd!=null)cmd.close();
			if(cnx != null)cnx.close();
		}
		
		return inscription;
	}
	
	/**
	 * Méthode en charge d'ajouter une inscription 
	 * 29 oct. 2015
	 * @param idTest
	 * @param dateDebut
	 * @param dateFin
	 * @param idUtilisateur
	 * @param duree
	 * @return
	 * @throws SQLException
	 */
	public static boolean ajouter(int idTest, String dateDebut, String dateFin, int idUtilisateur, int duree) throws SQLException{
		Connection cnx = null;
		PreparedStatement cmd = null;
		Statement st = null;
		String sql = "INSERT INTO INSCRIPTION VALUES(?,?,?,?,?)";
		try{
			cnx = AccesBase.getConnection();
			
			cnx.setAutoCommit(false);			
			
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, idTest);
			cmd.setString(2, dateDebut);
			cmd.setString(3, dateFin);
			cmd.setInt(4, idUtilisateur);
			cmd.setInt(5, duree);
			cmd.executeUpdate();
						
			cnx.commit();
			
		}catch(SQLException sqle){
			if(cnx != null)
				cnx.rollback();
			throw sqle;
		}finally{
			if(st != null) st.close();
			if(cmd != null)cmd.close();
			if(cnx!=null)cnx.close();
		}
		return true;
		
		
		
		
	}
	
	/**
	 * Méthode en charge de demarrer un test en passant par sa date de début d'inscription 
	 * 29 oct. 2015
	 * @param inscription Inscription concernée
	 * @throws SQLException
	 */
	public static void demarrerTest(Inscription inscription) throws SQLException{
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "UPDATE INSCRIPTION SET date_debut = ? "
					+ "WHERE idInscription = ? AND idTest = ? AND idUtilisateur = ? ";
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setDate(1,ManipDate.dateUtilVersSQL(new java.util.Date()));
			cmd.setInt(2, inscription.getIdInscription());
			cmd.setInt(3, inscription.getTest().getId());
			cmd.setInt(4, inscription.getUtilisateur().getId());
			cmd.executeUpdate();
					
		}finally{
			if(cmd != null) cmd.close();
			if(cnx != null) cnx.close();
		}
	}

	/**
	 * Méthode en charge de finir un test en passant sa date de fin d'inscripion
	 * 29 oct. 2015
	 * @param inscription inscription concernée
	 * @throws SQLException 
	 */
	public static void finirTest(Inscription inscription) throws SQLException{
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "UPDATE INSCRIPTION SET date_fin = ? "
					+ "WHERE idInscription = ? AND idTest = ? AND idUtilisateur = ? ";
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setDate(1,ManipDate.dateUtilVersSQL(new java.util.Date()));
			cmd.setInt(2, inscription.getIdInscription());
			cmd.setInt(3, inscription.getTest().getId());
			cmd.setInt(4, inscription.getUtilisateur().getId());
			cmd.executeUpdate();
					
		}finally{
			if(cmd != null) cmd.close();
			if(cnx != null) cnx.close();
		}
	}

	/**
	 * Méthode en charge de récupérer le nombre de bonne réponse pour ce test à la date d'inscription 
	 * 29 oct. 2015
	 * @param inscription Inscription concerné
	 * @return Le nombre de bonne réponse
	 * @throws SQLException 
	 */
	public static int getNbBonnesReponse(Inscription inscription) throws SQLException {
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "SELECT COUNT(*) as BONNE_REPONSE FROM REPOND_A ra "
						+"INNER JOIN INSCRIPTION i ON i.idInscription = ra.idInscription "
						+"INNER JOIN REPONSE r ON r.idReponse = ra.idReponse "
						+"WHERE ra.idInscription = ? AND i.idTest = ? AND ra.idUtilisateur=? "
						+ "AND bonne_reponse=1";		
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, inscription.getIdInscription());
			cmd.setInt(2, inscription.getTest().getId());
			cmd.setInt(3, inscription.getUtilisateur().getId());
			ResultSet rs =  cmd.executeQuery();

			if(rs.next()){
				return rs.getInt("BONNE_REPONSE");
			}
			else{
				return 0;
			}
			
		}finally{
			if(cmd != null) cmd.close();
			if(cnx != null) cnx.close();
		}
	}


	/**
	 * Méthode en charge de récupérer le nombre total de bonne réponse pour le test 
	 * 29 oct. 2015
	 * @param inscription Inscription concerné
	 * @return Le nombre  total de bonnes réponses
	 * @throws SQLException 
	 */
	public static int getNbTotalBonneReponse(Inscription inscription) throws SQLException {
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "SELECT COUNT(*) as TOTAL FROM REPONSE r "
					+"INNER JOIN TIRAGE t ON t.idQuestion = r.idQuestion "
					+"INNER JOIN INSCRIPTION i ON i.idInscription = t.idInscription "
					+"WHERE i.idInscription = ? AND i.idTest = ? AND i.idUtilisateur=? AND bonne_reponse=1";		
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, inscription.getIdInscription());
			cmd.setInt(2, inscription.getTest().getId());
			cmd.setInt(3, inscription.getUtilisateur().getId());
			ResultSet rs =  cmd.executeQuery();

			if(rs.next()){
				return rs.getInt("TOTAL");
			}
			else{
				return 0;
			}
			
		}finally{
			if(cmd != null) cmd.close();
			if(cnx != null) cnx.close();
		}
	}
	
	/**
	 * Méthode en charge de récupérer le nombre total de question par test 
	 * 29 oct. 2015
	 * @param inscription Inscription concerné
	 * @return Le nombre total de question
	 * @throws SQLException 
	 */
	public static int getNbTotalQuestion(Inscription inscription) throws SQLException{
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "SELECT COUNT(*) as TOTAL FROM TIRAGE t "
					+"INNER JOIN INSCRIPTION i ON i.idInscription = t.idInscription AND i.idTest = t.idTest "
					+"AND i.idUtilisateur = t.idUtilisateur "
					+"WHERE i.idInscription = ? AND i.idTest = ? AND i.idUtilisateur=? ";		
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, inscription.getIdInscription());
			cmd.setInt(2, inscription.getTest().getId());
			cmd.setInt(3, inscription.getUtilisateur().getId());
			ResultSet rs =  cmd.executeQuery();

			if(rs.next()){
				return rs.getInt("TOTAL");
			}
			else{
				return 0;
			}
			
		}finally{
			if(cmd != null) cmd.close();
			if(cnx != null) cnx.close();
		}
	}
	
	
}

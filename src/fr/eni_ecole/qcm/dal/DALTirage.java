/**
 * 
 */
package fr.eni_ecole.qcm.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni_ecole.qcm.bean.Question;
import fr.eni_ecole.qcm.bean.Tirage;
import fr.eni_ecole.qcm.util.AccesBase;

/**
 * @author Jordan
 *
 */
public class DALTirage {
	
	/**
	 * Méthode en charge de récupérer la question en fonction du tirage
	 * @param tirage Tirage en cours
	 * @return Retourne la question concernée
	 * @throws SQLException 
	 */
	public static Question getQuestionByTirage(Tirage tirage) throws SQLException {
		Question question = null;
		Connection cnx = null;
		PreparedStatement cmd =null;
		String sql = "SELECT q.* FROM QUESTION q "
				+ "INNER JOIN TIRAGE t ON t.idQuestion = q.idQuestion "
				+ "INNER JOIN INSCRIPTION  i ON i.idTest = t.idTest AND i.idUtilisateur = t.idUtilisateur "
				+ "AND i.idInscription = t.idInscription "
				+ "WHERE i.idTest = ? AND i.idUtilisateur = ? AND i.idInscription = ?"; 
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, tirage.getTest().getId());
			cmd.setInt(2, tirage.getUtilisateur().getId());
			cmd.setInt(3,tirage.getInscription().getIdInscription());
			ResultSet res = cmd.executeQuery();
			
			if(res.next()){
				question = new Question(res.getInt("idQuestion"), res.getString("enonce"),
						res.getBoolean("typeReponse"), res.getString("image"));
			}
			
		}finally{
			if(cmd!=null)cmd.close();
			if(cnx!=null)cnx.close();
		}
				
		return question;
	}
	
	/**
	 * Méthode en charge de marquer une question
	 * @param tirage Tirage en cours
	 * @throws SQLException 
	 */
	public static void marquerQuestion(Tirage tirage) throws SQLException{
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql= "UPDATE TIRAGE SET marquee = 1 WHERE idUtilisateur = ? AND idQuestion = ? "
				+ "AND idTest = ? and idInscription = ?";
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, tirage.getUtilisateur().getId());
			cmd.setInt(2, tirage.getQuestion().getIdQuestion());
			cmd.setInt(3, tirage.getTest().getId());
			cmd.setInt(4, tirage.getInscription().getIdInscription());
			cmd.executeUpdate();			
		}finally{
			if(cmd!=null)cmd.close();
			if(cnx != null)cnx.close();
		}
	}
	
	/**
	 * Méthode en charge de gérer la question en cours 
	 * @param tirage Tirage en cours
	 * @throws SQLException 
	 */
	public static void questionEnCours(Tirage tirage) throws SQLException{
		Connection cnx = null;
		PreparedStatement cmd = null;
		PreparedStatement cmd1 = null;
		
		String sql= "UPDATE TIRAGE SET en_cours = 0 WHERE idUtilisateur = ? AND idQuestion = ? "
				+ "AND idTest = ? and idInscription = ? AND en_cours = 1";
		
		try{
			cnx = AccesBase.getConnection();
			cnx.setAutoCommit(false);
			
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, tirage.getUtilisateur().getId());
			cmd.setInt(2, tirage.getQuestion().getIdQuestion());
			cmd.setInt(3, tirage.getTest().getId());
			cmd.setInt(4, tirage.getInscription().getIdInscription());
			cmd.executeUpdate();
			
			sql= "UPDATE TIRAGE SET en_cours = 1 WHERE idUtilisateur = ? AND idQuestion = ? "
					+ "AND idTest = ? and idInscription = ?";
			cmd1 = cnx.prepareStatement(sql);
			cmd1.setInt(1, tirage.getUtilisateur().getId());
			cmd1.setInt(2, tirage.getQuestion().getIdQuestion());
			cmd1.setInt(3, tirage.getTest().getId());
			cmd1.setInt(4, tirage.getInscription().getIdInscription());
			cmd1.executeUpdate();
			
			cmd.executeUpdate();			
		}catch(SQLException sqle){
			if(cnx != null)
				cnx.rollback();
			throw sqle;
		}finally{
			if(cmd!=null)cmd.close();
			if(cmd1!=null)cmd1.close();
			if(cnx != null)cnx.close();
		}
	}

	/**
	 * Méthode en charge d'ajouter un tirage à la BD 
	 * 28 oct. 2015
	 * @param tirage Tirage à ajouter
	 * @throws SQLException 
	 */
	public static void ajouter(Tirage tirage) throws SQLException {
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "INSERT INTO TIRAGE(idQuestion,idInscription,idUtilisateur,idTest,en_cours,marquee) VALUES (?,?,?,?,0,0)";
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, tirage.getQuestion().getIdQuestion());
			cmd.setInt(2, tirage.getInscription().getIdInscription());
			cmd.setInt(3, tirage.getUtilisateur().getId());
			cmd.setInt(4, tirage.getTest().getId());
			cmd.executeUpdate();			
		}finally{
			if(cmd!=null)cmd.close();
			if(cnx != null)cnx.close();
		}
	}

	/**
	 * Méthode en charge de récupérer le nombre de bonne réponse pour cet utilisateur
	 * 29 oct. 2015
	 * @param t Le tirage concerné
	 * @return Le nombre de bonne réponse
	 * @throws SQLException 
	 */
	public static int getNbBonneReponseUtilisateur(Tirage t) throws SQLException {
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "SELECT COUNT(*) as TOTAL FROM REPOND_A ra "
					+"INNER JOIN REPONSE r ON r.idReponse = ra.idReponse "
					+"INNER JOIN TIRAGE t ON t.idQuestion = ra.idQuestion "
					+"INNER JOIN INSCRIPTION i ON i.idInscription = t.idInscription "
					+"WHERE i.idInscription = ? AND i.idTest = ? AND i.idUtilisateur=? "
					+"AND bonne_reponse=1 AND ra.idQuestion = ?";	;		
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, t.getInscription().getIdInscription());
			cmd.setInt(2, t.getTest().getId());
			cmd.setInt(3, t.getUtilisateur().getId());
			cmd.setInt(4, t.getQuestion().getIdQuestion());
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
	 * Méthode en charge de	récupérer le nombre de bonne réponse pour ce tirage 
	 * 29 oct. 2015
	 * @param t Tirage concerné
	 * @return Le nombre de bonne réponse
	 * @throws SQLException
	 */
	public static int getNbBonneReponseTirage(Tirage t) throws SQLException {
		
			Connection cnx = null;
			PreparedStatement cmd = null;
			String sql = "SELECT COUNT(*) as TOTAL FROM REPONSE r "
						+"INNER JOIN TIRAGE t ON t.idQuestion = r.idQuestion "
						+"INNER JOIN INSCRIPTION i ON i.idInscription = t.idInscription "
						+"WHERE i.idInscription = ? AND i.idTest = ? AND i.idUtilisateur= ? "
						+"AND bonne_reponse=1 AND r.idQuestion = ?";			
			
			try{
				cnx = AccesBase.getConnection();
				cmd = cnx.prepareStatement(sql);
				cmd.setInt(1, t.getInscription().getIdInscription());
				cmd.setInt(2, t.getTest().getId());
				cmd.setInt(3, t.getUtilisateur().getId());
				cmd.setInt(4, t.getQuestion().getIdQuestion());
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

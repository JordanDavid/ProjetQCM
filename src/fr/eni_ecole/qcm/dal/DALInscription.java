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
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import fr.eni_ecole.qcm.bean.Inscription;
import fr.eni_ecole.qcm.bean.Test;
import fr.eni_ecole.qcm.bean.Utilisateur;
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
				inscription = new Inscription(res.getInt("idInscription"), test, 
						user, df.format(res.getTimestamp(("date_debut"))), df.format(res.getTimestamp(("date_fin"))),
						res.getInt("temps_restant"));
			}
			
		} finally{
			if(cmd!=null)cmd.close();
			if(cnx != null)cnx.close();
		}
		
		return inscription;
	}
	
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
	
}

/**
 * 22 oct. 2015
 * QCM V1.0
 * fr.eni_ecole.qcm.dal
 */
package fr.eni_ecole.qcm.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.eni_ecole.qcm.bean.Question;
import fr.eni_ecole.qcm.bean.Reponse;
import fr.eni_ecole.qcm.bean.Theme;
import fr.eni_ecole.qcm.util.AccesBase;

/**
 * @author d1408davidj
 * 22 oct. 2015
 */
public class DALReponse {
	
	/**
	 * Méthode en charge de lister les réponse en fonction deu thème et de la question
	 * 22 oct. 2015
	 * @param theme Theme de la question
	 * @param question Question concernée
	 * @return La liste des réponses
	 * @throws SQLException
	 */
	public static List<Reponse> selectByThemeQuestion(Theme theme,Question question) throws SQLException{
		Connection cnx = null;
		PreparedStatement cmd = null;
		List<Reponse> ret = new ArrayList<Reponse>();
		String sql = "SELECT * FROM REPONSE r "
						+"INNER JOIN QUESTION q ON q.idQuestion = r.idQuestion "
						+"INNER JOIN THEME t ON t.idTheme = q.idTheme "
						+"WHERE t.idTheme = ? and q.idQuestion = ?";
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, theme.getIdTheme());
			cmd.setInt(2, question.getIdQuestion());
			ResultSet rs = cmd.executeQuery();
			
			while(rs.next()){
				Reponse r = new Reponse(rs.getInt("idReponse"), rs.getString("reponse"),
						rs.getBoolean("bonne_reponse"));
				ret.add(r);
			}
			
		}finally{
			if(cmd != null)cmd.close();
			if(cnx != null)cnx.close();
		}
		return ret;
	}

	/**
	 * Méthode en charge de récupérer la réponse en fonction d'un identifiant 
	 * 21 oct. 2015
	 * @param reponse Reponse avec l'identifiant recherché
	 * @return Lea réponse recherchée
	 * @throws SQLException
	 */
	public static Reponse selectById(Reponse reponse) throws SQLException{
		Connection cnx= null;
		PreparedStatement cmd = null;
	
		String sql = "SELECT * FROM REPONSE WHERE REPONSE.idReponse = ?";
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, reponse.getIdReponse());
			ResultSet res = cmd.executeQuery();
			
			if(res.next()){
				Reponse r = new Reponse(res.getInt("idReponse"), res.getString("reponse"),
						res.getBoolean("bonne_reponse"));
			}else{
				reponse = null;
			}			
		}finally{
			if(cmd != null)cmd.close();
			if(cnx != null)cnx.close();
		}
		return reponse;
	}
	
	
	/**
	 * Méthode en charge d'ajouter une reponse à la BD 
	 * 21 oct. 2015
	 * @param reponse Reponse à ajouter
	 * @return La réponse avec son id
	 * @throws SQLException
	 */
	public static Reponse ajouter(Reponse reponse) throws SQLException{
		Connection cnx = null;
		Statement st = null;
		PreparedStatement cmd = null;
		String sql = "INSERT INTO REPONSE(reponse,bonne_reponse) VALUES (?)";
		
		try{
			cnx = AccesBase.getConnection();
			
			cnx.setAutoCommit(false);
			
			cmd = cnx.prepareStatement(sql);
			cmd.setString(1, reponse.getReponse());
			cmd.executeUpdate();
			
			st = cnx.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(idReponse)as New_Id FROM REPONSE");
			if(rs.next()){
				reponse.setIdReponse(rs.getInt("New_Id"));
			}
			
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
		return reponse;
	}

	/**
	 * Méthode en charge de modifier une réponse 
	 * 21 oct. 2015
	 * @param reponse Réponse à modifier
	 * @throws SQLException
	 */
	public static void modifier(Reponse reponse) throws SQLException{
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "UPDATE REPONSE SET reponse=?,bonne_reponse=? WHERE idReponse=?";
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setString(1, reponse.getReponse());
			cmd.setBoolean(2, reponse.getBonneReponse());
			cmd.executeUpdate();			
		}finally{
			if(cnx!=null)cnx.close();
			if(cmd!=null)cmd.close();
		}
	}

	/**
	 * Méthode en charge de supprimer une réponse 
	 * 21 oct. 2015
	 * @param reponse Réponse a supprimer
	 * @throws SQLException
	 */
	public static void supprimer(Reponse reponse) throws SQLException{
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "DELETE FROM REPONSE WHERE idQuestion=?";
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, reponse.getIdReponse());
			cmd.executeUpdate();
		}finally{
			if(cmd!=null)cmd.close();
			if(cnx!=null)cnx.close();
		}
	}

	/**
	 * Méthode en charge de supprimer toutes les réponses liées à un thème 
	 * 22 oct. 2015
	 * @param theme Theme concerné
	 * @throws SQLException 
	 */
	public static void supprimerByTheme(Theme theme) throws SQLException{
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "DELETE r FROM REPONSE r "
					+"INNER JOIN QUESTION q ON q.idQuestion = r.idQuestion "
					+"INNER JOIN THEME t ON t.idTheme = q.idTheme "
					+"WHERE t.idTheme = ?";
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, theme.getIdTheme());
			cmd.executeUpdate();
		}finally{
			if(cmd!=null)cmd.close();
			if(cnx!=null)cnx.close();
		}
	}

}

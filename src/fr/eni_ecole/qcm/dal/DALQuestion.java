/**
 * 21 oct. 2015
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
import fr.eni_ecole.qcm.bean.Tirage;
import fr.eni_ecole.qcm.util.AccesBase;

/**
 * @author d1408davidj
 * 21 oct. 2015
 */
public class DALQuestion {

	/**
	 * Méthode en charge de récupérer la liste des questions associé a un thème 
	 * 21 oct. 2015
	 * @param theme Thème concerné
	 * @return La liste des questions
	 * @throws SQLException 
	 */
	public static List<Question> getQuestionByTheme(Theme theme) throws SQLException {
		Connection cnx = null;
		PreparedStatement cmd = null;
		List<Question>ret = new ArrayList<Question>();
		String sql = "SELECT * FROM QUESTION WHERE idTheme = ?";
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, theme.getIdTheme());
			ResultSet res = cmd.executeQuery();
			
			while(res.next()){
				Question question = new Question(res.getInt("idQuestion"),res.getString("enonce"),
						res.getBoolean("type_reponse"), res.getString("image"));
				question.setTheme(theme);
				ret.add(question);
			}
			
		}finally{
			if(cmd!=null)cmd.close();
			if(cnx!=null)cnx.close();
		}
		return ret;
	}

	/**
	 * Méthode en charge de récupérer la question en fonction d'un identifiant 
	 * 21 oct. 2015
	 * @param question Question avec l'identifiant recherché
	 * @return La question recherchée
	 * @throws SQLException
	 */
	public static Question selectById(Question question) throws SQLException{
		Connection cnx= null;
		PreparedStatement cmd = null;
	
		String sql = "SELECT * FROM QUESTION WHERE QUESTION.idQuestion = ?";
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, question.getIdQuestion());
			ResultSet res = cmd.executeQuery();
			
			if(res.next()){
				question = new Question(res.getInt("idQuestion"),res.getString("enonce"),
						res.getBoolean("type_reponse"), res.getString("image"));
				Theme t = new Theme();
				t.setIdTheme(res.getInt("idTheme"));
				question.setTheme(t);
				
			}else{
				question = null;
			}			
		}finally{
			if(cmd != null)cmd.close();
			if(cnx != null)cnx.close();
		}
		return question;
	}
		
	/**
	 * Méthode en charge d'ajouter une question à la BD et de la lié à un thème
	 * 21 oct. 2015
	 * @param question Question à ajouter
	 * @return La question avec son id
	 * @throws SQLException
	 */
	public static Question ajouter(Question question) throws SQLException{
		Connection cnx = null;
		Statement st = null;
		PreparedStatement cmd = null;
		String sql = "INSERT INTO QUESTION(idTheme,enonce,type_reponse,image) VALUES (?,?,?,?)";
		
		try{
			cnx = AccesBase.getConnection();
			
			cnx.setAutoCommit(false);
			
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, question.getTheme().getIdTheme());
			cmd.setString(2, question.getEnonce());
			cmd.setBoolean(3, question.getTypeReponse());
			cmd.setString(4, question.getImage());
			cmd.executeUpdate();
			
			st = cnx.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(idQuestion)as New_Id FROM QUESTION");
			if(rs.next()){
				question.setIdQuestion(rs.getInt("New_Id"));
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
		return question;
	}

	/**
	 * Méthode en charge de modifier une question 
	 * 21 oct. 2015
	 * @param question Question à modifier
	 * @throws SQLException
	 */
	public static void modifier(Question question) throws SQLException{
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "UPDATE QUESTION SET enonce=?,type_reponse=?,image=? WHERE idQuestion=?";
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setString(1, question.getEnonce());
			cmd.setBoolean(2, question.getTypeReponse());
			cmd.setString(3, question.getImage());
			cmd.setInt(4, question.getIdQuestion());
			cmd.executeUpdate();			
		}finally{
			if(cnx!=null)cnx.close();
			if(cmd!=null)cmd.close();
		}
	}

	/**
	 * Méthode en charge de supprimer une question 
	 * 21 oct. 2015
	 * @param question Question a supprimer
	 * @throws SQLException
	 */
	public static void supprimer(Question question) throws SQLException{
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "DELETE FROM QUESTION WHERE idQuestion=?";
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, question.getIdQuestion());
			cmd.executeUpdate();
		}finally{
			if(cmd!=null)cmd.close();
			if(cnx!=null)cnx.close();
		}
	}
	
	/**
	 * Méthode en charge de supprimer les questions rattachés à un thémes 
	 * 22 oct. 2015
	 * @param theme
	 * @throws SQLException
	 */
	public static void supprimerByTheme(Theme theme) throws SQLException{
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "DELETE q FROM QUESTION q "
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

	/**
	 * Méthode en charge de modifier le thème d'une question 
	 * 26 oct. 2015
	 * @param question Question concernée portant l'identifiant du nouveau thème
	 * @throws SQLException
	 */
	public static void changerTheme(Question question) throws SQLException{
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "UPDATE QUESTION SET idTheme = ? WHERE idQuestion = ?";
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, question.getTheme().getIdTheme());
			cmd.setInt(2, question.getIdQuestion());
			cmd.executeUpdate();
		}finally{
			if(cmd != null) cmd.close();
			if(cnx != null) cnx.close();
		}
	}
}

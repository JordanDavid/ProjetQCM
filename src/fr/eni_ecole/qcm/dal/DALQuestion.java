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
import java.util.ArrayList;
import java.util.List;

import fr.eni_ecole.qcm.bean.Question;
import fr.eni_ecole.qcm.bean.Theme;
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
				ret.add(question);
			}
			
		}finally{
			if(cmd!=null)cmd.close();
			if(cnx!=null)cnx.close();
		}
		return ret;
	}

}

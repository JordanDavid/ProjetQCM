/**
 * 20 oct. 2015
 * QCM V1.0
 * fr.eni_ecole.qcm.dal
 */
package fr.eni_ecole.qcm.dal;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import fr.eni_ecole.qcm.bean.Theme;
import fr.eni_ecole.qcm.util.AccesBase;

/**
 * @author d1408davidj
 * 20 oct. 2015
 */
public class DALTheme {

	/**
	 * Méthode en charge de récupérer la liste des thèmes 
	 * 20 oct. 2015
	 * @return La liste des thèmes
	 * @throws SQLException 
	 */
	public static List<Theme> selectAll() throws SQLException {
		Connection cnx= null;
		Statement cmd = null;
		List<Theme> ret = null;
	
		String sql = "SELECT * FROM THEME";
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.createStatement();
			ResultSet res = cmd.executeQuery(sql);
			
			while(res.next()){
				
			}
			
		}finally{
			if(cmd != null)cmd.close();
			if(cnx != null)cnx.close();
		}
		return ret;
	}

}

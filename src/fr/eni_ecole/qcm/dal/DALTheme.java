/**
 * 20 oct. 2015
 * QCM V1.0
 * fr.eni_ecole.qcm.dal
 */
package fr.eni_ecole.qcm.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
		List<Theme> ret = new ArrayList<Theme>();
	
		String sql = "SELECT * FROM THEME";
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.createStatement();
			ResultSet res = cmd.executeQuery(sql);
			
			while(res.next()){
				Theme theme = new Theme(res.getInt("idTheme"),res.getString("libelle_theme"));
				ret.add(theme);
			}			
		}finally{
			if(cmd != null)cmd.close();
			if(cnx != null)cnx.close();
		}
		return ret;
	}

	/**
	 * Méthode en charge de récupérer le thème en fonction d'un identifiant 
	 * 21 oct. 2015
	 * @param theme Thème avec l'identifiant recherché
	 * @return Le thème recherché
	 * @throws SQLException
	 */
	public static Theme selectById(Theme theme) throws SQLException{
		Connection cnx= null;
		PreparedStatement cmd = null;
	
		String sql = "SELECT * FROM THEME WHERE THEME.idTheme = ?";
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, theme.getIdTheme());
			ResultSet res = cmd.executeQuery();
			
			if(res.next()){
				theme.setLibelle(res.getString("libelle_theme"));
			}else{
				theme = null;
			}			
		}finally{
			if(cmd != null)cmd.close();
			if(cnx != null)cnx.close();
		}
		return theme;
	}
	
	/**
	 * Méthode en charge d'ajouter un thème à la BD 
	 * 21 oct. 2015
	 * @param theme Thème à ajouter
	 * @return Le thème avec son id
	 * @throws SQLException
	 */
	public static Theme ajouter(Theme theme) throws SQLException{
		Connection cnx = null;
		Statement st = null;
		PreparedStatement cmd = null;
		String sql = "INSERT INTO THEME(libelle_theme) VALUES (?)";
		
		try{
			cnx = AccesBase.getConnection();
			
			cnx.setAutoCommit(false);
			
			cmd = cnx.prepareStatement(sql);
			cmd.setString(1, theme.getLibelle());
			cmd.executeUpdate();
			
			st = cnx.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(id)as New_Id FROM THEME");
			if(rs.next()){
				theme.setIdTheme(rs.getInt("New_Id"));
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
		return theme;
	}

	/**
	 * Méthode en charge de modifier un thème 
	 * 21 oct. 2015
	 * @param theme Thème à modifier
	 * @throws SQLException
	 */
	public static void modifier(Theme theme) throws SQLException{
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "UPDATE THEME SET libelle_theme=? WHERE idTheme=?";
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setString(1, theme.getLibelle());
			cmd.setInt(2, theme.getIdTheme());
			cmd.executeUpdate();			
		}finally{
			if(cnx!=null)cnx.close();
			if(cmd!=null)cmd.close();
		}
	}

	/**
	 * Méthode en charge de supprimer un thème 
	 * 21 oct. 2015
	 * @param theme
	 * @throws SQLException
	 */
	public static void supprimer(Theme theme) throws SQLException{
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "DELETE FROM THEME WHERE idTheme=?";
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

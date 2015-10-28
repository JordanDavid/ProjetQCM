/**
 * 26 oct. 2015
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

import fr.eni_ecole.qcm.bean.Section;
import fr.eni_ecole.qcm.bean.Test;
import fr.eni_ecole.qcm.bean.Theme;
import fr.eni_ecole.qcm.util.AccesBase;

/**
 * @author d1408davidj
 * 26 oct. 2015
 */
public class DALSection {

	/**
	 * Méthode en charge de lister les sections en fonction d'un test
	 * 28 oct. 2015
	 * @param test Test concerné
	 * @return La liste des sections
	 * @throws SQLException
	 */
	public static List<Section> selectSectionsByTest(Test test) throws SQLException{
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql="SELECT * FROM SECTION "
					+ "INNER JOIN THEME ON THEME.idTheme = SECTION.idTheme "
					+ "WHERE idTest = ? ORDER BY numero_section";
		List<Section> ret = new ArrayList<Section>();
		
		try{
			
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, test.getId());
			ResultSet res = cmd.executeQuery();
			
			while(res.next()){
				Theme t = new Theme(res.getInt("idTheme"),res.getString("libelle_theme"));
				Section s = new Section(res.getInt("numero_section"),test, t, res.getInt("nombre_question"));
				ret.add(s);
			}
			
			
		}finally{
			if(cmd != null) cmd.close();
			if(cnx != null) cnx.close();
		}
		
		return ret;
	}

	/**
	 *  Méthode en charge d'ajouter une section
	 * 28 oct. 2015
	 * @param s Section à ajouter
	 * @return La section avec son identifiant
	 * @throws SQLException
	 */
	public static Section ajoutSection(Section section) throws SQLException {
		Connection cnx = null;
		PreparedStatement cmd = null;
		Statement st = null;
		String sql = "INSERT INTO SECTION (idTest,idTheme,nombre_question) VALUES (?,?,?)";
		
		try{
			cnx = AccesBase.getConnection();
			
			cnx.setAutoCommit(false);
			
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, section.getTest().getId());
			cmd.setInt(2,section.getTheme().getIdTheme());
			cmd.setInt(3, section.getNbQuestion());
			cmd.executeUpdate();
			
			st = cnx.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(numero_section)as New_Id FROM SECTION");
			if(rs.next()){
				section.setNumSection(rs.getInt("New_Id"));
			}
						
			cnx.commit();
			
		}catch(SQLException sqle){
			if(cnx != null)
				cnx.rollback();
			throw sqle;
		}finally{
			if(cmd != null) cmd.close();
			if(st != null) st.close();
			if(cnx != null) cnx.close();
		}
		return section;
	}

	/**
	 * Méthode en charge de modifier une section
	 * 28 oct. 2015
	 * @param section Section à modifier
	 * @throws SQLException
	 */
	public static void modifierSection(Section section) throws SQLException {
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "UPDATE SECTION SET idTheme=?,idTest=?,nombre_question=? WHERE numero_section=?";
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1,section.getTheme().getIdTheme());
			cmd.setInt(2, section.getTest().getId());
			cmd.setInt(3, section.getNbQuestion());
			cmd.setInt(4, section.getNumSection());
			cmd.executeUpdate();	
			
		}finally{
			if(cmd != null) cmd.close();
			if(cnx != null) cnx.close();
		}
	}

	/**
	 * Méthode en charge de supprimer une section
	 * 28 oct. 2015
	 * @param section Section à supprimer
	 * @throws SQLException
	 */
	public static void supprimerSection(Section section) throws SQLException {
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "DELETE FROM SECTION WHERE numero_section=?";
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, section.getNumSection());
			cmd.executeUpdate();
					
		}finally{
			if(cmd != null) cmd.close();
			if(cnx != null) cnx.close();
		}
	}
}

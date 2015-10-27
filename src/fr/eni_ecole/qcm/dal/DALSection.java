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
	
}

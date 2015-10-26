/**
 * 26 oct. 2015
 * QCM V1.0
 * fr.eni_ecole.qcm.dal
 */
package fr.eni_ecole.qcm.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni_ecole.qcm.bean.Section;
import fr.eni_ecole.qcm.bean.Test;
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
					+ "WHERE idTest = ? ";
		List<Section> ret = new ArrayList<Section>();
		
		try{
			
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, test.getId());
			
		}finally{
			if(cmd != null) cmd.close();
			if(cnx != null) cnx.close();
		}
		
		return ret;
	}
	
}

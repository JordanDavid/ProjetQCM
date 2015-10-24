/**
 * 22 oct. 2015
 * QCM V1.0
 * fr.eni_ecole.qcm.dal
 */
package fr.eni_ecole.qcm.dal;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.eni_ecole.qcm.bean.Candidat;
import fr.eni_ecole.qcm.bean.Formateur;
import fr.eni_ecole.qcm.bean.ResponsableFormation;
import fr.eni_ecole.qcm.bean.Test;
import fr.eni_ecole.qcm.bean.Theme;
import fr.eni_ecole.qcm.bean.Utilisateur;
import fr.eni_ecole.qcm.tool.ManipString;
import fr.eni_ecole.qcm.util.AccesBase;

/**
 * @author d1410gerardm
 * 22 oct. 2015
 */
public class DALTest implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 2050877754531413020L;
	
	/**
	 * Méthode en charge de récupérer la liste des tests associés au theme
	 * @param theme Theme associé aux tests
	 * @return une liste des tests
	 * @throws SQLException
	 */
	public static List<Test> getTestByTheme(Theme theme) throws SQLException{
		
		List<Test> listeTests = new ArrayList<Test>();		
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "SELECT * FROM TEST te "
				+ "INNER JOIN SECTION s ON te.idTest = s.idTest "
				+ "INNER JOIN THEME th ON th.idTheme = s.idTheme "
				+ "WHERE th.idTheme = ?";
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, theme.getIdTheme());
			ResultSet res = cmd.executeQuery();
			
			while (res.next()){
				Test test = new Test();
				test.setId(res.getInt("idTest"));
				test.setLibelle(res.getString("libelle_test"));
				test.setDuree(res.getInt("duree"));
				test.setSeuil_minimum(res.getInt("seuil_minimum"));
				test.setSeuil_maximum(res.getInt("seuil_maximum"));
				
				listeTests.add(test);
			}			
		}finally{
			if(cmd!=null)cmd.close();
			if(cnx!=null)cnx.close();
		}
		return listeTests;
	}

	/**
	 * Méthode en charge de récupérer la liste des tests associés à l'utilisateur
	 * @param user Utilisateur concerné
	 * @return une liste des tests
	 * @throws SQLException
	 */
	public static List<Test> getTestByUser(Utilisateur user) throws SQLException{
		
		List<Test> listeTests = new ArrayList<Test>();		
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "SELECT * FROM TEST te "
				+ "INNER JOIN INSCRIPTION i ON i.idTest = te.idTest "
				+ "INNER JOIN UTILISATEUR u ON u.idUtilisateur = i.idUtilisateur "
				+ "WHERE i.idUtilisateur = ?";
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, user.getId());
			ResultSet res = cmd.executeQuery();
			
			while (res.next()){
				Test test = new Test();
				test.setId(res.getInt("idTest"));
				test.setLibelle(res.getString("libelle_test"));
				test.setDuree(res.getInt("duree"));
				test.setSeuil_minimum(res.getInt("seuil_minimum"));
				test.setSeuil_maximum(res.getInt("seuil_maximum"));
				
				listeTests.add(test);
			}			
		}finally{
			if(cmd!=null)cmd.close();
			if(cnx!=null)cnx.close();
		}
		return listeTests;
	}

}

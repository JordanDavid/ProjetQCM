/**
 * 22 oct. 2015
 * QCM V1.0
 * fr.eni_ecole.qcm.dal
 */
package fr.eni_ecole.qcm.dal;

import java.sql.Statement;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fr.eni_ecole.qcm.bean.Candidat;
import fr.eni_ecole.qcm.bean.Formateur;
import fr.eni_ecole.qcm.bean.PlageHoraire;
import fr.eni_ecole.qcm.bean.ResponsableFormation;
import fr.eni_ecole.qcm.bean.Section;
import fr.eni_ecole.qcm.bean.Test;
import fr.eni_ecole.qcm.bean.Theme;
import fr.eni_ecole.qcm.bean.Utilisateur;
import fr.eni_ecole.qcm.tool.ManipDate;
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
	 * Méthode en charge de récupérer la liste des test 
	 * 26 oct. 2015
	 * @return La liste des tests
	 * @throws SQLException 
	 */
	public static List<fr.eni_ecole.qcm.bean.Test> selectAll() throws SQLException{
		List<Test> listeTests = new ArrayList<Test>();		
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "SELECT * FROM TEST";
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
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
				+ "WHERE i.idUtilisateur = ? "
				+ "AND i.date_debut IS NULL";
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
	
	/**
	 * Méthode en charge de récupérer la liste des plage horaire d'un test
	 * @param test Test sélectionné par l'utilisateur
	 * @return une liste de plage horaire
	 * @throws SQLException
	 */
	public static List<PlageHoraire> getPlageHoraireByTest(Test test) throws SQLException{
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		List<PlageHoraire> plageHoraires = new ArrayList<PlageHoraire>();		
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "SELECT t.libelle_test, ph.* "
				+ "FROM PLAGE_HORAIRE_TEST pht "
				+ "INNER JOIN PLAGE_HORAIRE ph ON ph.idPlageHoraire = pht.idPlageHoraire "
				+ "INNER JOIN TEST t ON pht.idTest = t.idTest "
				+ "WHERE pht.idTest = ?";
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, test.getId());
			ResultSet res = cmd.executeQuery();
			
			while (res.next()){
				PlageHoraire plageHoraire = new PlageHoraire();
				plageHoraire.setIdPlageHoraire(res.getInt("idPlageHoraire"));
				plageHoraire.setDateDebut(df.format(res.getTimestamp("date_debut")));
				plageHoraire.setDateFin(df.format(res.getTimestamp("date_fin")));
				
				plageHoraires.add(plageHoraire);
			}			
		}finally{
			if(cmd!=null)cmd.close();
			if(cnx!=null)cnx.close();
		}
		return plageHoraires;
	}

	/**
	 * Méthode en charge de écupérer un test en fonction de son id 
	 * 27 oct. 2015
	 * @param test Test recherché
	 * @return Le test sinon null
	 * @throws SQLException
	 */
	public static Test getTest(Test test) throws SQLException{
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "SELECT * FROM TEST WHERE idTest = ?";
		
		try{
			cnx = AccesBase.getConnection();
			cmd= cnx.prepareStatement(sql);
			cmd.setInt(1, test.getId()); 
			ResultSet res = cmd.executeQuery();
			
			if(res.next()){
				test.setLibelle(res.getString("libelle_test"));
				test.setDuree(res.getInt("duree"));
				test.setSeuil_minimum(res.getInt("seuil_minimum"));
				test.setSeuil_maximum(res.getInt("seuil_maximum"));
			}else{
				test = null;
			}
			
		}finally{
			if(cmd != null) cmd.close();
			if(cnx != null) cnx.close();
		}
		return test;
	}
	
	
	/**
	 * Méthode en charge d'ajouter un test à la BDD 
	 * 27 oct. 2015
	 * @param test Test à ajouter
	 * @return Le test avec son identifiant
	 * @throws SQLException 
	 */


	public static Test ajouter(Test test) throws SQLException {
		Connection cnx = null;
		PreparedStatement cmd = null;
		Statement st = null;
		String sql = "INSERT INTO TEST(libelle_test,duree,seuil_minimum,seuil_maximum) VALUES(?,?,?,?)";
		try{
			cnx = AccesBase.getConnection();
			
			cnx.setAutoCommit(false);			
			
			cmd = cnx.prepareStatement(sql);
			cmd.setString(1, test.getLibelle());
			cmd.setInt(2, test.getDuree());
			cmd.setInt(3, test.getSeuil_minimum());
			cmd.setInt(4, test.getSeuil_maximum());
			cmd.executeUpdate();
			
			st = cnx.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(idTest)as New_Id FROM TEST");
			if(rs.next()){
				test.setId(rs.getInt("New_Id"));
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
		return test;
	}

	/**
	 * Méthode en charge de modifier un test 
	 * 27 oct. 2015
	 * @param test Test à modifier
	 * @throws SQLException 
	 */
	public static void modifier(Test test) throws SQLException {
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "UPDATE TEST set libelle_test=?,duree=?,seuil_minimum=?,seuil_maximum=? WHERE idTest = ?";
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setString(1, test.getLibelle());
			cmd.setInt(2, test.getDuree());
			cmd.setInt(3, test.getSeuil_minimum());
			cmd.setInt(4, test.getSeuil_maximum());
			cmd.setInt(5, test.getId());
			cmd.executeUpdate();
			
		} finally{
			if(cmd != null)cmd.close();
			if(cnx!=null)cnx.close();
		}
	}

	/**
	 * Méthode en charge de supprimer un test 
	 * 27 oct. 2015
	 * @param test test à supprimer
	 * @throws SQLException
	 */
	public static void supprimer(Test test) throws SQLException{
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "DELETE FROM TEST WHERE idTest = ?";
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, test.getId());
			cmd.executeUpdate();
			
		} finally{
			if(cmd != null)cmd.close();
			if(cnx!=null)cnx.close();
		}
	}

	/**
	 * Méthode en charge de lier un test à un e plage horaire 
	 * 28 oct. 2015
	 * @param test Test concerné
	 * @param plage Plage horaire souhaitée
	 * @throws SQLException
	 */
	public static void ajoutPlage(Test test, PlageHoraire plage) throws SQLException {
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "INSERT INTO PLAGE_HORAIRE_TEST VALUES(?,?)";
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, test.getId());
			cmd.setInt(2, plage.getIdPlageHoraire());
			cmd.executeUpdate();
					
		}finally{
			if(cmd != null) cmd.close();
			if(cnx != null) cnx.close();
		}
	}


	/**
	 * Méthode en charge de modifier une plage horaire d'un test
	 * 28 oct. 2015
	 * @param test Test concerné
	 * @param plage Plage à modifier
	 * @throws SQLException
	 */
	public static void modifierPlage(Test test,PlageHoraire plage) throws SQLException {
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql =  "UPDATE p SET p.date_debut=?,p.date_fin=? "
					+ "FROM PLAGE_HORAIRE p "
					+ "INNER JOIN PLAGE_HORAIRE_TEST pt ON pt.idPlageHoraire = p.idPlageHoraire "
					+ "WHERE pt.idPlageHoraire = ? AND pt.idTest = ?";
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setString(1,plage.getDateDebut());
			cmd.setString(2,plage.getDateFin());
			cmd.setInt(3,plage.getIdPlageHoraire());
			cmd.setInt(4,test.getId());
			cmd.executeUpdate();
					
		}finally{
			if(cmd != null) cmd.close();
			if(cnx != null) cnx.close();
		}
	}


	/**
	 * Méthode en charge de supprimer la liaison entre une plage horaire et un test 
	 * 28 oct. 2015
	 * @param test Test concerné
	 * @param plage Plage horaire à supprimer
	 * @throws SQLException
	 */
	public static void supprimerPlage(Test test, PlageHoraire plage) throws SQLException {
		Connection cnx = null;
		PreparedStatement cmd = null;
		String sql = "DELETE FROM PLAGE_HORAIRE_TEST WHERE idPlageHoraire = ? AND idTest = ?";
		
		try{
			cnx = AccesBase.getConnection();
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, plage.getIdPlageHoraire());
			cmd.setInt(2, test.getId());
			cmd.executeUpdate();
					
		}finally{
			if(cmd != null) cmd.close();
			if(cnx != null) cnx.close();
		}
	}



}

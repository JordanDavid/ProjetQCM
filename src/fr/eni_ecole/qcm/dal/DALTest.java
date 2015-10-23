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
	
//	public static List<Test> selectAll(Theme theme){
//		
//		List<Test> listeTests = new ArrayList<Test>();		
//		Connection cnx = null;
//		PreparedStatement rqt = null;
//		ResultSet rs = null;
//		try{
//			cnx = AccesBase.getConnection();
//			rqt = cnx.prepareStatement("SELECT * FROM TEST t "
//					+ "INNER JOIN TYPE_UTILISATEUR tu ON u.idType = tu.idType "
//					+ "WHERE tu.libelleType = 'Candidat'");
//			rs=rqt.executeQuery();
//			
//			while (rs.next()){
//				Utilisateur candidat = new Utilisateur();
//				candidat.setId(rs.getInt("id"));
//				candidat.setLogin(rs.getString("login"));
//				candidat.setMotdepasse(rs.getString("motdepasse"));
//				candidat.setNom(rs.getString("nom"));
//				candidat.setPrenom(rs.getString("prenom"));
//				candidat.setMail(rs.getString("email"));
//				
//				listeTests.add(candidat);
//			}			
//		}finally{
//			if (rs!=null) rs.close();
//			if (rqt!=null) rqt.close();
//			if (cnx!=null) cnx.close();
//		}
//		return listeTests;
//	}

}

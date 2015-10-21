/**
 * 20 oct. 2015
 * QCM V1.0
 * fr.eni_ecole.qcm.dal
 */
package fr.eni_ecole.qcm.dal;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.eni_ecole.qcm.bean.Candidat;
import fr.eni_ecole.qcm.bean.Formateur;
import fr.eni_ecole.qcm.bean.ResponsableFormation;
import fr.eni_ecole.qcm.bean.Utilisateur;
import fr.eni_ecole.qcm.tool.ManipString;
import fr.eni_ecole.qcm.util.AccesBase;

/**
 * @author d1410gerardm
 * 20 oct. 2015
 */
public class DALUtilisateur implements Serializable{

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 4976081422424317747L;
	
	/**
	 * Méthode en charge de de la connection des utilisateurs 
	 * 20 oct. 2015
	 * @param utilisateur
	 * @return un utilisateur
	 * @throws SQLException
	 */
	public static Utilisateur rechercher(Utilisateur utilisateur) throws SQLException{
		Connection cnx = null;
		PreparedStatement rqt = null;
		ResultSet rs = null;
		Utilisateur ret = null;
		try{
			cnx = AccesBase.getConnection();
			rqt = cnx.prepareStatement("SELECT * FROM UTILISATEUR u "
					+ "INNER JOIN TYPE_UTILISATEUR tu ON tu.idType = u.idType "
					+ "WHERE u.login = ? AND u.motdepasse = ?");
			rqt.setString(1, utilisateur.getLogin());
			rqt.setString(2, ManipString.encode(utilisateur.getMotdepasse()));
			rs = rqt.executeQuery();
			
			if (rs.next()) {
				String type = rs.getString("libelleType");
				int id = rs.getInt("idUtilisateur");
				String login = rs.getString("login");
				String motdepasse = rs.getString("motdepasse");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				String mail = rs.getString("mail");
				
				// depending on the type of user, a new instance is created
				switch (type) {
				case "Formateur":
					ret = new Formateur(id, login, motdepasse, nom, prenom, mail);
					break;
				case "Candidat":
					ret = new Candidat(id, login, motdepasse, nom, prenom, mail);
				case "Responsable de formation":
					ret = new ResponsableFormation(id, login, motdepasse, nom, prenom, mail);
				default:
					break;
				}
			}
		}finally{
			if (rs!=null) rs.close();
			if (rqt!=null) rqt.close();
			if (cnx!=null) cnx.close();
		}
		return ret;		
	}

}

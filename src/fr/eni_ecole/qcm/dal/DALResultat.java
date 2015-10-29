/**
 * 29 oct. 2015
 * QCM V1.0
 * fr.eni_ecole.qcm.dal
 */
package fr.eni_ecole.qcm.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.eni_ecole.qcm.bean.Resultat;
import fr.eni_ecole.qcm.tool.ManipDate;
import fr.eni_ecole.qcm.util.AccesBase;

/**
 * @author d1408davidj
 * 29 oct. 2015
 */
public class DALResultat {

	/**
	 * Méthode en charge d'ajouter un résultat 
	 * 29 oct. 2015
	 * @param resultat Résultat à ajouter
	 * @return Le résultat avec son identifiant
	 * @throws SQLException 
	 */
	public static Resultat ajouter(Resultat resultat) throws SQLException{
		Connection cnx = null;
		PreparedStatement cmd = null;
		Statement st = null;
		String sql = "INSERT INTO RESULTAT (nombre_bonnes_reponses,seuil_atteint,temps_passe,nombre_incidents "
						+",idInscription,idUtilisateur,idTest) VALUES (?,?,?,?,?,?,?,?)";
		try{
			cnx = AccesBase.getConnection();
			
			cnx.setAutoCommit(false);			
			
			cmd = cnx.prepareStatement(sql);
			cmd.setInt(1, resultat.getNbBonnesReponses());
			cmd.setString(2,resultat.getSeuilAtteint());
			cmd.setDate(3, ManipDate.dateUtilVersSQL(resultat.getTempsPasse()));
			cmd.setInt(4,resultat.getNbIncidents());
			cmd.setInt(5, resultat.getInscription().getIdInscription());
			cmd.setInt(6,resultat.getUtilisateur().getId());
			cmd.setInt(7,resultat.getTest().getId());
			
			cmd.executeUpdate();
			
			st = cnx.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(idResultat)as New_Id FROM RESULTAT");
			if(rs.next()){
				resultat.setIdResultat(rs.getInt("New_Id"));
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
		return resultat;
	}
	
}

/**
 * 
 */
package fr.eni_ecole.qcm.dal;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.eni_ecole.qcm.bean.PlageHoraire;
import fr.eni_ecole.qcm.util.AccesBase;

/**
 * @author d1410gerardm
 *
 */
public class DALPlageHoraire implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5241936122250168629L;
	
	public static Boolean ajouter(PlageHoraire plageHoraire) throws SQLException{
		Connection cnx = null;
		Statement st = null;
		PreparedStatement cmd = null;
		String sql = "INSERT INTO PLAGE_HORAIRE VALUES (?,?)";
		
		try{
			cnx = AccesBase.getConnection();
			
			cnx.setAutoCommit(false);
			
			cmd = cnx.prepareStatement(sql);
			cmd.setDate(1, (Date) plageHoraire.getDateDebut());
			cmd.setDate(2, (Date)plageHoraire.getDateFin());
			cmd.executeUpdate();
			
			st = cnx.createStatement();
			ResultSet rs = st.executeQuery("SELECT MAX(idPlageHoraire)as New_Id FROM PLAGE_HORAIRE");
			if(rs.next()){
				plageHoraire.setIdPlageHoraire(rs.getInt("New_Id"));
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
		return true;
	}

}

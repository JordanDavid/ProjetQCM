package fr.eni_ecole.qcm.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Classe Utilitaire pour se connecter à la BD.
 * @author Administrateur
 * @date 20 janv. 2015
 * @version J6_TPWEB v1.0
 */
public class AccesBase {	
	/**
	 * Récupère une connection à la BD.
	 * @return Connection
	 * @throws SQLException Exception de type SQL. 
	 */
	public static Connection getConnection() throws SQLException{
		Connection cnx=null;

		// Charger l'annuaire JNDI
		InitialContext jndi = null;
		try {
			jndi = new InitialContext();
		} catch (NamingException ne) {
			ne.printStackTrace();
			throw new SQLException("Impossible d'atteindre l'arbre JNDI");
		}
		
				
		// Chercher le pool de connexions dans l'annuaire
		DataSource ds = null;
		try {
			ds=(DataSource) jndi.lookup("java:comp/env/jdbc/dsTPWeb");
		} catch (NamingException ne) {
			ne.printStackTrace();
			throw new SQLException("Pool de connexion introuvable dans l'arbre jndi");
		}
		
		// Activer une connexion du pool
		cnx=ds.getConnection();
		
		return cnx;
	}
}

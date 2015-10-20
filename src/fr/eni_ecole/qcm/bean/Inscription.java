/**
 * 20 oct. 2015
 * QCM V1.0
 * fr.eni_ecole.qcm.bean
 */
package fr.eni_ecole.qcm.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author d1408davidj
 * 20 oct. 2015
 */
public class Inscription implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -4512201802284908754L;
	
	private int idInscription;
	private Test test;
	private Utilisateur utilisateur;
	private Date dateDebut;
	private Date DateFin;
	private Date tempsRestant;
	
	/**
	 * d1410gerardm
	 * Classe Inscription 
	 * @param idInscription Identifiant de l'inscription
	 * @param test test de l'inscription
	 * @param utilisateur Utilisateur de l'inscription
	 * @param dateDebut Date début de l'inscription
	 * @param dateFin Date fin de l'inscription
	 * @param tempsRestant Temps restant de l'inscription
	 */
	public Inscription(int idInscription, Test test, Utilisateur utilisateur, Date dateDebut,
			Date dateFin, Date tempsRestant){
		setIdInscription(idInscription);
		setTest(test);
		setUtilisateur(utilisateur);
		setDateDebut(dateDebut);
		setDateFin(dateFin);
		setTempsRestant(tempsRestant);
	}
	
	/**
	 * d1410gerardm
	 * Classe Inscription
	 */
	public Inscription(){
		super();
	}

	/**
	 * Getter for idInscription
	 * @return the idInscription
	 */
	public int getIdInscription() {
		return idInscription;
	}

	/**
	 * Setter for idInscription
	 * @param idInscription the idInscription to set
	 */
	public void setIdInscription(int idInscription) {
		this.idInscription = idInscription;
	}

	/**
	 * Getter for test
	 * @return the test
	 */
	public Test getTest() {
		return test;
	}

	/**
	 * Setter for test
	 * @param test the test to set
	 */
	public void setTest(Test test) {
		this.test = test;
	}

	/**
	 * Getter for utilisateur
	 * @return the utilisateur
	 */
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	/**
	 * Setter for utilisateur
	 * @param utilisateur the utilisateur to set
	 */
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	/**
	 * Getter for dateDebut
	 * @return the dateDebut
	 */
	public Date getDateDebut() {
		return dateDebut;
	}

	/**
	 * Setter for dateDebut
	 * @param dateDebut the dateDebut to set
	 */
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * Getter for DateFin
	 * @return the dateFin
	 */
	public Date getDateFin() {
		return DateFin;
	}

	/**
	 * Setter for DateFin
	 * @param dateFin the dateFin to set
	 */
	public void setDateFin(Date dateFin) {
		DateFin = dateFin;
	}

	/**
	 * Getter for tempsRestant
	 * @return the tempsRestant
	 */
	public Date getTempsRestant() {
		return tempsRestant;
	}

	/**
	 * Setter for tempsRestant
	 * @param tempsRestant the tempsRestant to set
	 */
	public void setTempsRestant(Date tempsRestant) {
		this.tempsRestant = tempsRestant;
	}
	

}

/**
 * 20 oct. 2015
 * QCM V1.0
 * fr.eni_ecole.qcm.bean
 */
package fr.eni_ecole.qcm.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author d1410gerardm
 * 20 oct. 2015
 */
public class Resultat implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -1056986759384298922L;
	
	private int idResultat;
	private int nbBonnesReponses;
	private int totalQuestion;
	private String seuilAtteint;
	private int tempsPasse;
	private int nbIncidents;
	private Inscription inscription;
	private Utilisateur utilisateur;
	private Test test;
	
	/**
	 * d1410gerardm
	 * Classe Resultat 
	 * @param idResultat Identifant du resultat
	 * @param nbBonnesReponses Nombre de bonnes reponses du resultat
	 * @param seuilAtteint Seuil du resultat
	 * @param tempsPasse Temps passe du resultat
	 * @param nbIncidents Nombre d'incidents du resultat
	 */
	public Resultat(int idResultat, int nbBonnesReponses,int totalQuestion, String seuilAtteint, int tempsPasse, int nbIncidents){
		setIdResultat(idResultat);
		setNbBonnesReponses(nbBonnesReponses);
		setSeuilAtteint(seuilAtteint);
		setTempsPasse(tempsPasse);
		setNbIncidents(nbIncidents);
		setTotalQuestion(totalQuestion);
	}
	
	/**
	 * d1410gerardm
	 * Classe Resultat
	 */
	public Resultat(){
		super();
	}

	/**
	 * Getter for idResultat
	 * @return the idResultat
	 */
	public int getIdResultat() {
		return idResultat;
	}

	/**
	 * Setter for idResultat
	 * @param idResultat the idResultat to set
	 */
	public void setIdResultat(int idResultat) {
		this.idResultat = idResultat;
	}

	/**
	 * Getter for nbBonnesReponses
	 * @return the nbBonnesReponses
	 */
	public int getNbBonnesReponses() {
		return nbBonnesReponses;
	}

	/**
	 * Setter for nbBonnesReponses
	 * @param nbBonnesReponses the nbBonnesReponses to set
	 */
	public void setNbBonnesReponses(int nbBonnesReponses) {
		this.nbBonnesReponses = nbBonnesReponses;
	}

	/**
	 * Getter for seuilAtteint
	 * @return the seuilAtteint
	 */
	public String getSeuilAtteint() {
		return seuilAtteint;
	}

	/**
	 * Setter for seuilAtteint
	 * @param seuilAtteint the seuilAtteint to set
	 */
	public void setSeuilAtteint(String seuilAtteint) {
		this.seuilAtteint = seuilAtteint;
	}

	/**
	 * Getter for tempsPasse
	 * @return the tempsPasse
	 */
	public int getTempsPasse() {
		return tempsPasse;
	}

	/**
	 * Setter for tempsPasse
	 * @param tempsPasse the tempsPasse to set
	 */
	public void setTempsPasse(int tempsPasse) {
		this.tempsPasse = tempsPasse;
	}

	/**
	 * Getter for nbIncidents
	 * @return the nbIncidents
	 */
	public int getNbIncidents() {
		return nbIncidents;
	}

	/**
	 * Setter for nbIncidents
	 * @param nbIncidents the nbIncidents to set
	 */
	public void setNbIncidents(int nbIncidents) {
		this.nbIncidents = nbIncidents;
	}

	/**
	 * Getter for inscription
	 * @return the inscription
	 */
	public Inscription getInscription() {
		return inscription;
	}

	/**
	 * Setter for inscription
	 * @param inscription the inscription to set
	 */
	public void setInscription(Inscription inscription) {
		this.inscription = inscription;
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

	public int getPourcentagebonneReponse() {
		return (100*this.nbBonnesReponses)/this.totalQuestion;
	}

	/**
	 * Getter for totalQuestion
	 * @return the totalQuestion
	 */
	public int getTotalQuestion() {
		return totalQuestion;
	}

	/**
	 * Setter for totalQuestion
	 * @param totalQuestion the totalQuestion to set
	 */
	public void setTotalQuestion(int totalQuestion) {
		this.totalQuestion = totalQuestion;
	}

}

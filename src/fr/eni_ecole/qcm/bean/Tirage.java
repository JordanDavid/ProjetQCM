/**
 * 20 oct. 2015
 * QCM V1.0
 * fr.eni_ecole.qcm.bean
 */
package fr.eni_ecole.qcm.bean;

import java.io.Serializable;

/**
 * @author d1410gerardm
 * 20 oct. 2015
 */
public class Tirage implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -8776622742506847574L;
	
	private Question question;
	private Inscription inscription;
	private Utilisateur utilisateur;
	private Boolean enCours;
	private Boolean marque;
	private Test test;
	
	/**
	 * d1410gerardm
	 * Classe Tirage 
	 * @param question Question du tirage
	 * @param inscription Inscription du tirage 
	 * @param utilisateur Utilisateur du tirage
	 * @param enCours Etat du tirage
	 * @param marque Marque du tirage
	 */
	public Tirage(Question question, Inscription inscription, Utilisateur utilisateur,
			Boolean enCours, Boolean marque){
		setQuestion(question);
		setInscription(inscription);
		setUtilisateur(utilisateur);
		setEnCours(enCours);
		setMarque(marque);
	}
	
	/**
	 * d1410gerardm
	 * Classe Tirage
	 */
	public Tirage(){
		super();
	}

	/**
	 * Getter for question
	 * @return the question
	 */
	public Question getQuestion() {
		return question;
	}

	/**
	 * Setter for question
	 * @param question the question to set
	 */
	public void setQuestion(Question question) {
		this.question = question;
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
	 * Getter for enCours
	 * @return the enCours
	 */
	public Boolean getEnCours() {
		return enCours;
	}

	/**
	 * Setter for enCours
	 * @param enCours the enCours to set
	 */
	public void setEnCours(Boolean enCours) {
		this.enCours = enCours;
	}

	/**
	 * Getter for marque
	 * @return the marque
	 */
	public Boolean getMarque() {
		return marque;
	}

	/**
	 * Setter for marque
	 * @param marque the marque to set
	 */
	public void setMarque(Boolean marque) {
		this.marque = marque;
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

}

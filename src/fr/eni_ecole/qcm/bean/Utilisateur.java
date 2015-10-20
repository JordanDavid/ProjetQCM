/**
 * 20 oct. 2015
 * QCM V1.0
 * fr.eni_ecole.qcm.bean
 */
package fr.eni_ecole.qcm.bean;

import java.io.Serializable;

import fr.eni_ecole.qcm.tool.ManipString;

/**
 * @author d1410gerardm
 * 20 oct. 2015
 */
public class Utilisateur implements Serializable{

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -7726805353377428960L;
	
	private int id;
	private String login;
	private String motdepasse;
	private String nom;
	private String prenom;
	private String mail;
	
	/**
	 * d1410gerardm
	 * Classe Utilisateur
	 */
	public Utilisateur(){
		super();
	}

	/**
	 * d1410gerardm
	 * Classe Utilisateur 
	 * @param id
	 */
	public Utilisateur(int id) {
		this();
		setId(id);
	}
	
	/**
	 * d1410gerardm
	 * Classe Utilisateur
	 * @param id Identifiant de l'utilisateur
	 * @param login Login de l'utilisateur
	 * @param motdepasse Mot de passe de l'utilisateur
	 * @param nom Nom de l'utilisateur
	 * @param prenom Prenom de l'utilisateur
	 * @param mail Mail de l'utilisateur
	 */
	public Utilisateur(int id, String login, String motdepasse, String nom, String prenom, String mail){
		this(id);
		setLogin(login);
		setMotdepasse(motdepasse);
		setNom(nom);
		setPrenom(prenom);
		setMail(mail);
	}
	
	
	/**
	 * Getter for mail
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Setter for mail
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * Getter for login
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Setter for login
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Getter for motdepasse
	 * @return the motdepasse
	 */
	public String getMotdepasse() {
		return motdepasse;
	}

	/**
	 * Setter for motdepasse
	 * @param motdepasse the motdepasse to set
	 */
	public void setMotdepasse(String motdepasse) {
		this.motdepasse = motdepasse;
	}
	
	/**
	 * Getter for id
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter for id
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter for nom
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Setter for nom
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom.toUpperCase();
	}

	/**
	 * Getter for prenom
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Setter for prenom
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		char[] separateur = {' ', '-', '\''};
		this.prenom = ManipString.firstInUpper(prenom, separateur);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getNom() + " " + getPrenom();
	}
	
}

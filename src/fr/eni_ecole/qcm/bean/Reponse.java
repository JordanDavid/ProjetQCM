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
public class Reponse implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -2994770495038984904L;
	
	private int idReponse;
	private String reponse;
	private Boolean bonneReponse;
	private Question question;

	/**
	 * d1410gerardm
	 * Classe Reponse 
	 * @param idReponse Identifiant de la reponse
	 * @param reponse Reponse de la reponse
	 * @param bonneReponse Bonne réponse de la reponse
	 */
	public Reponse(int idReponse, String reponse, Boolean bonneReponse){
		setIdReponse(idReponse);
		setReponse(reponse);
		setBonneReponse(bonneReponse);
	}
	
	/**
	 * d1410gerardm
	 * Classe Reponse
	 */
	public Reponse(){
		super();
	}

	/**
	 * Getter for idReponse
	 * @return the idReponse
	 */
	public int getIdReponse() {
		return idReponse;
	}

	/**
	 * Setter for idReponse
	 * @param idReponse the idReponse to set
	 */
	public void setIdReponse(int idReponse) {
		this.idReponse = idReponse;
	}

	/**
	 * Getter for reponse
	 * @return the reponse
	 */
	public String getReponse() {
		return reponse;
	}

	/**
	 * Setter for reponse
	 * @param reponse the reponse to set
	 */
	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

	/**
	 * Getter for bonneReponse
	 * @return the bonneReponse
	 */
	public Boolean getBonneReponse() {
		return bonneReponse;
	}

	/**
	 * Setter for bonneReponse
	 * @param bonneReponse the bonneReponse to set
	 */
	public void setBonneReponse(Boolean bonneReponse) {
		this.bonneReponse = bonneReponse;
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

}

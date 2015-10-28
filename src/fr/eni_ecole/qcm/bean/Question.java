/**
 * 20 oct. 2015
 * QCM V1.0
 * fr.eni_ecole.qcm.bean
 */
package fr.eni_ecole.qcm.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author d1410gerardm
 * 20 oct. 2015
 */
public class Question implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -4279151247222938496L;
	
	private int idQuestion;
	private Theme theme;
	private String enonce;
	private Boolean typeReponse;
	private String image;
	
	private List<Reponse> listeReponses;
	
	/**
	 * d1410gerardm
	 * Classe Question 
	 * @param idQuestion Identifiant de la question
	 * @param enonce Enonce de la question
	 * @param typeReponse type de réponse de la question
	 * @param image Image de la question
	 */
	public Question(int idQuestion, String enonce, Boolean typeReponse, String image){
		setIdQuestion(idQuestion);
		setEnonce(enonce);
		setTypeReponse(typeReponse);
		setImage(image);
	}
	
	/**
	 * d1410gerardm
	 * Classe Question
	 */
	public Question(){
		super();
	}

	/**
	 * Getter for idQuestion
	 * @return the idQuestion
	 */
	public int getIdQuestion() {
		return idQuestion;
	}

	/**
	 * Setter for idQuestion
	 * @param idQuestion the idQuestion to set
	 */
	public void setIdQuestion(int idQuestion) {
		this.idQuestion = idQuestion;
	}

	/**
	 * Getter for enonce
	 * @return the enonce
	 */
	public String getEnonce() {
		return enonce;
	}

	/**
	 * Setter for enonce
	 * @param enonce the enonce to set
	 */
	public void setEnonce(String enonce) {
		this.enonce = enonce;
	}

	/**
	 * Getter for typeReponse
	 * @return the typeReponse
	 */
	public Boolean getTypeReponse() {
		return typeReponse;
	}

	/**
	 * Setter for typeReponse
	 * @param typeReponse the typeReponse to set
	 */
	public void setTypeReponse(Boolean typeReponse) {
		this.typeReponse = typeReponse;
	}

	/**
	 * Getter for image
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * Setter for image
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * Getter for theme
	 * @return the theme
	 */
	public Theme getTheme() {
		return theme;
	}

	/**
	 * Setter for theme
	 * @param theme the theme to set
	 */
	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	/**
	 * Getter for listeReponses
	 * @return the listeReponses
	 */
	public List<Reponse> getListeReponses() {
		return listeReponses;
	}

	/**
	 * Setter for listeReponses
	 * @param listeReponses the listeReponses to set
	 */
	public void setListeReponses(List<Reponse> listeReponses) {
		this.listeReponses = listeReponses;
	}

	@Override
	public boolean equals(Object obj) {
		return this.idQuestion == ((Question)obj).getIdQuestion();
	}
	
}

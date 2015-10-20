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
public class Theme implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 2033690472758722923L;
	
	private int idTheme;
	private String libelle;
	
	private List<Section> listeSections;
	private List<Question> listeQuestions;
	
	/**
	 * d1410gerardm
	 * Classe Theme 
	 * @param idTheme Identifiant du theme
	 * @param libelle Libelle du theme
	 */
	public Theme(int idTheme, String libelle){
		setIdTheme(idTheme);
		setLibelle(libelle);
	}
	
	/**
	 * d1410gerardm
	 * Classe Theme
	 */
	public Theme(){
		super();
	}

	/**
	 * Getter for idTheme
	 * @return the idTheme
	 */
	public int getIdTheme() {
		return idTheme;
	}

	/**
	 * Setter for idTheme
	 * @param idTheme the idTheme to set
	 */
	public void setIdTheme(int idTheme) {
		this.idTheme = idTheme;
	}

	/**
	 * Getter for listeSections
	 * @return the listeSections
	 */
	public List<Section> getListeSections() {
		return listeSections;
	}

	/**
	 * Setter for listeSections
	 * @param listeSections the listeSections to set
	 */
	public void setListeSections(List<Section> listeSections) {
		this.listeSections = listeSections;
	}

	/**
	 * Getter for listeQuestions
	 * @return the listeQuestions
	 */
	public List<Question> getListeQuestions() {
		return listeQuestions;
	}

	/**
	 * Setter for listeQuestions
	 * @param listeQuestions the listeQuestions to set
	 */
	public void setListeQuestions(List<Question> listeQuestions) {
		this.listeQuestions = listeQuestions;
	}

	/**
	 * Getter for libelle
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * Setter for libelle
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

}

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
public class Section implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 834957358647674588L;

	private int numSection;
	private String idTest;
	private String idTheme;
	private int nbQuestion;
	
	/**
	 * d1410gerardm
	 * Classe Section 
	 * @param numSection numSection de la section
	 * @param idTest idTest de la section
	 * @param idTheme idTheme de la section
	 * @param nbQuestion nbQuestion de la section
	 */
	public Section(int numSection, String idTest, String idTheme, int nbQuestion){
		setNumSection(numSection);
		setIdTest(idTest);
		setIdTheme(idTheme);
		setNbQuestion(nbQuestion);
	}

	/**
	 * Getter for numSection
	 * @return the numSection
	 */
	public int getNumSection() {
		return numSection;
	}

	/**
	 * Setter for numSection
	 * @param numSection the numSection to set
	 */
	public void setNumSection(int numSection) {
		this.numSection = numSection;
	}

	/**
	 * Getter for idTest
	 * @return the idTest
	 */
	public String getIdTest() {
		return idTest;
	}

	/**
	 * Setter for idTest
	 * @param idTest the idTest to set
	 */
	public void setIdTest(String idTest) {
		this.idTest = idTest;
	}

	/**
	 * Getter for idTheme
	 * @return the idTheme
	 */
	public String getIdTheme() {
		return idTheme;
	}

	/**
	 * Setter for idTheme
	 * @param idTheme the idTheme to set
	 */
	public void setIdTheme(String idTheme) {
		this.idTheme = idTheme;
	}

	/**
	 * Getter for nbQuestion
	 * @return the nbQuestion
	 */
	public int getNbQuestion() {
		return nbQuestion;
	}

	/**
	 * Setter for nbQuestion
	 * @param nbQuestion the nbQuestion to set
	 */
	public void setNbQuestion(int nbQuestion) {
		this.nbQuestion = nbQuestion;
	}
	
}

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
	private Test test;
	private Theme theme;
	private int nbQuestion;
	
	/**
	 * d1410gerardm
	 * Classe Section 
	 * @param numSection numSection de la section
	 * @param idTest idTest de la section
	 * @param idTheme idTheme de la section
	 * @param nbQuestion nbQuestion de la section
	 */
	public Section(int numSection, Test test, Theme theme, int nbQuestion){
		setNumSection(numSection);
		setTest(test);
		setTheme(theme);
		setNbQuestion(nbQuestion);
	}
	
	/**
	 * d1410gerardm
	 * Classe Section
	 */
	public Section(){
		super();
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

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
public class Test implements Serializable {
	
	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 5123537307675054395L;
	
	private String libelle;
	private int duree;
	private int seuil_minimum;
	private int seuil_maximum;
	
	private List<Section> listeSections;
	
	public Test(){
		super();
	}
	/**
	 * d1410gerardm
	 * Classe Test 
	 * @param libelle Libelle du test
	 * @param duree Duree du test
	 * @param seuil_minimum Seuil_minimum du test
	 * @param seuil_maximum Seuil_maximum du test
	 */
	public Test(String libelle, int duree, int seuil_minimum, int seuil_maximum){
		setLibelle(libelle);
		setDuree(duree);
		setSeuil_minimum(seuil_minimum);
		setSeuil_maximum(seuil_maximum);
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

	/**
	 * Getter for duree
	 * @return the duree
	 */
	public int getDuree() {
		return duree;
	}

	/**
	 * Setter for duree
	 * @param duree the duree to set
	 */
	public void setDuree(int duree) {
		this.duree = duree;
	}

	/**
	 * Getter for seuil_minimum
	 * @return the seuil_minimum
	 */
	public int getSeuil_minimum() {
		return seuil_minimum;
	}

	/**
	 * Setter for seuil_minimum
	 * @param seuil_minimum the seuil_minimum to set
	 */
	public void setSeuil_minimum(int seuil_minimum) {
		this.seuil_minimum = seuil_minimum;
	}

	/**
	 * Getter for seuil_maximum
	 * @return the seuil_maximum
	 */
	public int getSeuil_maximum() {
		return seuil_maximum;
	}

	/**
	 * Setter for seuil_maximum
	 * @param seuil_maximum the seuil_maximum to set
	 */
	public void setSeuil_maximum(int seuil_maximum) {
		this.seuil_maximum = seuil_maximum;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getLibelle();
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
	
}

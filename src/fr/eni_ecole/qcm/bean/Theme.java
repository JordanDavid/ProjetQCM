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
public class Theme implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 2033690472758722923L;
	
	private String libelle;
	
	/**
	 * d1410gerardm
	 * Classe Theme 
	 * @param libelle Libelle du theme
	 */
	public Theme(String libelle){
		setLibelle(libelle);
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

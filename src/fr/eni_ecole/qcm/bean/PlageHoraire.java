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
public class PlageHoraire implements Serializable {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 4360946030275702597L;
	
	private int idPlageHoraire;
	private Date dateDebut;
	private Date dateFin;
	
	/**
	 * d1410gerardm
	 * Classe PlageHoraire 
	 * @param idPlageHoraire Identifiant de la plage horaire
	 * @param dateDebut Date début de la plage horaire
	 * @param dateFin Date fin de la plage horaire
	 */
	public PlageHoraire(int idPlageHoraire, Date dateDebut, Date dateFin){
		setIdPlageHoraire(idPlageHoraire);
		setDateDebut(dateDebut);
		setDateFin(dateFin);
	}
	
	/**
	 * d1410gerardm
	 * Classe PlageHoraire
	 */
	public PlageHoraire(){
		super();
	}

	/**
	 * Getter for idPlageHoraire
	 * @return the idPlageHoraire
	 */
	public int getIdPlageHoraire() {
		return idPlageHoraire;
	}

	/**
	 * Setter for idPlageHoraire
	 * @param idPlageHoraire the idPlageHoraire to set
	 */
	public void setIdPlageHoraire(int idPlageHoraire) {
		this.idPlageHoraire = idPlageHoraire;
	}

	/**
	 * Getter for dateDebut
	 * @return the dateDebut
	 */
	public Date getDateDebut() {
		return dateDebut;
	}

	/**
	 * Setter for dateDebut
	 * @param dateDebut the dateDebut to set
	 */
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * Getter for dateFin
	 * @return the dateFin
	 */
	public Date getDateFin() {
		return dateFin;
	}

	/**
	 * Setter for dateFin
	 * @param dateFin the dateFin to set
	 */
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

}

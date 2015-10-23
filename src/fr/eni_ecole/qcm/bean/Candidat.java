/**
 * 20 oct. 2015
 * QCM V1.0
 * fr.eni_ecole.qcm.bean
 * Test commit
 */
package fr.eni_ecole.qcm.bean;

/**
 * @author d1410gerardm
 * 20 oct. 2015
 */
public class Candidat extends Utilisateur {
	
	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -4273366316477984032L;

	/**
	 * d1410gerardm
	 * Classe Candidat
	 */
	public Candidat(){
		super();
	}
	
	/**
	 * d1410gerardm
	 * Classe Candidat 
	 * @param id Identifiant du candidat
	 */
	public Candidat(int id){
		super(id);
	}
	
	/**
	 * d1410gerardm
	 * Classe Candidat
	 * @param id Identifiant du candidat
	 * @param login Login du candidat
	 * @param motdepasse Mot de passe du candidat
	 * @param nom Nom du candidat
	 * @param prenom Prenom du candidat
	 * @param mail Mail du candidat
	 */
	public Candidat(int id, String login, String motdepasse, String nom, String prenom, String mail){
		super(id, login, motdepasse, nom, prenom, mail);
	}
	
}

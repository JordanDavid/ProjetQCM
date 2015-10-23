/**
 * 20 oct. 2015
 * QCM V1.0
 * fr.eni_ecole.qcm.bean
 */
package fr.eni_ecole.qcm.bean;

/**
 * @author d1410gerardm
 * 20 oct. 2015
 */
public class Formateur extends Utilisateur {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 7333228723649991130L;
	
	/**
	 * d1410gerardm
	 * Classe Formateur
	 */
	public Formateur(){
		super();
	}
	
	/**
	 * d1410gerardm
	 * Classe Formateur
	 * @param id Identifiant du formateur
	 */
	public Formateur(int id){
		super(id);
	}
	
	/**
	 * d1410gerardm
	 * Classe Formateur
	 * @param id Identifiant du formateur
	 * @param login Login du formateur
	 * @param motdepasse Mot de passe du formateur
	 * @param nom Nom du formateur
	 * @param prenom Prenom du formateur
	 * @param mail Mail du formateur
	 * @param statut Statut du formateur
	 */
	public Formateur(int id, String login, String motdepasse, String nom, String prenom, String mail, String statut){
		super(id, login, motdepasse, nom, prenom, mail, statut);
	}

}

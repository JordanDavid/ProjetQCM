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
public class ResponsableFormation extends Utilisateur {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = -4568069331571744426L;
	
	/**
	 * d1410gerardm
	 * Classe ResponsableFormation
	 */
	public ResponsableFormation(){
		super();
	}
	
	/**
	 * d1410gerardm
	 * Classe ResponsableFormation 
	 * @param id Identifiant du responsable de formation
	 */
	public ResponsableFormation(int id){
		super(id);
	}
	
	/**
	 * d1410gerardm
	 * Classe ResponsableFormation 
	 * @param id Identifiant du responsable de formation
	 * @param login Login du responsable de formation
	 * @param motdepasse Mot de passe du responsable de formation
	 * @param nom Nom du responsable de formation
	 * @param prenom Prenom du responsable de formation
	 * @param mail Mail du responsable de formation
	 */
	public ResponsableFormation(int id, String login, String motdepasse, String nom, String prenom, String mail){
		super(id, login, motdepasse, nom, prenom, mail);
	}

}

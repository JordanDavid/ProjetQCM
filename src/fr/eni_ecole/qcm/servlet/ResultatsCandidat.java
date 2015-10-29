package fr.eni_ecole.qcm.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni_ecole.qcm.bean.Resultat;
import fr.eni_ecole.qcm.bean.Tirage;
import fr.eni_ecole.qcm.bean.Utilisateur;
import fr.eni_ecole.qcm.dal.DALInscription;
import fr.eni_ecole.qcm.dal.DALResultat;
import fr.eni_ecole.qcm.dal.DALTest;
import fr.eni_ecole.qcm.dal.DALTirage;

/**
 * Servlet implementation class ResultatsCandidat
 */
public class ResultatsCandidat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResultatsCandidat() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.service(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatcher = null;
		Utilisateur user = (Utilisateur)request.getSession().getAttribute("user");
		
		try{		
			
			//Récupère le test concerné
			fr.eni_ecole.qcm.bean.Test test = new fr.eni_ecole.qcm.bean.Test();
			test.setId(Integer.parseInt(request.getParameter("idTest")));
			test = DALTest.getTest(test);
			
			//Récupère l'inscription
			fr.eni_ecole.qcm.bean.Inscription inscription = 
					DALInscription.getInscriptionByUserTest(test, user);
			
			//Nombre total de question
			int totalQuestion = DALInscription.getNbTotalQuestion(inscription);
			
			//On va créer le résultat
			Resultat resultat = new Resultat();
			resultat.setTest(test);
			resultat.setInscription(inscription);
			resultat.setUtilisateur(user);

			//Calcul du nombre de bonne réponse question pour le test
//			resultat.setNbBonnesReponses(DALInscription.getNbBonnesReponse(inscription));
			
			//Nombre d'incidents
			resultat.setNbIncidents(0);
			
			//Calcul de question question bien répondue
			List<Tirage> tirages = (List<Tirage>)request.getSession().getAttribute("tirage");
			
			int nbbonneQuestion = 0;
			for (Tirage t : tirages){
				t.setInscription(inscription);

				int bonneReponseUser = DALTirage.getNbBonneReponseUtilisateur(t);
				int bonneReponsePossible = DALTirage.getNbBonneReponseTirage(t);
				if(bonneReponseUser == bonneReponsePossible)
					nbbonneQuestion ++;
			}
			
			//Nombre de question valide
			resultat.setNbBonnesReponses(nbbonneQuestion);
			
			//Nombre de questions totales
			resultat.setTotalQuestion(DALInscription.getNbTotalQuestion(inscription));
			
			

			//note sur 20 (Seuil atteint)
			int noteSurVingt = (nbbonneQuestion*20)/totalQuestion;
			if(noteSurVingt >0 && noteSurVingt < test.getSeuil_minimum() ){
				resultat.setSeuilAtteint("Non acquis");
			}else if(noteSurVingt >= test.getSeuil_minimum() && noteSurVingt < test.getSeuil_maximum()){
				resultat.setSeuilAtteint("En cours d'acquisition");
			} else{
				resultat.setSeuilAtteint("Acquis");
			}
			
			
			//Ajout du résultat en base de données
			resultat = DALResultat.ajouter(resultat);
			
			dispatcher = request.getRequestDispatcher("/candidat/resultats.jsp");
			request.setAttribute("test", test);
			request.setAttribute("resultat", resultat);
			dispatcher.forward(request, response);		
			
		}catch(Exception e){
			dispatcher = request.getRequestDispatcher("/erreur.jsp");
			request.setAttribute("erreur", e);
			dispatcher.forward(request, response);
		}
		
	}

}

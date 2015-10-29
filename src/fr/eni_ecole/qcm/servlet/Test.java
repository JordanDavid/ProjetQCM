package fr.eni_ecole.qcm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni_ecole.qcm.bean.*;
import fr.eni_ecole.qcm.dal.DALInscription;
import fr.eni_ecole.qcm.dal.DALQuestion;
import fr.eni_ecole.qcm.dal.DALReponse;
import fr.eni_ecole.qcm.dal.DALSection;
import fr.eni_ecole.qcm.dal.DALTest;
import fr.eni_ecole.qcm.dal.DALTirage;

/**
 * Servlet implementation class Test
 */
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Test() {
        super();
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
		;
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
		
		String action= request.getParameter("action");
		Utilisateur user = (Utilisateur)request.getSession().getAttribute("user");
		
		PrintWriter out = response.getWriter();
		
		try{
			if("selection".equals(action)){
				List<fr.eni_ecole.qcm.bean.Test> tests = DALTest.getTestByUser(user);
				dispatcher= request.getRequestDispatcher("/candidat/selectionTest.jsp");
				request.setAttribute("tests", tests);
				dispatcher.forward(request, response);
			}else if("lancerTest".equals(action)) {
					//Récupère le test concerné
					fr.eni_ecole.qcm.bean.Test test = new fr.eni_ecole.qcm.bean.Test();
					test.setId(Integer.parseInt(request.getParameter("idTest")));
					test = DALTest.getTest(test);
					
					//Récupère l'inscription
					fr.eni_ecole.qcm.bean.Inscription inscription = 
							DALInscription.getInscriptionByUserTest(test, user);

					//récupère la liste des sections du test
					List<Section> sections = DALSection.selectSectionsByTest(test);
					
					//Va contenir le tirage
					List<Tirage> tirages = new ArrayList<Tirage>();
					
					//récupère l'ensemble des questions du thème de la section
					for(Section s : sections){
						//nombre de question dans la section
						int nbQuestionSection = s.getNbQuestion();
						//Toutes les questions du thème
						List<Question> questionsTotales =  DALQuestion.getQuestionByTheme(s.getTheme());
						//Va contenir les questions tirées
						List<Question> questionsTirage = new ArrayList<Question>();
						//Variable pour le tirage aléatoire
						Random rdm = new Random();
						//numéro du tirage en cours
						int numTirage = 0;
						
						while(numTirage < nbQuestionSection){
							Boolean ok = false;
							//Tant que le tirage n'est pas valide
							while (!ok){
								//Tirage entre 0 et le nombre max de questions
								int valTirage = rdm.nextInt(questionsTotales.size());
								//on récupère la question
								Question q = questionsTotales.get(valTirage);
								
								//Si elle est pas déjà tirée on la prend sinon on reboucle
								if(!questionsTirage.contains(q)){
									//On ajoute aux questions tirées
									questionsTirage.add(q);
									//On créer le tirage et on l'ajoute à la liste des tirages
									Tirage t = new Tirage();
									t.setQuestion(q);
									t.setUtilisateur(user);
									t.setTest(test);
									t.setInscription(inscription);
									t.setEnCours(false);
									t.setMarque(false);
									DALTirage.ajouter(t);
									tirages.add(t);
									
									numTirage++;
									ok = true;
								}
							}
						}						
					}
						
					//Démarre le test
					DALInscription.demarrerTest(inscription);
					
					//Stock dans la variable de session le tirage complet et le debut du test
					HttpSession session = request.getSession();
					session.setAttribute("tirage", tirages);
					session.setAttribute("test", test);
					session.setAttribute("debut", new Date());
					

					dispatcher= request.getRequestDispatcher("/candidat/test?action=afficher&q=1");
					dispatcher.forward(request, response);
					
			} else if("recapitulatif".equals(action)){
				//indique que l'on veut afficher la page récapitulative
				request.setAttribute("recapitulatif", true);
				
				//Si on a un paramètre q c'est que l'on souhaite enregistrer la page (Bouton terminer)
				//Sinon on redirigie vers la page (Lien récapitulatif)
				if(request.getParameter("q") != null){
					int q = Integer.parseInt(request.getParameter("q"));				
					dispatcher= request.getRequestDispatcher("/candidat/test?action=enregistrer&q="+(q+1));			
				} else{
					dispatcher= request.getRequestDispatcher("/candidat/recapitulatif.jsp");
				}
				
				//redirige selon le dispatcher
				dispatcher.forward(request, response);		
				
			} else if("terminer".equals(action)){
				//Récupère le test concerné
				fr.eni_ecole.qcm.bean.Test test = new fr.eni_ecole.qcm.bean.Test();
				test.setId(Integer.parseInt(request.getParameter("idTest")));
				test = DALTest.getTest(test);
				
				//Récupère l'inscription
				fr.eni_ecole.qcm.bean.Inscription inscription = 
						DALInscription.getInscriptionByUserTest(test, user);
				
				//traitement lorsque le test est terminé => redirection vers la page des résultats
				DALInscription.finirTest(inscription);
				
				//Redirige vers la page des résultats
				dispatcher = request.getRequestDispatcher("/candidat/resultats?idTest="+test.getId());
				dispatcher.forward(request, response);
				
			} else {				
				//Récupère le tirage de l'utilisateur
				List<Tirage> tirages = (List<Tirage>)request.getSession().getAttribute("tirage");

				//Numéro de la question en cours
				int numQuestion = Integer.parseInt(request.getParameter("q"));
				
				if("enregistrer".equals(action)){
					Tirage tirageAEnregister = null;
					
					//Récupérer les id des questions cochés dans l'attribut reponses
					tirageAEnregister = tirages.get(numQuestion-2);

					if(request.getParameterValues("reponses") != null){
					
						//Récupère les réponses de l'utilisateur
						List<String> lstReponsesUser = Arrays.asList(request.getParameterValues("reponses"));
	
						//Récupère les réponses existantes pour ce tirage en base de données
						List<Reponse>lstReponseTirageAEnregistrer = DALReponse.getReponseAuTirage(tirageAEnregister);
					
						for(String r : lstReponsesUser){
							//Construit une réponse en fonction de son identifiant
							Reponse reponse = new Reponse();
							reponse.setIdReponse(Integer.parseInt(r));
							
							//Si l'identifiant passé n'existe pas dans la liste des réponses existant dans
							//la base de donnée pour ce tirage. On ajoute à la BD
							if(lstReponseTirageAEnregistrer==null 
									||(lstReponseTirageAEnregistrer != null 
										&& !lstReponseTirageAEnregistrer.contains(reponse))){
								//Lie une réponse au tirage
								DALReponse.ajouterReponseAuTirage(tirageAEnregister,reponse);
							}
							
							//Chaque élément enregistrer n'est pas à supprimer de la BD
							lstReponseTirageAEnregistrer.remove(reponse);
						}
						
						//pour chaque réponse restantes on les supprimes
						for(Reponse r : lstReponseTirageAEnregistrer){
							DALReponse.supprimerReponseAuTirage(tirageAEnregister, r);
						}
						
					}
					
					//Si la question est marqué on le note en tant que tirage marqué
					if(request.getParameter("marquer") != null){
						tirages.get(numQuestion-2).setMarque(true);
						DALTirage.marquerQuestion(tirageAEnregister);	
					}
				}
					
				if(numQuestion <= tirages.size()){
					//Récupère le tirage en cours
					Tirage tirage = tirages.get(numQuestion-1);				
					
					//récupère la liste des réponses existante pour ce tirage sinon null
					List<Reponse>reponsesTirage = DALReponse.getReponseAuTirage(tirage);
					
					//Récupère la question en cours
					Question question = tirage.getQuestion();
	
					//Enregistre la question en tantque question tirage en cours 
					DALTirage.questionEnCours(tirage);
					
					//Récupère la liste des réponses pour cette question
					List<Reponse>reponses = DALReponse.selectByThemeQuestion(question);
					
					request.setAttribute("question", question);
					request.setAttribute("reponses", reponses);
					request.setAttribute("reponsesTirage", reponsesTirage);
				}
				
				
				//Si on est rendu à la dernière question on affiche le bouton terminer
				//Sinon c'est le bouton suivant
				if(numQuestion == tirages.size() )
					request.setAttribute("terminer", true);
				else
					request.setAttribute("terminer",false);
				
				//Si on a l'attribut "recapitulatif on redirige vers cette page
				//Sinnon on affiche la question
				if(request.getAttribute("recapitulatif") == null){
					dispatcher= request.getRequestDispatcher("/candidat/test.jsp");
					request.setAttribute("q",numQuestion);
					dispatcher.forward(request, response);
				}else {
					dispatcher= request.getRequestDispatcher("/candidat/recapitulatif.jsp");
					dispatcher.forward(request, response);
				}
			}
		}catch(Exception e){
			dispatcher = request.getRequestDispatcher("/erreur.jsp");
			request.setAttribute("erreur", e);
			dispatcher.forward(request, response);
		}
	}


}

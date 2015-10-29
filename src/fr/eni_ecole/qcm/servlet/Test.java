package fr.eni_ecole.qcm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
					out.println(inscription.getIdInscription());
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
						
					//Stock dans la variable de session le tirage complet et le debut du test
					HttpSession session = request.getSession();
					session.setAttribute("tirage", tirages);
					session.setAttribute("debut", new Date());
					

					dispatcher= request.getRequestDispatcher("/candidat/test?action=afficher&q=1");
					dispatcher.forward(request, response);
					
			} else if("terminer".equals(action)){
				//traitement lorsque le test est terminé => redirection vers la page des résultats
				
				
			} else {				
				//Récupère le tirage de l'utilisateur
				List<Tirage> tirages = (List<Tirage>)request.getSession().getAttribute("tirage");

				//Numéro de la question en cours
				int numQuestion = Integer.parseInt(request.getParameter("q"));
				
				//Récupère le tirage
				Tirage tirage = tirages.get(numQuestion-1);
				
				//récupère la liste des réponses existante pour ce tirage sinon null
				List<Reponse>reponsesTirage = DALReponse.getReponseAuTirage(tirage);
				
				if("enregistrer".equals(action)){
					//Récupérer les id des questions cochés dans l'attribut reponses
					Tirage tirageAEnregister = tirages.get(numQuestion-2);
					//Récupère les réponses de l'utilisateur
					String[] lstReponses = request.getParameterValues("reponses");

					//Récupère les réponses existantes pour ce tirage en base de données
//					List<Reponse>lstReponseirageAEnregistrer = DALTirage.getQuestionByTirage(tirageAEnregister);
					
					if(lstReponses != null){
						for(int i=0; i<lstReponses.length;i++){
							//Construit une réponse en fonction de son identifiant
							Reponse reponse = new Reponse();
							reponse.setIdReponse(Integer.parseInt(lstReponses[i]));
							//Lie une réponse au tirage
							DALReponse.ajouterReponseAuTirage(tirageAEnregister,reponse);
						}
					}
					
					//Si la question est marqué on le note en tant que tirage marqué
					if(request.getParameter("marquer") != null){
						tirages.get(numQuestion-2).setMarque(true);
						DALTirage.marquerQuestion(tirageAEnregister);	
					}
				}
				
				//Récupère la question en cours
				Question question = tirage.getQuestion();

				//Enregistre la question en tantque question tirage en cours 
				DALTirage.questionEnCours(tirage);
				
				//Récupère la liste des réponses pour cette question
				List<Reponse>reponses = DALReponse.selectByThemeQuestion(question);
				
				dispatcher= request.getRequestDispatcher("/candidat/test.jsp");
				request.setAttribute("question", question);
				request.setAttribute("reponses", reponses);
				request.setAttribute("reponsesTirage", reponsesTirage);
				
				if(numQuestion == tirages.size())
					request.setAttribute("terminer", true);
				else
					request.setAttribute("terminer",false);
				

				request.setAttribute("q",numQuestion);
				dispatcher.forward(request, response);
			}
		}catch(Exception e){
			dispatcher = request.getRequestDispatcher("/erreur.jsp");
			request.setAttribute("erreur", e);
			dispatcher.forward(request, response);
		}
	}


}

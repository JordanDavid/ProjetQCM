package fr.eni_ecole.qcm.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni_ecole.qcm.bean.*;
import fr.eni_ecole.qcm.dal.DALQuestion;
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
		Tirage tirage = new Tirage();
		
		try{
			if("selection".equals(action)){
				List<fr.eni_ecole.qcm.bean.Test> tests = DALTest.getTestByUser(user);
				dispatcher= request.getRequestDispatcher("/candidat/selectionTest.jsp");
				request.setAttribute("tests", tests);
				dispatcher.forward(request, response);
			}else{
				int idQuestion = (int) request.getAttribute("q");
				
				if("lancerTest".equals(action)) {
					//générer un tirage.
					//Rajouter un ordre de la question ?
					
				} else if("enregisterReponse".equals(action)){
					//Récupérer les id des questions cochés
					
				}else if("marquerQuestion".equals("action")){
					//Récupérerles id pour question à marquer
					
//					DALTirage.marquerQuestion(tirage);
				}
							
				
				//Comment récupérer la question dutirage en cours
				Question question = DALTirage.getQuestionByTirage(tirage);
				
				//Enregistre la question en tantque question tirage en cours 
				DALTirage.questionEnCours(tirage);
				
				dispatcher= request.getRequestDispatcher("/candidat/test.jsp");
				request.setAttribute("question", question);
				dispatcher.forward(request, response);
			}
		}catch(Exception e){
			dispatcher = request.getRequestDispatcher("/erreur");
			request.setAttribute("erreur", e);
			dispatcher.forward(request, response);
		}
	}


}

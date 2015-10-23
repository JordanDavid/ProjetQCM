package fr.eni_ecole.qcm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import fr.eni_ecole.qcm.bean.Question;
import fr.eni_ecole.qcm.bean.Reponse;
import fr.eni_ecole.qcm.bean.Theme;
import fr.eni_ecole.qcm.dal.DALQuestion;
import fr.eni_ecole.qcm.dal.DALReponse;
import fr.eni_ecole.qcm.dal.DALTheme;

/**
 * Servlet implementation class Referentiel
 */
public class Referentiel extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Referentiel() {
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
		String action = request.getParameter("action");
		List<Theme> themes = null;
		Gson gson = null;
		
		try {
			if("getQuestions".equals(action)){

				HashMap<String, List<Question>> map = new HashMap<String, List<Question>>();
				gson = new Gson();
				
				Theme theme = new Theme();
				theme.setIdTheme(Integer.parseInt(request.getParameter("id"))); ;
				
				List<Question> questions = DALQuestion.getQuestionByTheme(theme);
				
				response.setContentType("application/json");        
				response.setHeader("Cache-Control", "no-store");
				
				map.put("data", questions);
				
				PrintWriter out = response.getWriter();		
				out.println(gson.toJson(map));
				out.flush();
			}else if("getReponses".equals(action)){
				HashMap<String, List<Reponse>> map = new HashMap<String, List<Reponse>>();
				gson = new Gson();
				
				Theme theme = new Theme();
				theme.setIdTheme(Integer.parseInt(request.getParameter("idTheme"))); ;
				Question question = new Question();
				question.setIdQuestion(Integer.parseInt(request.getParameter("idQuestion")));
				
				List<Reponse>reponses = DALReponse.selectByThemeQuestion(theme, question);
				
				response.setContentType("application/json");        
				response.setHeader("Cache-Control", "no-store");
				
				map.put("data", reponses);
				
				PrintWriter out = response.getWriter();		
				out.println(gson.toJson(map));
				out.flush();				
			}else if("enregistrerQuestion".equals(action)){
				gson = new Gson();

				//Récupère les paramètre de la requête
				String enonce = request.getParameter("enonce");
				String image = request.getParameter("image");					
				Boolean typeQuestion = Boolean.parseBoolean(request.getParameter("typeQuestion"));
				
				PrintWriter out = response.getWriter();
				
				Enumeration<String> params = request.getParameterNames();
				
				while(params.hasMoreElements()){
					out.println(params.nextElement());
				}
				
				Type listType = new TypeToken<List<String>>() {}.getType();
				List<String> reponses =  new Gson().fromJson(request.getParameter("lst_reponses"),listType);

				out.println(reponses);
				
				out.flush();
				out.close();
				
//				String enonce = "test";
//				String image = null;
//				Boolean typeQuestion = true;
//				
//				//Récupère le thème concerné
//				int idTheme = Integer.parseInt(request.getParameter("idTheme"));
//				Theme theme = new Theme();
//				theme.setIdTheme(1);
//				
				//Création de la question
//				Question question = new Question();
//				question.setEnonce(enonce);
//				question.setImage(image);
//				question.setTypeReponse(typeQuestion);
//				question = DALQuestion.ajouter(theme,question);
				
			} else {
				
				if("ajoutTheme".equals(action)){
					String libelle = request.getParameter("libelle_theme");
					Theme theme = new Theme();
					theme.setLibelle(libelle);
					theme = DALTheme.ajouter(theme);
				}else if("supprimerTheme".equals(action)){
					int idTheme = Integer.parseInt(request.getParameter("idTheme"));
					Theme theme = new Theme();
					theme.setIdTheme(idTheme);
					DALTheme.supprimer(theme);
				} 
				
				themes = DALTheme.selectAll();				
				dispatcher = request.getRequestDispatcher("/formateur/gestionReferentiel.jsp");
				request.setAttribute("themes", themes);
				dispatcher.forward(request, response);
			}
			
		} catch (Exception e) {
			dispatcher = request.getRequestDispatcher("/erreur.jsp");
			request.setAttribute("erreur", e);
			dispatcher.forward(request, response);
		}
	}

}

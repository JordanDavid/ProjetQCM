package fr.eni_ecole.qcm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.google.gson.reflect.TypeToken;
import org.json.*;

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
		JSONObject json = null;
		
		try {
			if("getQuestions".equals(action)){

				HashMap<String, List<Question>> map = new HashMap<String, List<Question>>();
								
				Theme theme = new Theme();
				theme.setIdTheme(Integer.parseInt(request.getParameter("id"))); ;
				
				List<Question> questions = DALQuestion.getQuestionByTheme(theme);
				
				response.setContentType("application/json");        
				response.setHeader("Cache-Control", "no-store");
				
				map.put("data", questions);
				
				json = new JSONObject(map);
				
				PrintWriter out = response.getWriter();		
				out.println(json.toString());
				out.flush();
			}else if("getReponses".equals(action)){
				HashMap<String, List<Reponse>> map = new HashMap<String, List<Reponse>>();
				
				Theme theme = new Theme();
				theme.setIdTheme(Integer.parseInt(request.getParameter("idTheme"))); ;
				Question question = new Question();
				question.setIdQuestion(Integer.parseInt(request.getParameter("idQuestion")));
				question.setTheme(theme);
				
				List<Reponse>reponses = DALReponse.selectByThemeQuestion(question);
				
				response.setContentType("application/json");        
				response.setHeader("Cache-Control", "no-store");
				
				map.put("data", reponses);

				json = new JSONObject(map);
				
				PrintWriter out = response.getWriter();		
				out.println(json.toString());
				out.flush();				
			}else {
				
				if("ajoutTheme".equals(action)){
					String libelle = request.getParameter("libelle_theme");
					Theme theme = new Theme();
					theme.setLibelle(libelle);
					theme = DALTheme.ajouter(theme);
				}else if("supprimerTheme".equals(action)){
					Theme theme = new Theme();
					theme.setIdTheme(Integer.parseInt(request.getParameter("idTheme")));
					DALTheme.supprimer(theme);
				} else if("changerThemeQuestion".equals(action)){
					Theme theme = new Theme();
					theme.setIdTheme(Integer.parseInt(request.getParameter("idTheme")));
					Question question = new Question();
					question.setIdQuestion(Integer.parseInt(request.getParameter("idQuestion")));
					question.setTheme(theme);
					DALQuestion.changerTheme(question);
				} else if("supprimerQuestion".equals(action)){
					Question question = new Question();
					question.setIdQuestion(Integer.parseInt(request.getParameter("idQuestion")));
					DALQuestion.supprimer(question);
				} else if("enregistrerQuestion".equals(action)){
					//Récupère les paramètre de la requête
					String enonce = request.getParameter("enonce");
					String image = request.getParameter("image");					
					Boolean typeQuestion = Integer.parseInt(request.getParameter("typeQuestion")) == 1 ? true : false;

					//Récupère le thème concerné
					Theme theme = new Theme();
					theme.setIdTheme(Integer.parseInt(request.getParameter("theme")));
					
					//Création de la question
					Question question = new Question();
					question.setEnonce(enonce);
					question.setImage(image);
					question.setTypeReponse(typeQuestion);
					question.setTheme(theme);
					
					if((Integer.parseInt(request.getParameter("idQuestion"))) == 0){
						question = DALQuestion.ajouter(question);						
					}else{
						question.setIdQuestion(Integer.parseInt(request.getParameter("idQuestion")));
						DALQuestion.modifier(question);
					}

					//récupère la liste des réponses
					JSONArray reponses = new JSONArray(request.getParameter("lst_reponses"));				
					for(int i=0; i < reponses.length();i++){
						if(!reponses.isNull(i)){
							JSONObject reponse = reponses.getJSONObject(i);
							Reponse r = new Reponse();
							r.setReponse(reponse.getString("reponse"));
							r.setIdReponse(reponse.getInt("idReponse"));
							r.setBonneReponse(reponse.getBoolean("bonneReponse"));
							r.setQuestion(question);
							
							if(r.getIdReponse()<1){
								DALReponse.ajouter(r);
							}else{
								DALReponse.modifier(r);
							}
						}
					}	
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

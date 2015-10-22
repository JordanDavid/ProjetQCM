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





import com.google.gson.Gson;

import fr.eni_ecole.qcm.bean.Question;
import fr.eni_ecole.qcm.bean.Reponse;
import fr.eni_ecole.qcm.bean.Theme;
import fr.eni_ecole.qcm.dal.DALQuestion;
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
			if("afficher".equals(action)){
				themes = DALTheme.selectAll();				
				dispatcher = request.getRequestDispatcher("/formateur/gestionReferentiel.jsp");
				request.setAttribute("themes", themes);
				dispatcher.forward(request, response);
			} else if("getQuestions".equals(action)){

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
				
				
				
			}
			
		} catch (Exception e) {
			dispatcher = request.getRequestDispatcher("/erreur.jsp");
			request.setAttribute("erreur", e);
			dispatcher.forward(request, response);
		}
	}

}

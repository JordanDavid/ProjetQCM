package fr.eni_ecole.qcm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import fr.eni_ecole.qcm.bean.Test;
import fr.eni_ecole.qcm.bean.Theme;
import fr.eni_ecole.qcm.bean.Utilisateur;
import fr.eni_ecole.qcm.dal.DALTest;
import fr.eni_ecole.qcm.dal.DALTheme;
import fr.eni_ecole.qcm.dal.DALUtilisateur;

/**
 * Servlet implementation class Inscription
 */
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Inscription() {
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
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.service(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
	
	/**
	 * M�thode en charge de lister les candidats  
	 * 21 oct. 2015
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		String action = request.getParameter("action");
		List<Utilisateur> listeCandidats = new ArrayList<Utilisateur>();
		List<Theme> themes = null;
		Gson gson = null;
		
		try{
			if("getTests".equals(action)){				
				HashMap<String, List<Test>> map = new HashMap<String, List<Test>>();
				gson = new Gson();
				
				Theme theme = new Theme();
				theme.setIdTheme(Integer.parseInt(request.getParameter("id")));
				
				List<Test> tests = DALTest.getTestByTheme(theme);
				
				response.setContentType("application/json");        
				response.setHeader("Cache-Control", "no-store");
				
				map.put("data", tests);
				
				PrintWriter out = response.getWriter();		
				out.println(gson.toJson(map));
				out.flush();
			} else if("getTestsPlageHoraire".equals(action)){
				// affichage des informations de la popup
				HashMap<String, List<Test>> map = new HashMap<String, List<Test>>();
				gson = new Gson();
				
				Theme theme = new Theme();
				theme.setIdTheme(Integer.parseInt(request.getParameter("id")));
				
				List<Test> tests = DALTest.getTestByTheme(theme);
				
				response.setContentType("application/json");        
				response.setHeader("Cache-Control", "no-store");
				
				map.put("data", tests);
				
				PrintWriter out = response.getWriter();		
				out.println(gson.toJson(map));
				out.flush();
			} else if ("ajoutCandidatToTheme".equals(action)){
				//il faut récupérer les informations de la pop et faire la création				
			} else {
				dispatcher = request.getRequestDispatcher("/formateur/inscription.jsp");
				listeCandidats = DALUtilisateur.selectAll();
				request.setAttribute("candidats", listeCandidats);
				
				themes = DALTheme.selectAll();			
				request.setAttribute("themes", themes);
				
				dispatcher.forward(request, response);
			}
			
			
		}catch (SQLException e){
			dispatcher = request.getRequestDispatcher("/erreur.jsp");
			request.setAttribute("erreur", e);
			dispatcher.forward(request, response);
		}
	}
}

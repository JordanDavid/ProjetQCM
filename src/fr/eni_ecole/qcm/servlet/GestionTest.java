package fr.eni_ecole.qcm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.eni_ecole.qcm.bean.*;
import fr.eni_ecole.qcm.dal.DALSection;
import fr.eni_ecole.qcm.dal.DALTest;
import fr.eni_ecole.qcm.dal.DALTheme;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class GestionTest
 */
public class GestionTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionTest() {
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
		
//		String action = request.getParameter("action") == null ? "afficher" : request.getParameter("action");
		String action = request.getParameter("action");
		JSONObject json = null;
		RequestDispatcher dispatcher = null;
		
		try{
			if("ajouterTest".equals(action)){
				
			}else if("getTests".equals(action)){
				HashMap<String, List<fr.eni_ecole.qcm.bean.Test>> map = 
						new HashMap<String, List<fr.eni_ecole.qcm.bean.Test>>();
				
				List<fr.eni_ecole.qcm.bean.Test> tests = DALTest.selectAll();
				
				response.setContentType("application/json");        
				response.setHeader("Cache-Control", "no-store");
				
				map.put("data", tests);
				
				json = new JSONObject(map);
				
				PrintWriter out = response.getWriter();		
				out.println(json.toString());
				out.flush();
				
			} else if("getPlagesHoraires".equals(action)){
				HashMap<String, List<PlageHoraire>> map = 
						new HashMap<String, List<PlageHoraire>>();
				
				fr.eni_ecole.qcm.bean.Test test = new fr.eni_ecole.qcm.bean.Test();
				test.setId(Integer.parseInt(request.getParameter("idTest")));
				
				List<PlageHoraire> plages = DALTest.getPlageHoraireByTest(test);
				
				response.setContentType("application/json");        
				response.setHeader("Cache-Control", "no-store");
				
				map.put("data", plages);
				
				json = new JSONObject(map);
				
				PrintWriter out = response.getWriter();		
				out.println(json.toString());
				out.flush();
			} else if("getSections".equals(action)){
				HashMap<String, List<Section>> map = 
						new HashMap<String, List<Section>>();
				
				fr.eni_ecole.qcm.bean.Test test = new fr.eni_ecole.qcm.bean.Test();
				test.setId(Integer.parseInt(request.getParameter("idTest")));
				
				List<Section> sections = DALSection.selectSectionsByTest(test);
				
				response.setContentType("application/json");        
				response.setHeader("Cache-Control", "no-store");
				
				map.put("data", sections);
				
				json = new JSONObject(map);
				
				PrintWriter out = response.getWriter();		
				out.println(json.toString());
				out.flush();
			} else if("afficherGererTest".equals(action)){

				fr.eni_ecole.qcm.bean.Test test = new fr.eni_ecole.qcm.bean.Test();
				test.setId(0);
				
				List<Section> sections = new ArrayList<Section>();
				List<PlageHoraire> plages = new ArrayList<PlageHoraire>();
				List<Theme> themes = DALTheme.selectAll();
						
				if(request.getParameter("id") != null){		
					//Récupère le test concerné
					test.setId(Integer.parseInt(request.getParameter("id")));					
					test = DALTest.getTest(test);
					
					//Récupère les sections
					sections = DALSection.selectSectionsByTest(test);
					
					//Récupère les plages horaires
					plages = DALTest.getPlageHoraireByTest(test);
					
				}
				
				request.setAttribute("test",test);
				request.setAttribute("sections", sections);
				request.setAttribute("plages", plages);
				request.setAttribute("themes", themes);				
				
				
				dispatcher = request.getRequestDispatcher("/formateur/gererTest.jsp");
				dispatcher.forward(request, response);
			} else {				
				dispatcher = request.getRequestDispatcher("/formateur/gestionTest.jsp");
				dispatcher.forward(request, response);
			}
		}catch(Exception e){
			dispatcher = request.getRequestDispatcher("/erreur");
			request.setAttribute("erreur", e);
			dispatcher.forward(request, response);
		}
	}
	
	

}

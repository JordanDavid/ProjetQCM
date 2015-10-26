package fr.eni_ecole.qcm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import fr.eni_ecole.qcm.bean.*;
import fr.eni_ecole.qcm.dal.DALTest;

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
				
			} else{
				
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

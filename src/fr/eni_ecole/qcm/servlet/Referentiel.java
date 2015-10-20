package fr.eni_ecole.qcm.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni_ecole.qcm.bean.Theme;
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

		try {
			if("afficher".equals(action)){
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

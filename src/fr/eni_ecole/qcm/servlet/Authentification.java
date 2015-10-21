package fr.eni_ecole.qcm.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni_ecole.qcm.bean.Utilisateur;
import fr.eni_ecole.qcm.dal.DALUtilisateur;

/**
 * Servlet implementation class Authentification
 */
public class Authentification extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Authentification() {
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
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		super.service(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		Utilisateur utilisateur = new Utilisateur();
		RequestDispatcher dispatcher = null;

		try {
			utilisateur.setLogin(login);
			utilisateur.setMotdepasse(password);

			if (request.getSession().getAttribute("user") == null) {
				utilisateur = DALUtilisateur.rechercher(utilisateur);
				if (utilisateur == null) {
					dispatcher = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("message", "Login ou mot de passe incorrect");
					dispatcher.forward(request, response);
				} else {
					dispatcher = request.getRequestDispatcher("/accueil.jsp");
					request.getSession().setAttribute("user", utilisateur);
					dispatcher.forward(request, response);					
				}
			}
			else {
				dispatcher = request.getRequestDispatcher("/accueil.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			dispatcher = request.getRequestDispatcher("/erreur.jsp");
			request.setAttribute("erreur", e);
			dispatcher.forward(request, response);
		}
	}

}

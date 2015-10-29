package fr.eni_ecole.qcm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.eni_ecole.qcm.bean.Inscription;
import fr.eni_ecole.qcm.bean.PlageHoraire;
import fr.eni_ecole.qcm.bean.Test;
import fr.eni_ecole.qcm.bean.Theme;
import fr.eni_ecole.qcm.bean.Utilisateur;
import fr.eni_ecole.qcm.dal.DALInscription;
import fr.eni_ecole.qcm.dal.DALPlageHoraire;
import fr.eni_ecole.qcm.dal.DALTest;
import fr.eni_ecole.qcm.dal.DALTheme;
import fr.eni_ecole.qcm.dal.DALUtilisateur;

/**
 * Servlet implementation class Inscription
 */
public class GestionInscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestionInscription() {
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
		try {
			processRequest(request, response);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * M�thode en charge de lister les candidats  
	 * 21 oct. 2015
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * @throws ParseException 
	 * @throws JSONException 
	 */
	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, ParseException, JSONException {
		RequestDispatcher dispatcher = null;
		String action = request.getParameter("action");
		List<Utilisateur> listeCandidats = new ArrayList<Utilisateur>();
		List<Theme> themes = null;
		JSONObject json = null;
		
		try{
			if (!"afficher".equals(action)) {
				switch (action) {
				case "getTests":
					HashMap<String, List<Test>> mapTest = new HashMap<String, List<Test>>();
					
					Theme theme = new Theme();
					theme.setIdTheme(Integer.parseInt(request.getParameter("id")));
					
					List<Test> tests = DALTest.getTestByTheme(theme);
					
					response.setContentType("application/json");        
					response.setHeader("Cache-Control", "no-store");
					
					mapTest.put("data", tests);
					
					json = new JSONObject(mapTest);
					
					PrintWriter out = response.getWriter();		
					out.println(json.toString());
					out.flush();
					break;
				case "enregistrer":
					// ENREGISTREMENT de l'inscription
					Test testIncription = new Test();
					testIncription.setId(Integer.parseInt(request.getParameter("idTest")));
					Test newTest = DALTest.getTest(testIncription);
					
					int duree = newTest.getDuree();
					int idTest = newTest.getId();					
					String date_debut = request.getParameter("dateDebutPlage");
					String date_fin = request.getParameter("dateFinPlage");
					
					JSONArray jsonArray = new JSONArray(request.getParameter("idCandidats"));
					for (int i = 0; i < jsonArray.length(); i++){						
						int idUtilisateur = jsonArray.getInt(i);
						// INSERT INTO pour chaque candidat
						boolean res = DALInscription.ajouter(idTest, null, null, idUtilisateur, duree);
					}
					
					// Gestion PLAGE HORAIRE
					int idPlage = Integer.parseInt(request.getParameter("idPlage"));
					if (idPlage == 0) {
						PlageHoraire p = new PlageHoraire(idPlage, date_debut, date_fin);
						// Alors CREATION d'une nouvelle PLAGE HORAIRE
						p = DALPlageHoraire.ajouter(p);
						// et CREATION d'une ligne sur l'entité PLAGE_HORAIRE_TEST
						DALTest.ajoutPlage(testIncription, p);
					}
					dispatcher = request.getRequestDispatcher("/formateur/inscription?action=afficher");
					dispatcher.forward(request, response);
					
					break;
				case "getPlageHoraire":
					HashMap<String, List<PlageHoraire>> mapPlageHoraire = new HashMap<String, List<PlageHoraire>>();
					
					Test test = new Test();
					test.setId(Integer.parseInt(request.getParameter("id")));
					
					List<PlageHoraire> plageHoraires = DALTest.getPlageHoraireByTest(test);
					
					response.setContentType("application/json");        
					response.setHeader("Cache-Control", "no-store");
					
					mapPlageHoraire.put("data", plageHoraires);
					
					json = new JSONObject(mapPlageHoraire);
					
					PrintWriter out2 = response.getWriter();		
					out2.println(json.toString());
					out2.flush();
					break;
				
				default:
					break;		
				}
			}else {
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

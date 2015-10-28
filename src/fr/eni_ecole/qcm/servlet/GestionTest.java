package fr.eni_ecole.qcm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import fr.eni_ecole.qcm.bean.*;
import fr.eni_ecole.qcm.dal.DALPlageHoraire;
import fr.eni_ecole.qcm.dal.DALSection;
import fr.eni_ecole.qcm.dal.DALTest;
import fr.eni_ecole.qcm.dal.DALTheme;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
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

		PrintWriter out = response.getWriter();
		try{
			if("getTests".equals(action)){
				HashMap<String, List<fr.eni_ecole.qcm.bean.Test>> map = 
						new HashMap<String, List<fr.eni_ecole.qcm.bean.Test>>();
				
				List<fr.eni_ecole.qcm.bean.Test> tests = DALTest.selectAll();
				
				response.setContentType("application/json");        
				response.setHeader("Cache-Control", "no-store");
				
				map.put("data", tests);
				
				json = new JSONObject(map);
				
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
			} else if("enregistrer".equals(action)){
				fr.eni_ecole.qcm.bean.Test test = new fr.eni_ecole.qcm.bean.Test();
										
				//test 
				test.setId(Integer.parseInt(request.getParameter("idTest")));
				test.setLibelle(request.getParameter("nom"));
				test.setDuree(Integer.parseInt(request.getParameter("duree")));
				test.setSeuil_minimum(Integer.parseInt(request.getParameter("seuil1")));
				test.setSeuil_maximum(Integer.parseInt(request.getParameter("seuil2")));
					
				//Si Id = 0 => Ajout Sinon => Modification
				if(test.getId() == 0){
					test = DALTest.ajouter(test);
				}else{
					DALTest.modifier(test);
				}
				
//				Récupère dans la BDD l'ensemble des plages horaires du test 
//				et l'ensemble des sections du test
				test.setListeSections(DALSection.selectSectionsByTest(test));
				test.setListePlageHoraire(DALTest.getPlageHoraireByTest(test));
				
				
//				//Récupère les sections du test après enregistrement du client
				List<Section> lst_new_sections = new ArrayList<Section>();
				JSONArray sections = new JSONArray(request.getParameter("lst_sections"));
				for(int i=0;i<sections.length();i++){
					if(!sections.isNull(i)){
						JSONObject section = sections.getJSONObject(i);
						Theme t = new Theme();
						t.setIdTheme(section.getInt("idTheme"));
						
						Section s = new Section(section.getInt("idSection"),test,t,Integer.parseInt(section.getString("nbQuestion")));
						lst_new_sections.add(s);
					}
				}
				
				//Récupère les plages horaires du test après enregistrement du client
				List<PlageHoraire> lst_new_plages = new ArrayList<PlageHoraire>();
				JSONArray plages = new JSONArray(request.getParameter("lst_plages"));
				for(int i=0;i<plages.length();i++){
					if(!plages.isNull(i)){
						JSONObject plage = plages.getJSONObject(i);
						
						PlageHoraire p = new PlageHoraire(plage.getInt("idPlage"),
								plage.getString("debut"),
								plage.getString("fin"));
						
						lst_new_plages.add(p);
					}
				}
				
//				//Pour chacunes des sections enregistrées : 
				//Si ID == 0  => Ajout section au test => remove de la liste
				//Si ID != 0 && existe dans BDD => Update BDD => remove de la liste
				//Section inexistante => remove BDD => remove de la liste
				for(Section s : lst_new_sections){
//					out.println(s.getNumSection());
					if(s.getNumSection() == 0){
						s = DALSection.ajoutSection(s);
						test.getListeSections().remove(s);
					}else{
						if(test.getListeSections().indexOf(s) != -1){
							DALSection.modifierSection(s);
							test.getListeSections().remove(s);
						}
					}
				}
				//Suprimme de la BDD
				for(Section s : test.getListeSections()){
					DALSection.supprimerSection(s);
				}
				
				//Pour chacunes des plages enregistrées : 
				//Si ID == 0  => Ajout plages , liaison au test => remove de la liste
				//Si ID != 0 && existe dans BDD => Update plage BDD => remove de la liste
				//Plage inexistante => remove plage et liaison BDD => remove de la liste
				for(PlageHoraire p : lst_new_plages){
					if(p.getIdPlageHoraire() == 0){
						p = DALPlageHoraire.ajouter(p);
						DALTest.ajoutPlage(test,p);
						test.getListePlageHoraire().remove(p);
					}else{
						if(test.getListePlageHoraire().indexOf(p) != -1){
							DALTest.modifierPlage(test,p);
							test.getListePlageHoraire().remove(p);
						}
					}
				}
				//Suprimme de la BDD
				for(PlageHoraire p : test.getListePlageHoraire()){
					DALTest.supprimerPlage(test, p);
				}
				
				//redirige vers la page avec l'ajout en affichage
				request.setAttribute("test",test);
				request.setAttribute("sections", DALSection.selectSectionsByTest(test));
				request.setAttribute("plages", DALTest.getPlageHoraireByTest(test));
				request.setAttribute("themes", DALTheme.selectAll());				
				
				dispatcher = request.getRequestDispatcher("/formateur/gererTest.jsp");
				dispatcher.forward(request, response);
				
			} else {		
				
				dispatcher = request.getRequestDispatcher("/formateur/gestionTest.jsp");
				dispatcher.forward(request, response);
			}
		}catch(Exception e){
//			out.println(e.getMessage());
			dispatcher = request.getRequestDispatcher("/erreur.jsp");
			request.setAttribute("erreur", e);
			dispatcher.forward(request, response);
		}
		out.flush();
	}
	
	

}

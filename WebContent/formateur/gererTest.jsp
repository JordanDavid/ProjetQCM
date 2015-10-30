<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="fr.eni_ecole.qcm.bean.*,java.util.*"%>
<% 	String titre = "Ajout/Modification d'un Test";
	String menu = "gestionTest";
%>
<%@include file="/fragments/haut.jspf"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/gestionTest.js"></script>
<% 
	Test t = (Test)request.getAttribute("test"); 
	List<Section> sections = (ArrayList<Section>)request.getAttribute("sections");
	List<PlageHoraire> plages = (ArrayList<PlageHoraire>)request.getAttribute("plages");
	List<Theme> themes = (ArrayList<Theme>)request.getAttribute("themes");
%>
<form id="formGererTest" action="<%=request.getContextPath()%>/formateur/gestionTests?action=enregistrer" method="post">
	<input type="hidden" name="idTest" id="idTest" value="<%=t.getId()%>"/>
	<fieldset>
		<legend>Informations Générales</legend>
		<div id="div_infos_test">
			<div class="inline_infos">
				<label id="label_nom" for="nom">Nom :</label>
				<input type="text" name="nom" id="nom" placeholder="Veuillez saisir le nom du test" value="<%= (t.getLibelle() != null ? t.getLibelle() : "")%>">
			</div>
			<div class="inline_infos">
				<div class="inline_block_infos_left">
					<label for="duree">Durée :</label>
					<input type="number" name="duree" id="duree" value="<%=t.getDuree()%>">
				</div>
				<div class="inline_block_infos_right">
					<label for="nbSections">Nombre de sections :</label>
					<input type="number" name="nbSections" id="nbSections" min="1"
						value="<%= (sections.size() == 0 ? 1 : sections.size())%>"
						onchange="changeNbSection(this);">
				</div>
			</div>
			<div class="inline_infos">
				<div class="inline_block_infos_left">
					<label for="seuil1">Seuil non acquis :</label>
					<input type="number" name="seuil1" id="seuil1" value="<%=t.getSeuil_minimum()%>">
				</div>
				<div class="inline_block_infos_right">				
					<label for="seuil2">Seuil acquis :</label>
					<input type="number" name="seuil2" id="seuil2" value="<%=t.getSeuil_maximum()%>">
				</div>
			</div>
		</div>
	</fieldset>
	<fieldset>
		<legend>Plage de validité</legend>
		<div id="gestion_plage_horaire">
			<div class="inline_dtp">
				<label>Début</label>
				<input type="text" id="datetimepickerdebut" />
			</div>
			<div class="inline_dtp">
				<label>Fin</label>
				<input type="text" id="datetimepickerfin" />
			</div >
			<div class="inline_dtp" align="center">
				<input type="button" id="btn_AjoutPlage" onclick="ajouterPlage();" value="Nouvelle plage"/>
				<input type="button" id="btn_SupprimePlage" onclick="supprimerPlage();" disabled="disabled" value="Supprimer plage"/>
			</div>
		</div>
		<div id="div_lst_plages_horaires">
		<input type="hidden" name="lst_plages" id="lst_plages"/>
			<table class="display" id="lst_plages_horaires">
				<tbody>
					<% for(PlageHoraire plage : plages) { %>
						<tr>
							<td><%=plage.getIdPlageHoraire()%></td>
							<td><%=plage%></td>							
							<td><%=plage.getDateDebut()%></td>
							<td><%=plage.getDateFin()%></td>
						</tr>
					<% } %>
				</tbody>
			</table>
		</div>
	</fieldset>
	<fieldset>
		<legend>Sections / Questions</legend>
		<div id="sections_test">
				<% if(sections.size() > 0) { %>
					<%for (Section section : sections) { 
						String selected =""; %>
						<div class="section_test" data-id="<%=section.getNumSection()%>">
							<div class="div_select_theme_section">
								<select class="select_theme_section" id="select_theme_section_<%=section.getNumSection()%>" name="select_theme_section">
									<% for (Theme theme : themes ) {
										if(theme.getIdTheme() == section.getTheme().getIdTheme())
											selected = "selected=\"selected\"";
										else 
											selected= "";
									%>
										 <option value="<%=theme.getIdTheme()%>" <%=selected %>><%=theme.getLibelle() %></option>
									<% } %>
								</select>
							</div>
							<div class="div_nb_questions_sections">
								Nombre de question : 
								<input type="number" class="nb_questions_sections" name="nb_questions_section" 
								id="nb_questions_section_<%=section.getNumSection()%>"  min="1"
								value="<%=section.getNbQuestion()%>" 
								onclick="changeNbQuestion(this);">
								<input type="hidden" class="valide_nb_question_sections" id="valide_section_<%=section.getNumSection()%>" value="true"/>
								<div class="valide_nb_questions">
									<img id="img_valide_section_<%=section.getNumSection()%>" alt="valide" src="<%=request.getContextPath()%>/images/valide.png"/>
								</div>
							</div>
						</div>		
					<% } %>		
				<% } else { %>
						<div class="section_test new_section_test" data-id="0">
							<div class="div_select_theme_section">
								<select class="select_theme_section" id="select_theme_section_0" name="select_theme_section">
									<% for (Theme theme : themes ) { %>
										 <option value="<%=theme.getIdTheme()%>"><%=theme.getLibelle() %></option>
									<% } %>
								</select>
							</div>
							<div class="div_nb_questions_sections">
								Nombre de question : 
								<input type="number" class="nb_questions_sections" 
								name="nb_questions_section" id="nb_questions_section_0" min="1" value="1"
								onclick="changeNbQuestion(this);">
								<div class="valide_nb_questions">
									<img alt="valide" src="<%=request.getContextPath()%>/images/valide.png"/>
								</div>
							</div>	
						</div>
				<% } %>
		</div>	
		
		<div class="new_section_test hide" data-id="0">
			<div class="div_select_theme_section">
				<select class="select_theme_section" id="select_theme_section_0" name="select_theme_section">
					<% for (Theme theme : themes ) { %>
						 <option value="<%=theme.getIdTheme()%>"><%=theme.getLibelle() %></option>
					<% } %>
				</select>
			</div>
			<div class="div_nb_questions_sections">
				Nombre de question : 
				<input type="number" class="nb_questions_sections" 
				name="nb_questions_section" id="nb_questions_section_0" min="1" value="1"
				onclick="changeNbQuestion(this);">
				<div class="valide_nb_questions">
					<img alt="valide" src="<%=request.getContextPath()%>/images/valide.png"/>
				</div>
			</div>	
		</div>
		
		<input type="hidden" name="lst_sections" id="lst_sections" />	
	</fieldset>
	<div id="div_buttons_gerer_test">
		<input type="button" name="enregistrerTest" id="enregistrerTest" onclick="enregistrerModifTest();" value="Enregistrer"/>
		<input type="reset" name="annulerTest" id="annulerTest" value="Annuler"/>
		<input type="button" id="retour" value="Retour à la sélection des tests" onclick="retourGestionTest();"/>
	</div>
</form>

<%@include file="/fragments/bas.jspf"%>
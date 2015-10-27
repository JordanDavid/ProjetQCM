<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="fr.eni_ecole.qcm.bean.*,java.util.*"%>
<% String titre = "Ajout/Modification d'un Test"; %>
<%@include file="/fragments/haut.jspf"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/gestionTest.js"></script>
<% 
	Test t = (Test)request.getAttribute("test"); 
	List<Section> sections = (ArrayList<Section>)request.getAttribute("sections");
	List<PlageHoraire> plages = (ArrayList<PlageHoraire>)request.getAttribute("plages");
	List<Theme> themes = (ArrayList<Theme>)request.getAttribute("themes");
%>
<form id="formGererTest" action="" method="post">
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
					<input type="number" name="nbSections" id="nbSections" value="<%= sections.size()%>">
				</div>
			</div>
			<div class="inline_infos">
				<div class="inline_block_infos_left">
					<label for="seuil1">Seuil 1 :</label>
					<input type="number" name="seuil1" id="seuil1" value="<%=t.getSeuil_minimum()%>">
				</div>
				<div class="inline_block_infos_right">				
					<label for="seuil2">Seuil 2 :</label>
					<input type="number" name="seuil2" id="seuil2" value="<%=t.getSeuil_maximum()%>">
				</div>
			</div>
		</div>
	</fieldset>
	<fieldset>
		<legend>Plage de validité</legend>
		<div id="gestion_plage_horaire">
			<input type="text" id="datetimepickerdebut" />
		</div>
		<div id="div_lst_plages_horaires">
			<table class="display" id="lst_plages_horaires">
				<tbody>
					<% for(PlageHoraire plage : plages) { %>
						<tr>
							<td><%=plage%></td>
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
						<div class="section_test">
							<div class="div_select_theme_section">
								<select class="select_theme_section" id="select_theme_section_1" name="select_theme_section">
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
								<input type="number" class="nb_questions_sections" name="nb_questions_section_1" id="nb_questions_section_1" value="<%=section.getNbQuestion()%>">
								<div class="valide_nb_questions"></div>
							</div>
						</div>		
					<% } %>		
				<% } else { %>
						<div class="section_test">
							<div>
								<select class="select_theme_section" id="select_theme_section_0" name="select_theme_section">
									<% for (Theme theme : themes ) { %>
										 <option value="<%=theme.getIdTheme()%>"><%=theme.getLibelle() %></option>
									<% } %>
								</select>
							</div>
							<div>
								Nombre de question : 
								<input type="number" class="nb_questions_sections" name="nb_questions_section" id="nb_questions_section_0">
								<div class="valide_nb_questions"></div>
							</div>	
						</div>
				<% } %>
		</div>		
		<div align="right">
			<a class="blueText underlineLink point" onclick="ajouterSection()">Ajouter une section</a>
		</div>
	</fieldset>
	<div id="div_buttons_gerer_test">
		<input type="button" name="enregistrerTest" id="enregistrerTest" onclick="enregistrerTest()" value="Enregistrer"/>
		<input type="reset" name="annulerTest" id="annulerTest" value="Annuler"/>
	</div>
</form>

<%@include file="/fragments/bas.jspf"%>
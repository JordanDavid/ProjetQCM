<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="fr.eni_ecole.qcm.bean.*,java.util.*"%>
<%
	String titre = "Inscription des candidats";
%>
<%@include file="/fragments/haut.jspf"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/fonctionMaxime.js"></script>
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/jquery-ui-1.8.5.custom.css" />
<fieldset>
<legend>Recherche</legend>
	<table id="tableauCandidat" class="display">
		<thead>
			<tr>
				<th><input type="checkbox" name="select_all" value="1" id="select-all"></th>
				<th>Nom</th>
				<th>Prénom</th>
				<th>Statut</th>
			</tr>
		</thead>
		<tbody>
		<%
			ArrayList<Utilisateur> listeCandidats = (ArrayList<Utilisateur>)request.getAttribute("candidats");
			for (Utilisateur u : listeCandidats) {
		%>
		<tr>
			<td><input type="checkbox" name="select_candidats" id="selectCandidat_<%=u.getId()%>"></td>
			<td><%= u.getNom() %></td>
			<td><%= u.getPrenom() %></td>
			<td><%= u.getStatut() %></td>
		</tr>
		<% } %>
		</tbody>
	</table>
	<script type="text/javascript">
				$('#tableauCandidat').dataTable( {
				"bInfo":  false,
				"bLengthChange": false,
				"language": {
					"url": "../Tools/French.json"
				},
				 "searchable":false,
			});
	</script>
</fieldset>
<p></p>
<fieldset>
	<legend>Tests</legend>
	<div id="div_choix_test">		
		Tri par thème :
		<div id="div_select_theme">
			<select id="themes" name="theme" onchange="SelectionThemeForTest()">
				<%
					int index = 0;
					for (Theme t : (List<Theme>)request.getAttribute("themes")) {
						if (index==0){
				%>
						<option selected="selected" value="<%=t.getIdTheme()%>"><%=t.getLibelle()%></option>
				<% }else{ %>
						<option value="<%=t.getIdTheme()%>"><%=t.getLibelle()%></option>
				<%
						}
						index++;
					}
				%>
			</select>
		</div>
		<div id="div_tests_theme">
			<table id="list_tests" class="display">
				<tbody>
					<!-- Affichage en Ajax du tableau. Voir fonctionMaxime.js -->
				</tbody>
			</table>
		</div>
	</div>
	<div id="ajout_candidat_theme">
		<input type="button" name="ajouterCandidatToTheme" id="ajouterCandidatToTheme"
				value="Ajouter" >
	</div>	
	
</fieldset>

<div class="hide" id="ajoutCandidatToTheme" title="Sélectionner la date du test">
	<form id="formAjoutCandidatToTheme" action="<%=request.getContextPath()%>/formateur/inscription?action=ajoutCandidatToTheme" method="post">
		<div class="inline_div_reponse" align="left">
			<!-- contenu de la popup -->
			<table id="list_tests_plage_horaire" class="display">
				<tbody>
					<!-- contenu du tableau -->
				</tbody>
			</table>			
				<p>Nouvelle plage : </p>
				<input type="text" name="date_picker_debut" id="date_picker_debut" />
				<input type="text" name="date_picker_fin" id="date_picker_fin" />
				<input type="submit" id="AjouterNouvellePlageHoraire" name="ajoutPlageHoraire" value="Ajouter">
		</div>
	</form>
</div>

<%@include file="/fragments/bas.jspf"%>
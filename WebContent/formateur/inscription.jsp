<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="fr.eni_ecole.qcm.bean.*,java.util.*"%>
<%
	String titre = "Inscription des candidats";
	String menu = "inscription";
%>
<%@include file="/fragments/haut.jspf"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/fonctionMaxime.js"></script>
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
				<thead>
				<tr>
					<th></th>
					<th>Nom du test</th>
				</tr>
			</thead>
				<tbody>
					<!-- Affichage en Ajax du tableau. Voir fonctionMaxime.js -->
				</tbody>
			</table>
		</div>
	</div>
	<div id="ajout_candidat_theme">
		<input type="button" name="ajouterCandidatToTheme" id="ajouterCandidatToTheme"
				value="Ajouter" onclick="RechargerPlages()">
	</div>	
	
</fieldset>
<p></p>
<fieldset>
	<legend>Tests sélectionnés</legend>
	<div id="div_tests_selectionnes">
		<table id="tabTestsSelect" class="display">
			<thead>
				<tr>
					<th>id Test</th>
	                <th>Nom du test</th>
	                <th>id Plage</th>
	                <th>Date de début</th>
	                <th>Date de fin</th>
				</tr>
			</thead>
			<tbody>
				<!-- Affichage du tableau des tests selectionnes -->
			</tbody>
		</table>
		<p><input type="button" id="deleteButton" value="Retirer"></p>
	</div>
</fieldset>
<p>
	<input type="submit" value="Enregistrer l'Inscription">
	<a href="../accueil.jsp"><input type="button" name="cancel" value="Annuler l'Inscription"></a>
</p>
<div class="hide" id="choixPlage" title="Sélectionner la date du test">
	<div class="inline_div_reponse" align="left">
		<!-- contenu de la popup -->
		<table id="tabPlagesHoraires" class="display">
			<thead>
	            <tr>
	            	<th>id plage</th>
	                <th>Date de début</th>
	                <th>Date de fin</th>
	            </tr>
			</thead>
			<tbody>
				<!-- contenu du tableau -->
			</tbody>
		</table>			
			<p>Nouvelle plage : </p>
			<input type="text" name="date_picker_debut" id="date_picker_debut" tabindex="-1"/>
			<input type="text" name="date_picker_fin" id="date_picker_fin" tabindex="-1"/>
			<a href="#"><input type="button" id="ajoutNewPlageHoraire" value="Ajouter"></a>
			
			<a href="#"><input type="button" id="validerAjoutTest" value="Valider" style="width:400px;"></a>
			
			<!-- INPUT caché -->
			<input type="hidden" id="idTest" name="idTest" value="0"/>
			<input type="hidden" id="idPlageHoraire" name="idPlageHoraire" />
			<input type="hidden" id="libelleTest" name="libelleTest"/>
			<input type="hidden" id="dateDebutPlage" name="dateDebutPlage"/>
			<input type="hidden" id="dateFinPlage" name="dateFinPlage"/>
			
			<br/>
	</div>
</div>

<%@include file="/fragments/bas.jspf"%>
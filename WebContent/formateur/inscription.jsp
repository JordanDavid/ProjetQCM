<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="fr.eni_ecole.qcm.bean.*,java.util.*"%>
<%
	String titre = "Inscription des candidats";
	String menu = "inscription";
%>
<%@include file="/fragments/haut.jspf"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/fonctionMaxime.js"></script>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/theme/basic/styleMaxime.css">
<form id="insciptionCandidat" action="<%=request.getContextPath()%>/formateur/inscription?action=enregistrer" method="post">
	<fieldset>
	<legend>Recherche</legend>
		<table id="tableauCandidat" class="display select">
			<thead>
				<tr>
					<th></th>
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
				<td><input type="checkbox" value="<%= u.getId()%>" name="select_candidats" id="selectCandidat_<%=u.getId()%>"
					onclick="selectCandidat(this)" ></td>
				<td><%= u.getNom() %></td>
				<td><%= u.getPrenom() %></td>
				<td><%= u.getStatut() %></td>
			</tr>
			<% } %>
			</tbody>
		</table>
		<script type="text/javascript">
		$(document).ready(function (){
			var tableCandidat = $('#tableauCandidat').dataTable( {
				"bInfo":  false,
				"bLengthChange": false,
		        "className": "dt-body-center",
				"language": {
					"url": "../Tools/French.json"
				},
				 "searchable":false,
				'columnDefs': [{
			         'targets': 0,
			         'searchable': false,
			         'orderable': false,
				}]
			});
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
		<div id="btn_ajout_plage_horaire">
			<div id="ajout_candidat_theme">
				<input type="button" name="ajouterCandidatToTheme" id="ajouterCandidatToTheme"
					value="Ajouter" onclick="RechargerPlages()" >
			</div>
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
		</div>
		<div>
			<div id="btn_supprimer_plage_horaire" >
				<p><input type="button" id="deleteButton" value="Retirer"></p>			
			</div>
		</div>
	</fieldset>
			<!-- INPUT caché -->
			<input type="hidden" id="idTest" name="idTest" value="0"/>
			<input type="hidden" id="idPlage" name="idPlage" />
			<input type="hidden" id="libelleTest" name="libelleTest"/>
			<input type="hidden" id="dateDebutPlage" name="dateDebutPlage"/>
			<input type="hidden" id="dateFinPlage" name="dateFinPlage"/>
			<input type="hidden" id="idCandidats" name="idCandidats"/>
	<p>
		<input type="submit" value="Enregistrer l'Inscription">
		<a href="../accueil.jsp"><input type="button" name="cancel" value="Annuler l'Inscription"></a>
	</p>
</form>
<div class="hide" id="choixPlage" title="Sélectionner la date du test">
	<div class="inline_div_reponse" align="left" style="margin-top:0%">
		<!-- contenu de la popup -->
		<fieldset>
		<legend>Plage Horaire</legend>
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
		</fieldset>
		
		<fieldset>
		<legend>Nouvelle Plage Horaire</legend>
			<div id="gestion_plage_horaire_inscription">
				<div class="inline_dtp" style="text-align: center">
					<label>Début</label>
					<input type="text" id="InscriptionDatetimepickerdebut" tabindex="-1" style="width:135px"/>
					<label>Fin</label>
					<input type="text" id="InscriptionDatetimepickerfin" tabindex="-1" style="width:135px"/>
				</div >
				<div class="inline_dtp_inscription" align="center">
					<input type="button" id="btn_AjoutPlage_inscription" onclick="ajouterPlage();" value="Nouvelle plage"/>
				</div>
			</div>
		</fieldset>
			
		<br/>
		<p style="text-align: center;"><input type="button" id="validerAjoutTest" value="Valider" style="width:250px" ></p>		
		<br/>
	</div>
</div>

<%@include file="/fragments/bas.jspf"%>
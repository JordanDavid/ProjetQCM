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
	<table id="tableauCandidat" class="display select">
		<thead>
			<tr>
				<th><input type="checkbox" name="select-all" value="1" id="select-all"></th>
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
			<td><input type="checkbox" name="select_candidats" id="selectCandidat_<%=u.getId()%>" onclick="selectCandidat(this)"></td>
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
			"language": {
				"url": "../Tools/French.json"
			},
			 "searchable":false,
		});	
		
		// Handle click on "Select all" control
	   $('#select-all').on('click', function(){
	      // Get all rows with search applied
	      var rows = tableCandidat.rows({ 'search': 'applied' }).nodes();
	      // Check/uncheck checkboxes for all rows in the table
	      $('input[type="checkbox"]', rows).prop('checked', this.checked);
	   });

	   // Handle click on checkbox to set state of "Select all" control
	   $('#tableauCandidat tbody').on('change', 'input[type="checkbox"]', function(){
	      // If checkbox is not checked
	      if(!this.checked){
	         var el = $('#select-all').get(0);
	         // If "Select all" control is checked and has 'indeterminate' property
	         if(el && el.checked && ('indeterminate' in el)){
	            // Set visual state of "Select all" control 
	            // as 'indeterminate'
	            el.indeterminate = true;
	         }
	      }
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
		<!-- INPUT caché -->
		<input type="hidden" id="idTest" name="idTest" value="0"/>
		<input type="hidden" id="idPlage" name="idPlage" />
		<input type="hidden" id="libelleTest" name="libelleTest"/>
		<input type="hidden" id="dateDebutPlage" name="dateDebutPlage"/>
		<input type="hidden" id="dateFinPlage" name="dateFinPlage"/>			
		<br/>
	</div>
</div>

<%@include file="/fragments/bas.jspf"%>
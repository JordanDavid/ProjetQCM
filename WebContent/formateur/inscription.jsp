<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="fr.eni_ecole.qcm.bean.*,java.util.*"%>
<%
	String titre = "Inscription des candidats";
%>
<%@include file="/fragments/haut.jspf"%>
<fieldset>
	<table id="tableauCandidat">
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
				$('#tableauCandidat').DataTable( {
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
Tri par thème : <select id="listeThemes" name="listeThemes">
	<%
		ArrayList<Theme> listeThemes = (ArrayList<Theme>)request.getAttribute("themes");
		for (Theme t : listeThemes) {
	%>
	<option value="<%=t.getIdTheme()%>"><%=t.getLibelle()%></option>
	<% } %>
</select>
<p></p>
<div id="div_tests_theme">
	<table id="list_tests" class="display">
		<tbody>
		
		</tbody>
	</table>
</div>
<p></p>
<input type="button" value="Ajouter" id="ajoutInscription" name="ajoutInscription">
</fieldset>
<%@include file="/fragments/bas.jspf"%>
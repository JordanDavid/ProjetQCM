<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import ="fr.eni_ecole.qcm.bean.*,java.util.*" 
   %>
<% 
	String titre = "Inscription";
%>
<%@include file="/fragments/haut.jspf" %>


<fieldset>
<legend>Recherche</legend>
	<div id="dialo-rechercheCandidat" title="Inscription des candidats">	
	<form action="<%= request.getContextPath()%>/inscription" method="POST">
		<input type="text" name="libelleRecherche" id="libelleRecherche">
		<input type="submit" tabindex="-1" name="rechercheCandidat" id="rechercheCandidat" value="Rechercher">
	</form>
	</div>

<table id="tableauCandidat" class="display">
	<thead>
		<tr>
			<th>Nom</th>
			<th>Prenom</th>
			<th>Statut</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>Gaston</td>
			<td>beau</td>
			<td>CDI</td>
		</tr>
		<tr>
			<td>Alain</td>
			<td>Proviste</td>
			<td>ASR</td>
		</tr>		
	</tbody>
</table>
</fieldset>

<%@include file="/fragments/bas.jspf"%>
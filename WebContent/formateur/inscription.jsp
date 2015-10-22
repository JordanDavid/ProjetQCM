<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="fr.eni_ecole.qcm.bean.*,java.util.*"%>
<%
	String titre = "Inscription des candidats";
%>
<%@include file="/fragments/haut.jspf"%>
<fieldset>
	<table id="tableauCandidat" class="display select">
		<thead>
			<tr>
				<th><input type="checkbox" name="select_all" value="1"
					id="example-select-all"></th>
				<th>Nom</th>
				<th>Prénom</th>
				<th>Statut</th>
			</tr>
		</thead>
	</table>
</fieldset>
<p></p>
<fieldset>
Tri par thème : <select id="listeThemes" name="listeThemes">
	<%
		ArrayList<Theme> listeThemes = (ArrayList<Theme>)request.getAttribute("themes");
		for (Theme t : listeThemes) {
	%>
	<option value="<%=t.getIdTheme()%>"><%=t.getLibelle()%></option>
	<%
		}
	%>
</select>
<p></p>
<table id="tableauTest" class="display select">
		<thead>
			<tr>
				<th>Nom du test</th>
			</tr>
		</thead>
	</table>
<p></p>
<input type="button" value="Ajouter" id="ajoutInscription" name="ajoutInscription">
</fieldset>
<%@include file="/fragments/bas.jspf"%>
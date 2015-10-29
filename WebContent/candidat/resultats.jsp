<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="fr.eni_ecole.qcm.bean.*,java.util.*"%>
<%
	String titre = "Résultats du test - " + ((Test)request.getAttribute("test")).getLibelle();
	String menu = "passerTest";
	Resultat resultat = (Resultat)request.getAttribute("resultat");
%>
<%@include file="/fragments/haut.jspf"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/resultat.js"></script>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<!-- 
	<div id="lst_resultats_questions">
	
	</div> -->
	<div id="charts_resultats_question">
		
		<div id="charts_bonnes_reponses" style="min-width: 100px;max-width: 250px;height: 200px;margin:auto;margin-top:5%"
			data-bonneReponse="<%=resultat.getNbBonnesReponses()%>" 
			data-totalBonneReponse="<%=resultat.getTotalQuestion() %>" ></div>
			
		<div id="details_resultat">
			<h2>Détails du résultat</h2>
			<ul>
				<li>Nombre de bonnes questions : <%=resultat.getNbBonnesReponses()%>/<%=resultat.getTotalQuestion()%>
				<li>Pourcentage réussite : <%=resultat.getPourcentagebonneReponse() %>%</li>
				<li>Seuil atteint : <%=resultat.getSeuilAtteint() %></li>
				<li>Nombre d'incidents : <%=resultat.getNbIncidents() %></li>
				<li>Temps passé : 5 min</li>
			</ul>
		</div>
		<div align="right" style="margin-right: 5%;">
			<a class="blueText underlineLink" href="<%=request.getContextPath()%>/accueil.jsp">Retour sur la page d'accueil</a>
		</div>
	</div>

<%@include file="/fragments/bas.jspf"%>
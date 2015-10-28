<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="fr.eni_ecole.qcm.bean.*,java.util.*"%>
<%
	Question question = (Question)request.getAttribute("question");
	String titre = "Question n°"+request.getParameter("q");
	String menu = "passerTest";
%>
<%@include file="/fragments/haut.jspf"%>
	<div>
		<div id="enonce">
			<%= question.getEnonce() %>
		</div>
		<div id="reponse">
		
		</div>	
		<div id="action">
			<input type="checkbox" name="marquer" id="marquer" onclick="marquerQuestion()"/>
			<input type="button" name="repondre" id="repondre" value="Suivant >>"/>
		</div>
		<div>
			<div id="titre_historique">
			
			</div>
			<div id="historique">
				<%  int y=1;
					for(Tirage tirage : (List<Tirage>)session.getAttribute("tirage")) { %>
					<a href="./test?action=afficher&q=<%=y%>">Question <%=y%></a>
				<%	y++;
					} %>
			</div>
		</div>
	</div>

<%@include file="/fragments/bas.jspf"%>
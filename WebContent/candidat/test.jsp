<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="fr.eni_ecole.qcm.bean.*,java.util.*"%>
<%
	Question question = (Question)request.getAttribute("question");
	List<Reponse> reponses = (List<Reponse>)request.getAttribute("reponses");
	int numQuestion = (int)request.getAttribute("q");
	String titre = "Question n°"+numQuestion;
	String menu = "passerTest";
	String type = question.getTypeReponse() == false ? "radio" : "checkbox";
%>
<%@include file="/fragments/haut.jspf"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/test.js"></script>
	<div>
		<form id="formQuestionTest" action="<%=request.getContextPath()%>/candidat/test" method="post">
			<div id="enonce">
				<%= question.getEnonce() %>
			</div>
			<div id="reponses">
				<% for(Reponse reponse : reponses) { %>
					<div class="reponse">
						<input class="input_reponse" id="input_reponse_<%=reponse.getIdReponse() %>" 
							type="<%=type%>" name="reponses" title="Cocher pour indiquer la bonne réponse"
							value="<%=reponse.getIdReponse()%>"/>
						<label class="enonce_reponse" for="input_reponse_<%=reponse.getIdReponse() %>" ><%=reponse.getReponse()%></label>			
					</div>
				<% } %>
			</div>	
			<div id="action">
				<input type="checkbox" name="marquer" id="marquer" onclick="marquerQuestion()"/>
				<% if((Boolean)request.getAttribute("terminer")) { %>
						<input type="button" name="repondre" id="repondre"  data-q="<%=numQuestion%>" value="Suivant >>" onclick="Suivant()"/>
				<% }else{ %>
						<input type="button" name="terminer" id="terminer" value="Terminer >>" onclick="Terminer()"/>
				<% } %>
			</div>
		</form>
		<div>
			<div id="titre_historique">
				<p>Naviguer sur les réponses</p>
			</div>
			<div id="historique">
				<%  int y=1;
					for(Tirage tirage : (List<Tirage>)session.getAttribute("tirage")) { 
						String classe = "";
						if(y==(numQuestion-1)) {
							classe+="question_encours ";
						}
						if(tirage.getMarque()){
							classe+="question_marquee";
						}					
				%>
					<a class="historique_questions <%=classe%>" 
						href="./test?action=afficher&q=<%=y%>">Question <%=y%></a>
				<%	y++;
					} %>
			</div>
		</div>
	</div>
	
	<div class="hide" id="dialogConfirmTerminerTest">
		<div id="messageConfirmTerminerTest"></div>
	</div>
<%@include file="/fragments/bas.jspf"%>
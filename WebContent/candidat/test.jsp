<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="fr.eni_ecole.qcm.bean.*,java.util.*"%>
<%
	Question question = (Question)request.getAttribute("question");
	List<Reponse> reponses = (List<Reponse>)request.getAttribute("reponses");
	List<Reponse> responsesTirageBD = (List<Reponse>)request.getAttribute("reponsesTirage");
	int numQuestion = Integer.parseInt(request.getAttribute("q").toString());
	String titre = "Question n°"+numQuestion;
	String menu = "passerTest";
	String type = question.getTypeReponse() == false ? "radio" : "checkbox";
%>
<%@include file="/fragments/haut.jspf"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/test.js"></script>
	<div id="passerTest">
		<div id="temps_restant">
		
		</div>
		<form id="formQuestionTest" action="<%=request.getContextPath()%>/candidat/test" method="post">
			<div id="enonce">
				<%= question.getEnonce() %>
			</div>
			<div id="reponses">
				<% for(Reponse reponse : reponses) { 
					String checked="";
					if(responsesTirageBD.contains(reponse))
						checked="checked=\"checked\"";				
				%>
					<div class="reponse">
						<input class="input_reponse" id="input_reponse_<%=reponse.getIdReponse() %>" 
							type="<%=type%>" name="reponses" title="Cocher pour indiquer la bonne réponse"
							value="<%=reponse.getIdReponse()%>" <%=checked%>/>
						<label class="enonce_reponse" for="input_reponse_<%=reponse.getIdReponse() %>" ><%=reponse.getReponse()%></label>			
					</div>
				<% } %>
			</div>	
			<div id="action">
				<input type="checkbox" name="marquer" id="marquer" onclick="marquerQuestion()"/><label for="marquer">Marquer la question</label>
				<% if(!(Boolean)request.getAttribute("terminer")) { %>
						<input type="button" name="repondre" id="repondre"  data-q="<%=numQuestion%>" value="Suivant >>" onclick="Suivant(this)"/>
				<% }else{ %>
						<input type="button" name="terminer" id="terminer" data-q="<%=numQuestion%>" value="Terminer >>" onclick="Terminer(this)"/>
				<% } %>
			</div>
		</form>
		<div>
			<div id="titre_historique">
				<p>Historique des questions :</p>
			</div>
			<div id="historique">
				<%  int y=1;
					for(Tirage tirage : (List<Tirage>)session.getAttribute("tirage")) { 
						String classe = ""; 
						if(y==(numQuestion)) {
							classe+="question_encours ";
						}
						if(tirage.getMarque()){
							classe+="question_marquee";
						} 			
				%>
				<a class="blueText underlineLink historique_questions <%=classe%>" 
						href="<%=request.getContextPath() %>/candidat/test?action=afficher&q=<%=y%>">Question <%=y%></a>
				<%	y++;
					} %>
					<a class="blueText underlineLink historique_questions" href="<%=request.getContextPath() %>/candidat/test?action=recapitulatif">Récapitualtif</a>
			</div>
		</div>
	</div>

<%@include file="/fragments/bas.jspf"%>
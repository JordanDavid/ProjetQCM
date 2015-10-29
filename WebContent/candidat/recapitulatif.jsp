<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="fr.eni_ecole.qcm.bean.*,java.util.*"%>
<%
	String titre = "Récapitulatif";
	String menu = "passerTest";
%>
<%@include file="/fragments/haut.jspf"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/test.js"></script>	
	<div id="recap_questions">
		<%  int y=1;
		for(Tirage tirage : (List<Tirage>)session.getAttribute("tirage")) { 
			String classe = ""; 
			if(tirage.getMarque()){
				classe+="question_marquee";
			} 			
		%>
		<a class="blueText underlineLink <%=classe%>" 
				href="<%=request.getContextPath() %>/candidat/test?action=afficher&q=<%=y%>">Question <%=y%></a>
		<%	y++;
			} %>
	</div>
	<div align="center">
		<form id="finirTest" 
			action="<%=request.getContextPath() %>/candidat/test?action=terminer&idTest=<%=((Test)session.getAttribute("test")).getId() %>" method="post">
			<input type="submit" name="finir" id="finir" value="Fin du test"/>
		</form>
	</div>
<div class="hide" id="dialogConfirmTerminerTest">
	<div id="messageConfirmTerminerTest"></div>
</div>
<%@include file="/fragments/bas.jspf"%>
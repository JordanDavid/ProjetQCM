<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*,fr.eni_ecole.qcm.bean.*"%>
<%
	String titre = "Choix du test";	
	String menu = "passerTest";
%>
<%@include file="/fragments/haut.jspf"%>
	<form action="./test?action=lancerTest" method="POST">
		<div>
			<select id="test">
			<% for(Test test : (ArrayList<Test>)request.getAttribute("tests")) { %>
				<option value="<%= test.getId() %>"><%=test.getLibelle() %></option>
			<% } %>
			</select>	
		</div>			
		<div>
			<input type="submit" name="commencer" id="commencer" value="Lancer le test"/>
		</div>
	</form>
<%@include file="/fragments/bas.jspf"%>
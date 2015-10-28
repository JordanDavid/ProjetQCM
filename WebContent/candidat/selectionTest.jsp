<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*,fr.eni_ecole.qcm.bean.*"%>
<%
	String titre = "Choix du test";	
	String menu = "passerTest";
%>
<%@include file="/fragments/haut.jspf"%>
	<div id="div_form_selection_test">
		<form id="form_selection_test" action="./test?action=lancerTest" method="POST">
			<div id="div_select_test">
				<select id="tests" name="idTest">
				<% for(Test test : (ArrayList<Test>)request.getAttribute("tests")) { %>
					<option value="<%= test.getId() %>"><%=test.getLibelle() %></option>
				<% } %>
				</select>	
			</div>			
			<div id="div_lancer_test">
				<input type="submit" name="commencer" id="commencer" value="Lancer le test"/>
			</div>
		</form>
	</div>
<%@include file="/fragments/bas.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*,fr.eni_ecole.qcm.bean.*"%>
<%
	String titre = "Choix du test";	
	String menu = "passerTest";
	List<Test> tests = (ArrayList<Test>)request.getAttribute("tests");
%>
<%@include file="/fragments/haut.jspf"%>
	<div id="div_form_selection_test">
		<form id="form_selection_test" action="./test?action=lancerTest" method="POST">		
			<% if(tests.size() > 0){%>
				<div id="div_select_test">
						<select id="tests" name="idTest">
						<% for(Test test : tests) { %>
							<option value="<%= test.getId() %>"><%=test.getLibelle() %></option>
						<% } %>
						</select>
				</div>			
				<div id="div_lancer_test">
					<input type="submit" name="commencer" id="commencer" value="Lancer le test"/>
				</div>				
			<% } else { %>
				<p>Aucun test pour le moment</p>
			<% } %>
		</form>
	</div>
<%@include file="/fragments/bas.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import ="fr.eni_ecole.qcm.bean.*,java.util.*" 
   %>
<% 
	String titre = "Gestion du référentiel";
%>
<%@include file="/fragments/haut.jspf" %>
	<select id="themes" name="theme">
		<% for(Theme theme : (List<Theme>)request.getAttribute("themes") ) { %>
			<option><%=theme.getLibelle()%></option>
		<% } %>
	</select>
<%@include file="/fragments/bas.jspf"%>
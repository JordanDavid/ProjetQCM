<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="fr.eni_ecole.qcm.bean.*,java.util.*"%>
<%
	String titre = "Résultats du test - " + ((Test)request.getAttribute("test")).getLibelle();
	String menu = "passerTest";
%>
<%@include file="/fragments/haut.jspf"%>
<script> </script>

<%@include file="/fragments/bas.jspf"%>
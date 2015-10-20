<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String titre = "Erreur !";
%>
<%@include file="/fragments/haut.jspf" %>
	<%= ((Exception)request.getAttribute("erreur")).getMessage() %>
<%@include file="/fragments/bas.jspf" %>


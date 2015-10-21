<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/theme/basic/styleConnection.css">
<title>QCM</title>
</head>
<body>
<div class="formConnection">
	<p>Bonjour</p>
	<p>Merci de vous connecter � l'aide de vos identifiants
	<form action="<%= request.getContextPath()%>/authentification" method="POST">
		<p><input type="text" name="login" id="login" value="jDupont"></input></p>
		<p><input type="password" name="password" id="password" value="jdupont"></input></p>
		<p><input type="submit" name="valider" id="valider" value="Se connnecter"></p>
	</form>
	<% if(request.getAttribute("message") != null){ %>
		<div class="erreur">
			<p><%= request.getAttribute("message").toString() %></p>
		</div>
	<% } %>
	
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/theme/basic/styleConnection.css">
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/theme/basic/structure.css">
<title>QCM</title>
</head>
<body>
<div id="formConnection">
	<p class="blueText" id="titre_bonjour">Bonjour</p>
	<p id="sous_titre_connexion">Merci de vous connecter � l'aide de vos identifiants</p>
	<form action="<%= request.getContextPath()%>/authentification" method="POST">
		<div class="inline_input_connexion">
			<input type="text" name="login" id="login" value="jDupont"></input>
		</div>
		<div class="inline_input_connexion">
			<input type="password" name="password" id="password" value="jdupont"></input>
		</div>
		<div class="inline_input_connexion" id="input_connexion">
			<input type="submit" name="valider" id="valider" value="Se connnecter">
		</div>
	</form>
	<% if(request.getAttribute("message") != null){ %>
		<div class="erreur">
			<p><%= request.getAttribute("message").toString() %></p>
		</div>
	<% } %>
	
</div>
</body>
</html>
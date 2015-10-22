<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="fr.eni_ecole.qcm.bean.*,java.util.*"%>
<%
	String titre = "Gestion du référentiel";
%>
<%@include file="/fragments/haut.jspf"%>
<form id="gestion_referentiel"
	action="<%=request.getContextPath()%>/formation/referentiel"
	method="post">
	<div id="div_theme">
		<div id="div_select_theme">
			<select id="themes" name="theme" onchange="SelectionTheme()">
				<%  int i =0;
					for(Theme theme : (List<Theme>)request.getAttribute("themes") ) { %>
						<% if(i==0){ %>
							<option selected="selected" value="<%=theme.getIdTheme()%>"><%=theme.getLibelle()%></option>
						<% }else{ %>						
							<option value="<%=theme.getIdTheme()%>"><%=theme.getLibelle()%></option>
						<%} %>
				<% 	i++;
					} %>
			</select>
		</div>
		<div id="div_gestion_theme">
			<input type="button" name="ajouterTheme" id="ajouterTheme"
				value="Ajouter un thème" onclick="AfficherAjoutTheme();"> <input
				type="submit" name="supprimerTheme" id="supprimerTheme"
				value="Supprimer le thème" />
		</div>
	</div>
	<div id="div_questions_theme">
		<fieldset>
			<legend>Liste des questions</legend>
			<table id="lst_questions" class="display">
				<tbody>
					
				</tbody>
			</table>
		</fieldset>
		<div id="div_btn_question">
			<input type="button" name="ajouterQuestion" id="ajouterQuestion"
				value="Ajouter une question" onclick="AfficherAjouterQuestion();" />
			<input type="submit" name="supprimerQuestion" id="supprimerQuestion"
				value="Supprimer la question" /> <input type="button"
				name="changerTheme" id="changerTheme"
				value="Changer le thème de la question">
		</div>
	</div>
	<div id="div_details_question">
		<fieldset>
			<legend>Détail de la question</legend>
			<div class="inline_div_reponse" align="left">
				<label for="enonce">Énoncé :</label>
			</div>
			<div class="inline_div_reponse" align="left">
				<textarea id="enonce" name="enonce" ></textarea>
			</div>
			<div class="inline_div_reponse" align="left">
				<label for="image">Joindre une image</label> <input type="file"
					name="image" id="image" />
			</div>
			<div class="inline_div_reponse" align="left">
				<label for="typeQuestion">Type de la question :</label>
			</div>
			<div class="inline_div_reponse" align="left">
				<select id="typeQuestion" name="typeQuestion" onchange="SelectionTypeQuestion();">
					<option value="0">1 seule bonne réponse</option>
					<option value="1">Plusieurs bonnes réponses</option>
				</select>
			</div>
			<div class="inline_div_reponse" align="left">
				<label>Ajouter les différentes réponses possibles :</label>
				<div id="div_reponses_question">
					<div class="reponse">
						<input type="text" name="reponses[]" id="reponse_n" placeholder="Veuillez saisir la réponse"/>
						<input type="radio" name="" id="" title="Cocher pour indiquer la bonne réponse"/>					
					</div>		
					<div class="reponse">
						<input type="text" name="reponses[]" id="reponse_n" placeholder="Veuillez saisir la réponse"/>
						<input type="radio" name="" id="" title="Cocher pour indiquer la bonne réponse"/>					
					</div>					
				</div>
			</div>
			<div class="inline_div_reponse" align="right">
				<a class="blueText underlineLink" onclick="ajouterReponse();">Ajouter
					une reponse</a>
			</div>
			<div class="inline_div_reponse" align="center">
				<input type="submit" name="enregisterQuestion"
					id="enregistrerQuestion" value="Enregistrer" /> <input
					type="button" name="annulerQuestion" id="annuler" value="Annuler" />
			</div>
		</fieldset>
	</div>
</form>
<%@include file="/fragments/bas.jspf"%>
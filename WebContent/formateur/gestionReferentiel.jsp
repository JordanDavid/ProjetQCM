<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="fr.eni_ecole.qcm.bean.*,java.util.*"%>
<%
	String titre = "Gestion du référentiel";
%>
<%@include file="/fragments/haut.jspf"%>
<form id="gestion_referentiel"
	action="<%=request.getContextPath()%>/formateur/referentiel"
	method="post"
	>
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
				value="Ajouter un thème" onclick="AfficherAjoutTheme();"> 
			<input type="button" name="supprimerTheme" id="supprimerTheme"
				value="Supprimer le thème" onclick="AfficherConfirmSupprTheme();" />
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
			<input type="button" name="supprimerQuestion" id="supprimerQuestion"
				value="Supprimer la question" disabled="disabled" onclick="AfficherConfirmSupprQuestion()"/> 
			<input type="button"
				name="changerTheme" id="changerTheme"
				value="Changer le thème de la question" disabled="disabled" onclick="AfficherChangerTheme()">
		</div>
	</div>
	<div id="div_details_question">
		<fieldset>
			<legend>Détail de la question</legend>
			<input type="hidden" name="idQuestion" id="idQuestion"/>
			<div class="inline_div_reponse" align="left">
				<label for="enonce">Énoncé :</label>
			</div>
			<div class="inline_div_reponse" align="left" style="min-height: 70px">
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
					<input type="hidden" name="lst_reponses" id="lst_reponses"/>
					<div class="reponse">
						<input type="hidden" name="reponses" placeholder="Veuillez saisir la réponse" value="-1"/>
						<input type="text" class="enonce_reponse" name="reponses" id="reponse_n-1" placeholder="Veuillez saisir la réponse"/>
						<input class="input_reponse" type="radio" name="reponses" title="Cocher pour indiquer la bonne réponse"/>					
					</div>		
					<div class="reponse">
						<input type="hidden" name="reponses" value="0"/>
						<input type="text" class="enonce_reponse" name="reponses" id="reponse_n0" placeholder="Veuillez saisir la réponse"/>
						<input class="input_reponse" type="radio" name="reponses" title="Cocher pour indiquer la bonne réponse"/>					
					</div>					
				</div>
			</div>
			<div class="inline_div_reponse" align="right">
				<a class="blueText underlineLink pointer" onclick="ajouterReponse();">Ajouter
					une reponse</a>
			</div>
			<div class="inline_div_reponse" align="center">
				<input type="button" name="enregisterQuestion" id="enregistrerQuestion" value="Enregistrer" onclick="EnregistrerQuestion();"/> 
				<input type="button" name="annulerQuestion" id="annuler" value="Annuler" />
			</div>
		</fieldset>
	</div>
</form>

<div class="hide" id="ajoutTheme" title="Ajouter un thème">
	<form id="formAjoutTheme" action="<%=request.getContextPath()%>/formateur/referentiel?action=ajoutTheme" method="post">
		<div class="inline_div_reponse" align="left">
			<label>Libelle</label>
			<textarea id="libelle_theme" name="libelle_theme" rows="2" cols="100"></textarea>
		</div>
	</form>
</div>
<div class="hide" id="confirmSupprTheme" title="Supprimer le thème">
	<form id="formConfirmSupprTheme" action="<%=request.getContextPath()%>/formateur/referentiel?action=supprimerTheme" method="post">
		<div id="messageConfirmSupprTheme"></div>
		<input type="hidden" name="idTheme" id="idThemeSupprime" />
	</form>
</div>
<div class="hide" id="confirmSupprQuestion" title="Supprimer la question">
	<form id="formConfirmSupprQuestion" action="<%=request.getContextPath()%>/formateur/referentiel?action=supprimerQuestion" method="post">
		<div id="messageConfirmSupprQuestion"></div>
		<input type="hidden" name="idQuestion" id="idQuestionToDelete" />
	</form>
</div>
<div class="hide" id="changerThemeQuestion" title="Changer le thème">
	<form id="formChangerTheme" action="<%=request.getContextPath()%>/formateur/referentiel?action=changerThemeQuestion" method="post">
		<div id="div_select_change_theme">
			<select id="select_change_theme" name="theme">
				<%  int y =0;
					for(Theme theme : (List<Theme>)request.getAttribute("themes") ) { %>
						<% if(i==0){ %>
							<option selected="selected" value="<%=theme.getIdTheme()%>"><%=theme.getLibelle()%></option>
						<% }else{ %>						
							<option value="<%=theme.getIdTheme()%>"><%=theme.getLibelle()%></option>
						<%} %>
				<% 	y++;
					} %>
			</select>
		</div>
		<input type="hidden" name="idQuestion" id="idQuestionToChange" />
	</form>
</div>

<%@include file="/fragments/bas.jspf"%>
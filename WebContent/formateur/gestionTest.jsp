<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%  String titre = "Gestion des tests";
	String menu = "gestionTest";
 %>
<%@include file="/fragments/haut.jspf"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/gestionTest.js"></script>
 
<div id="div_lst_tests">
	<div class="titre_form">
		Liste des tests
	</div>
	<div class="border_div">
		<table id="table_lst_test" class="display">
			<tbody></tbody>
		</table>
		<div align="center">
			<button id="btn_ajoutTest" onclick="afficherAjoutTest();">Ajouter</button>
			<button id="btn_modifierTest" onclick="afficherModificationTest();">Modifier</button>
			<button id="btn_supprimerTest" onclick="supprimerTest();">Supprimer</button>
		</div>
	</div>
</div>
<div id="div_details_tests">
	<input type="hidden" name="idTest" id="idTest" />
	<div class="titre_form">
		Détails du test
	</div>
	<div class="border_div">
		<div class="hide" id="details_test">
			<div id="titre_test"></div>
			<hr/><br/>
			Plages disponibles :
			<div id="plages_horaires_test">
			</div>
			<div class="inline_details_test" id="duree_test"></div>
			<div class="inline_details_test" id="nb_sections_test"></div>
			<div class="inline_details_test" id="seuil1_test"></div>
			<div class="inline_details_test" id="seuil2_test"></div>		
			<hr/>
			<div id="sections"></div>
		</div>
	</div>
</div>
<%@include file="/fragments/bas.jspf"%>
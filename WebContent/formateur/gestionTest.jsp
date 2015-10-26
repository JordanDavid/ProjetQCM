<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String titre = "Gestion des tests"; %>
<%@include file="/fragments/haut.jspf"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/gestionTest.js"></script>
 <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/Tools/JQuery-Smoothness/jquery-ui.min.css"/>
 <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/Tools/JQuery-Smoothness/jquery-ui.theme.min.css"/>
 <!-- Feuille CSS Datatables -->        
 <link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/Tools/DataTables-1.10.9/media/css/jquery.dataTables.min.css"/>

<div id="div_lst_tests">
	<div class="titre_form">
		Liste des tests
	</div>
	<div class="border_div">
		<table id="table_lst_test" class="display">
			<tbody></tbody>
		</table>
		<div align="center">
			<button>Ajouter</button>
			<button>Modifier</button>
			<button>Supprimer</button>
		</div>
	</div>
</div>
<div id="div_details_tests">
	<div class="titre_form">
		Détails du test
	</div>
	<div class="border_div">
		<div id="titre_test"></div>
		<hr/><br/>
		Plages disponibles :
		<div id="plages_horaires_test">
		</div>
		<div class="inline_details_test" id="duree_test"></div>
		<div class="inline_details_test" id="nb_sections_test">Nombre de sections : 3</div>
		<div class="inline_details_test" id="seuil1_test"></div>
		<div class="inline_details_test" id="seuil2_test"></div>		
		<hr/>
		<div id="sections">
			<div class="section">
				<div class="nom_section">Section 1 : POO</div>
				<div class="nb_questions_section">Nombre de questions : 5</div>
			</div>
			<div class="section">
				<div class="nom_section">Section 2 : POO</div>
				<div class="nb_questions_section">Nombre de questions : 5</div>
			</div>
		</div>
	</div>
</div>
<%@include file="/fragments/bas.jspf"%>
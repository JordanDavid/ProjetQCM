/**
 * Contient l'ensemble des fonctions javascripts utilisées
 */

$(document).ready(function() {

	/**
	 * Gestion de l'affichage du menu
	 */
	SelectionMenu = function(li){
		//On enleve l'ancien li active
		$(".liMenu.active").removeClass("active");
		//ON place le nouveau li active
		$(li).addClass("active");	
	}
	
	$('#tableauCandidat').dataTable( {
		"language" : {
			"url" : "../Tools/French.json"
		}
	});
	
	//Création de la datatable contenant les questions du thème sélectionné
	oTableQuestions = $("#lst_questions").dataTable({
		"bSort" : false,
		"bFilter" : false,
		"bInfo" : false,
		"bLengthChange" : false,
		"iDisplayLength": 5,
		"language" : {
			"url" : "../Tools/French.json"
		},
		"columns" : [
    		 {
    			 "data" : "idQuestion",
    			 "bVisible" : false
    		 },
    		 {
    			 "data" : "enonce"
    		 }
         ],
		"sAjaxSource" : "./referentiel?action=getQuestions&id="+$("#themes option:selected")[0].value,
		"fnCreatedRow" : function(nRow, aData,iDataIndex){
			$(nRow).addClass("onclickRow")
			$(nRow).attr("onclick","SelectionQuestion(this);");
			$(nRow).attr("title","Cliquer pour afficher le détail de la question");
		}
	});	
	
	/**
	 * Gestion du changement du thème
	 */
	SelectionTheme = function(){
		oTableQuestions.fnReloadAjax("./referentiel?action=getQuestions&id="+$("#themes option:selected")[0].value);			
	}
	
	SelectionQuestion = function(element){
		var data = oTableQuestions.fnGetData($(element));
		var idTheme = $("#themes option:selected")[0].value;
		var idQuestion = data.idQuestion;
		$.ajax({
			url : "./referentiel",
			method : "POST",
			data : "action=getReponses&idTheme="+idTheme+"&idQuestion="+idQuestion,
			success : function(data){
				console.log(data);
			}
		});
	}
	
});
/**
 * Script pour passer un test
 */
$(document).ready(function(){
	
	/**
	 * Passe à la question suivante
	 */
	Suivant = function(element){
		var q = $(element)[0].dataset.q;
		//passe à la question suivante
		$("#formQuestionTest").attr("action","./test?action=enregistrer&q="+(parseInt(q)+1));
		$("#formQuestionTest").submit();
	};

	/**
	 * Termine le test en cours et redirige vers la page des résultats s'il confirme la fin du test
	 */
	Terminer = function(element){
		var q = $(element)[0].dataset.q;
		$("#formQuestionTest").attr("action","./test?action=recapitulatif&q="+parseInt(q));
		$("#formQuestionTest").submit();
	};
	

	/**
	 * Dialog de confirmation de fin de test
	 */
	dialogConfirmTerminerTest = $("#dialogConfirmTerminerTest").dialog({
		 	autoOpen: false,
	        height: 200,
	        resizable : false,
	        width: 350,
	        modal: true,
	    	buttons : {
	    		"Oui" : function(){
	    			$("#formQuestionTest").attr("action","./test?action=terminer");
	    			$("#formQuestionTest").submit();
	    		},
	    		"Non" : function(){
	    			dialogConfirmTerminerTest.dialog("close");
	    		}
	        },
	        open : function(){
	        	$("#messageConfirmTerminerTest").html("<p>Confirmez-vous avoir terminé le test ? " +
	        			"Une fois terminé vous ne pourrez plus revenir en arrière.</p>");
	        }
	    });
	
});
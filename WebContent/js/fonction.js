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
			$(nRow).addClass("pointer")
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
	
	/**
	 * Gestion de la selection d'une question
	 * @param element
	 */
	SelectionQuestion = function(element){
		aadata = oTableQuestions.fnGetData($(element));
		var idTheme = $("#themes option:selected")[0].value;
		var idQuestion = aadata.idQuestion;
		
		//Alimentation des détails de la réponse
		$("#idQuestion")[0].value = aadata.idQuestion;
		$("#enonce")[0].value = aadata.enonce;
		$("#image")[0].value = aadata.image == null ? "" : aadata.image;
		$("#typeQuestion option").eq(aadata.typeReponse).prop("selected","selected");
		
		$.ajax({
			url : "./referentiel",
			method : "POST",
			data : "action=getReponses&idTheme="+idTheme+"&idQuestion="+idQuestion,
			success : function(data){
				data = data.data;
				var divreponse = "";
				for(var i=0; i<data.length;i++){
					var checked = "";
					divreponse += "<div class=\"reponse\">";
					
					divreponse += "<input type=\"text\" name=\"reponses[]\" id=\"reponse_"+data[i].idReponse+"\" placeholder=\"Veuillez saisir la réponse\" value=\""+data[i].reponse+"\"/>";

					if(data[i].bonneReponse === true)
						checked="checked=\"checked\"";
						
					if(aadata.typeReponse == 0)
						divreponse += "<input class=\"input_reponse\" type=\"radio\" name=\"\" id=\"\" "+checked+" title=\"Cocher pour indiquer la bonne réponse\"/>";					
					else
						divreponse += "<input class=\"input_reponse\" type=\"checkbox\" name=\"\" id=\"\" "+checked+" title=\"Cocher pour indiquer la bonne réponse\"/>";					
					
					divreponse += "</div>";
				}	
				$("#div_reponses_question").html(divreponse);
			}
		});
	};
	
	/**
	 * Gestion de la selection du type de réponse
	 * 0 -> Input type radioButton
	 * 1 -> Input type checkbox
	 */
	SelectionTypeQuestion = function(){
		var typequestion = $("#typeQuestion option:selected")[0].value;
		var type = "radio";
		if(typequestion == 1){
			type = "checkbox";
		}
		$(".input_reponse").each(function(){
			$(this)[0].type = type;
		});
	};
	
	/**
	 * Créeation de la div pour ajouter un thème
	 */
    dialog = $( "#ajoutTheme" ).dialog({
        autoOpen: false,
        height: 250,
        resizable : false,
        width: 350,
        modal: true,
        position : {
        	my: "left top",
        	at: "left bottom",
        	of: $("#ajouterTheme") 
    	},
    	buttons : {
    		"Valider" : function(){
    			$("#formAjoutTheme").submit();
    		},
    		"Annuler" : function(){
    		   dialog.dialog("close");
    		}
        },
        close: function() {
          $("#formAjoutTheme")[0].reset();
        }
    });
	
	/**
	 * Affiche la div pour ajouter un thème
	 */
	AfficherAjoutTheme = function(){
				
		if(dialog.dialog( "isOpen" ))
			dialog.dialog( "close" );
		else
			dialog.dialog( "open" );
	};
	
});


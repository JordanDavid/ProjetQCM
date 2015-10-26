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
	
	
	/*****************************************************************/
	/******************* GESTION REFERENTIEL *************************/
	/*****************************************************************/
	
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
	
	$('#lst_questions tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }else {
        	oTableQuestions.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
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
		$("#idQuestionToDelete")[0].value = aadata.idQuestion;
		$("#idQuestionToChange")[0].value = aadata.idQuestion;
		$("#enonce")[0].value = aadata.enonce;
		$("#image")[0].value = aadata.image == null ? "" : aadata.image;
		$("#typeQuestion option").eq(aadata.typeReponse).prop("selected","selected");
		$("#supprimerQuestion").removeProp("disabled");
		$("#changerTheme").removeProp("disabled");
		
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
					
					divreponse += "<input type=\"hidden\" name=\"reponses\" value=\""+data[i].idReponse+"\"/>";
					divreponse += "<input type=\"text\" class=\"enonce_reponse\" name=\"reponses["+data[i].idReponse+"][enonce]\" id=\"reponse_"+data[i].idReponse+"\" placeholder=\"Veuillez saisir la réponse\" value=\""+data[i].reponse+"\"/>";

					if(data[i].bonneReponse === true)
						checked="checked=\"checked\"";
						
					if(aadata.typeReponse == 0)
						divreponse += "<input class=\"input_reponse\" type=\"radio\" name=\"reponses["+data[i].idReponse+"][valide]\" id=\"\" "+checked+" title=\"Cocher pour indiquer la bonne réponse\"/>";					
					else
						divreponse += "<input class=\"input_reponse\" type=\"checkbox\" name=\"reponses["+data[i].idReponse+"][valide]\" id=\"\" "+checked+" title=\"Cocher pour indiquer la bonne réponse\"/>";					
					
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
	 * Création de la div pour ajouter un thème
	 */
    dialogAjoutTheme = $( "#ajoutTheme" ).dialog({
        autoOpen: false,
        height: 230,
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
    			$("#formAjoutTheme")[0].reset();
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
				
		if(dialogAjoutTheme.dialog( "isOpen" ))
			dialogAjoutTheme.dialog( "close" );
		else
			dialogAjoutTheme.dialog( "open" );
	};
	
	/**
	 * Création d'une fenetre de confirmation de suppression de la thématique
	 */
	dialogConfirmSupprTheme =  $( "#confirmSupprTheme" ).dialog({
        autoOpen: false,
        height: 200,
        resizable : false,
        width: 350,
        modal: true,
        position : {
        	my: "left top",
        	at: "left bottom",
        	of: $("#ajouterTheme") 
    	},
    	buttons : {
    		"Oui" : function(){
    			$("#idThemeSupprime")[0].value = $("#themes option:selected")[0].value;
    			$("#formConfirmSupprTheme").submit();
    		},
    		"Non" : function(){
    			dialogConfirmSupprTheme.dialog("close");
    		}
        },
        open : function(){
        	$("#messageConfirmSupprTheme").html("<p>Confirmez vous la suppression de ce thème ?</p>" +
        			"<p>Toutes les questions et réponses associées a ce thème seront également supprimées.</p>");
        }
    });
	
	/**
	 * Affiche la div pour supprimer un theme
	 */
	AfficherConfirmSupprTheme = function(){
		if(dialogConfirmSupprTheme.dialog("isOpen"))
			dialogConfirmSupprTheme.dialog("close");
		else
			dialogConfirmSupprTheme.dialog("open");
	};
	
	/**
	 * Passe en mode création d'une question
	 */
	AfficherAjouterQuestion = function(){
		$("#idQuestion")[0].value = "-1";
		$("#enonce")[0].value = "";
		$("#image")[0].value = "";
		$("#typeQuestion option").eq(0).prop("selected","selected");
		$("#supprimerQuestion").prop("disabled","disabled");
		$("#changerTheme").prop("disabled","disabled");
		
		var divreponse = "";
		for(var i=0; i<2;i++){
			divreponse += "<div class=\"reponse\">";
			divreponse += "<input type=\"hidden\" name=\"reponses\" value=\""+(i-1)+"\"/>";
			divreponse += "<input type=\"text\" class=\"enonce_reponse\" name=\"reponses["+(i-1)+"][enonce]\" id=\"reponse_n"+(i-1)+"\" placeholder=\"Veuillez saisir la réponse\" />";
			divreponse += "<input class=\"input_reponse\" type=\"radio\" name=\"reponses["+(i-1)+"][valide]\" id=\"\" title=\"Cocher pour indiquer la bonne réponse\"/>";					
			divreponse += "</div>";
		}	
		$("#div_reponses_question").html(divreponse);
	};

	/**
	 * Enregistre la question
	 */
	EnregistrerQuestion = function(){
		var messageErreur= VerifValideQuestion();
		if(messageErreur == null){
			
			$("#gestion_referentiel")[0].action = "./referentiel?action=enregistrerQuestion";
			
			var reponses = new Array();
			
			var i=0;
			$(".reponse").each(function(){
				reponses[i] = {
					"idReponse":$(this)[0].children[0].value,
					"reponse":$(this)[0].children[1].value,
					"bonneReponse":$(this)[0].children[2].checked
				}
				i++;
			});
			
			$("#lst_reponses")[0].value = JSON.stringify(reponses);
			
			$("#gestion_referentiel").submit();

		}else{
			console.log("Erreur "+messageErreur);
		}
	};
	
	/**
	 * Vérifie que la question saisi est valide
	 * @returns Message d'erreur sinon null;
	 */
	VerifValideQuestion = function(){
		var message = "";
		var debutmessage = "Erreur lors de l'ajout de la question :";
		
		//L'énoncé doit obligatoirement etre saisi
		if($("#enonce")[0].value == null || $("#enonce")[0].value == ""){
			message += "<br/>L'énoncé doit obligatoirement être saisi"
		}
		
		//vérifier qu'il y ait au moins deux réponse de saisie
		if(!VerifNbReponse()){
			message += "<br/>Vous devez saisir au moins deux réponses"
		}
		
		if($("#typeQuestion option:selected")[0].value == 0){
		//Si le type de question est égale à 0 (une seule bonne réponse), vérifier qu'il y ait une réponse de cochée
			if(!VerifNbBonnesReponses(1)){
				message += "<br/>Il doit y avoir une bonne réponse pour ce type de question";				
			}
		}else{
		//Si le type de question est égale à 1 (plusieurs bonne réponse), vérifier qu'il y ait au moins deux réponses de cochées
			if(!VerifNbBonnesReponses(1)){
				message += "<br/>Il doit y avoir une bonne réponse pour ce type de question";
			}			
		}

		if(message != "")
			return debutmessage+message;
		else
			return null;
	};
	
	/**
	 * Vérifie que le nombre de réponse est supérieur à 2
	 * @returns {Boolean} Vrai s'il y a plus de deux réponses sinon faux
	 */
	VerifNbReponse = function(){
		var nbReponseOK = 0;
		$(".enonce_reponse").each(function(){
			if($(this)[0].value != "")
				nbReponseOK++;
		});
		return nbReponseOK >= 2;
	};
	
	/**
	 * Vérifie que l nombre de bonnes réponses cochées est suffisant
	 * @param minBonnesReponses Le nombre minimum de réponse à cocher
	 * @returns {Boolean} Vrai si le nombre de bonnes réponses cochées est supérieur ou égale 
	 * au nombre minimum de bonnes réponses obligatoire, sinon faux
	 */
	VerifNbBonnesReponses = function(minBonnesReponses){
		var nbBonnesReponses = 0;
		$(".input_reponse").each(function(){
			if($(this)[0].checked)
				nbBonnesReponses++;
		});
		return  nbBonnesReponses >= minBonnesReponses;
	};
	
	
	var numAddReponse = -2;
	/**
	 * Ajoute une possibilité de réponse à la question
	 */
	ajouterReponse = function(){
		
		var type = $("#typeQuestion option:selected")[0].value == 0 ? "radio" : "checkbox";		
		var divreponse = "";
		divreponse += "<div class=\"reponse\">";
		divreponse += "<input type=\"hidden\" name=\"reponses\" value=\""+numAddReponse+"\"/>";
		divreponse += "<input type=\"text\" class=\"enonce_reponse\" name=\"reponses\" id=\"reponse_n"+numAddReponse+"\" placeholder=\"Veuillez saisir la réponse\" />";
		divreponse += "<input class=\"input_reponse\" type=\""+type+"\" name=\"reponses\" title=\"Cocher pour indiquer la bonne réponse\"/>";					
		divreponse += "</div>";
		numAddReponse--;
		$("#div_reponses_question").append(divreponse);
	};
	
	/**
	 * Pop up de confirmation de suppression de question
	 */
	dialogConfirmSupprQuestion = $( "#confirmSupprQuestion" ).dialog({
        autoOpen: false,
        height: 200,
        resizable : false,
        width: 350,
        modal: true,
    	buttons : {
    		"Oui" : function(){
    			if($("#idQuestionToDelete")[0].value != ""){
    				//Récupérer l'identifiant de la ligne selectionné
        			$("#formConfirmSupprQuestion").submit();
    			}
    		},
    		"Non" : function(){
    			dialogConfirmSupprQuestion.dialog("close");
    		}
        },
        open : function(){
        	$("#messageConfirmSupprQuestion").html("<p>Confirmez vous la suppression de cette question ?</p>" +
        			"<p>Toutes les réponses associées à cette question seront également supprimées.</p>");
        }
    });
	
	
	/**
	 * Affiche la pop up de confirmation de suppression de question
	 */
	AfficherConfirmSupprQuestion = function(){
		if(dialogConfirmSupprQuestion.dialog("isOpen"))
			dialogConfirmSupprQuestion.dialog("close");
		else
			dialogConfirmSupprQuestion.dialog("open");
	};
	
	/**
	 * Pop up pour changer le thème d'une question
	 */
	dialogChangerTheme = $( "#changerThemeQuestion" ).dialog({
        autoOpen: false,
        height: 180,
        resizable : false,
        width: 300,
        modal: true,
    	buttons : {
    		"Valider" : function(){
    			if($("#idQuestionToChange")[0].value != ""){
    				//Récupérer l'identifiant de la ligne selectionné
        			$("#formChangerTheme").submit();
    			}
    		},
    		"Annuler" : function(){
    			dialogChangerTheme.dialog("close");
    		}
        }
    });
	
	
	/**
	 * Affiche la pop up pour change le thème d'une question
	 */
	AfficherChangerTheme = function() {
		if(dialogChangerTheme.dialog("isOpen"))
			dialogChangerTheme.dialog("close");
		else
			dialogChangerTheme.dialog("open");
	};
});

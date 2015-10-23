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
					
					divreponse += "<input type=\"text\" class=\"enonce_reponse\" name=\"reponses[]\" id=\"reponse_"+data[i].idReponse+"\" placeholder=\"Veuillez saisir la réponse\" value=\""+data[i].reponse+"\"/>";

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
    			dialogConfirmSupprTheme.Close();
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
		var divreponse = "";
		for(var i=0; i<2;i++){
			divreponse += "<div class=\"reponse\">";
			divreponse += "<input type=\"text\" class=\"enonce_reponse\" name=\"reponses[]\" id=\"reponse_n"+i+"\" placeholder=\"Veuillez saisir la réponse\" />";
			divreponse += "<input class=\"input_reponse\" type=\"radio\" name=\"\" id=\"\" title=\"Cocher pour indiquer la bonne réponse\"/>";					
			divreponse += "</div>";
		}	
		$("#div_reponses_question").html(divreponse);
	};

	/**
	 * Enregistre la question
	 */
	EnregistrerQuestion = function(){
		if(VerifValideQuestion()){
//			var enonce = $("#enonce")[0].value;
//			var image = $("#image")[0].value;
//			var typeQuestion = $("#typeQuestion option:selected")[0].value;
						
			$.ajax({
				url : "./formateur/referentiel",
				method : "POST",
				data : "action=enregistrerQuestion",
				success : function(){
					console.log("success");
				}					
			});
		}
	}
	
	/**
	 * Vérifie que la question saisi est valide
	 */
	VerifValideQuestion = function(){
		var valide = true;
		var message = "Erreur lors de l'ajout de la question :";
		
		//L'énoncé doit obligatoirement etre saisi
		if($("#enonce")[0].value == null || $("#enonce")[0].value == ""){
			valide = valide & false;
			message += "<br/>L'énoncé doit obligatoirement être saisi"
		}
		
		//vérifier qu'il y ait au moins deux réponse de saisie
		if(!VerifNbReponse()){
			valide = valide & false;
			message += "<br/>Il doit y avoir au minimum deux réponses possibles"
		}
		
		//Si le type de question est égale à 0 (une seule bonne réponse), vérifier qu'il y ait une réponse de cochée
		
		//Si le type de question est égale à 1 (plusieurs bonne réponse), vérifier qu'il y ait au moins deux réponses de cochées
		
		
		return true;
	}
	
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
		
		if(nbReponseOK < 2)
			return false;
	}
	
});


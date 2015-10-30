/**
 * 
 */
$(document).ready(function(){
	/*****************************************************************/
	/************************ GESTION TEST ***************************/
	/*****************************************************************/

	/**
	 * Création de la datatable contenant les tests
	 */
	oTableTest  = $("#table_lst_test").dataTable({
		"bSort" : false,
		"bFilter" : false,
		"bInfo" : false,
		"bLengthChange" : false,
		"iDisplayLength": 15,
		"language" : {
			"url" : "../Tools/French.json"
		},
		"columns" : [
			 {
				 "data" : "libelle"
			 }
	     ],
		"sAjaxSource" : "./gestionTests?action=getTests",
		"fnCreatedRow" : function(nRow, aData,iDataIndex){
			$(nRow).addClass("pointer")
			$(nRow).attr("onclick","SelectionTest(this);");
			$(nRow).attr("title","Cliquer pour afficher le détail du test");
		}
	});
	
	$('#table_lst_test tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
            $("#btn_modifierTest").attr("disabled","disabled");
            $("#btn_supprimerTest").attr("disabled","disabled");
        }else {
        	oTableTest.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
            $("#btn_modifierTest").removeAttr("disabled");
            $("#btn_supprimerTest").removeAttr("disabled");
        }
    });
	
	
	/**
	 * Méthode en charge d'afficher l détail d'un test après sa sélection
	 * @param row Ligne sélectionnée
	 */
	SelectionTest = function(row){
		$("#details_test").addClass("show")
		aaData = oTableTest.fnGetData($(row));

		//Alimentation des données statiques du test sélectionné
		$("#idTest")[0].value = aaData.id;
		$("#idTestToDelete")[0].value = aaData.id;
		$("#titre_test").html(aaData.libelle);
		$("#duree_test").html("Durée : " + aaData.duree);
		$("#seuil1_test").html("Seuil n°1 : " + aaData.seuil_maximum);
		$("#seuil2_test").html("Seuil n°2 : " + aaData.seuil_minimum);
		
		//Récupération des plages horaires du test
		$.ajax({
			action : "/gestionTest",
			method : "POST",
			data : "action=getPlagesHoraires&idTest="+aaData.id,
			success : function(data){
				var plages = "";
				if(data.data.length == 0)
					plages = "Aucunes plages horaires définies";
				for(var i=0;i<data.data.length;i++){
					console.log(data);
					plages += "<div class=\"plage_horaire_test\">";
					plages += data.data[i].dateDebut +" - " +data.data[i].dateFin
					plages += "</div>";
				}
				$("#plages_horaires_test").html(plages)
			}
		});
		
		//Récupération des sections du test
		$.ajax({
			action : "/gestionTest",
			method : "POST",
			data : "action=getSections&idTest="+aaData.id,
			processData : false,
			success : function(data){
				var sections = "";
				var numSection = 1;
				if(data.data.length == 0)
					sections = "Aucunes plages horaires définies";
				for(var i=0;i<data.data.length;i++){
					sections += "<div class=\"section\">";
					sections += "<div class=\"nom_section\">Section "+numSection+" : " + data.data[i].theme.libelle +"</div>";
					sections += "<div class=\"nb_questions_section\">Nombre de questions : "+data.data[i].nbQuestion+"</div>";
					sections += "</div>";
					numSection++;
				}
				$("#nb_sections_test").html("Nombre de sections : " + data.data.length);
				$("#sections").html(sections);
			}
		});
	};
	
	/**
	 * Dialog de confirmation de suppression de test
	 */
	dialogConfirmSupprimerTest = $( "#confirmSuppressionTest" ).dialog({
        autoOpen: false,
        height: 200,
        resizable : false,
        width: 350,
        modal: true,
    	buttons : {
    		"Oui" : function(){
    			if($("#idTestToDelete")[0].value != ""){
    				//Récupérer l'identifiant de la ligne selectionné
        			$("#formConfirmSupprTest").submit();
    			}
    		},
    		"Non" : function(){
    			dialogConfirmSupprimerTest.dialog("close");
    		}
        },
        open : function(){
        	$("#messageConfirmSupprTest").html("<p>Confirmez vous la suppression de ce test ?</p>" +
        			"<p>Toutes les élément en relations avec ce test seront également supprimés.</p>");
        }
    });
	
	/**
	 * Affiche la boite de dialog pour confirmer la suppression d'un test
	 */
	afficherSupprimerTest = function(){
		if(dialogConfirmSupprimerTest.dialog( "isOpen" ))
			dialogConfirmSupprimerTest.dialog( "close" );
		else
			dialogConfirmSupprimerTest.dialog( "open" );
	};
	
	/**
	 * Affiche la page d'ajout de test
	 */
	afficherAjoutTest = function(){
		window.location.href = "./gestionTests?action=afficherGererTest";
	}
	
	/**
	 * Affiche la page de modification de test
	 */
	afficherModificationTest = function(){
		window.location.href = "./gestionTests?action=afficherGererTest&id="+$("#idTest")[0].value;
	}

	/**
	 * Création de la datatable des plages horaires
	 */
	oTablePlageHorairesTest = $("#lst_plages_horaires").dataTable({
		"bSort" : false,
		"bFilter" : false,
		"bInfo" : false,
		"bLengthChange" : false,
		"sScrollY": "80px",
        "bScrollCollapse": true,
		"iDisplayLength": 3,
		"language" : {
			"url" : "../Tools/French.json"
		},
		"columns" : [
			 {
				 "data" : "idPlage",
				 "bVisible" : false
			 },{
				 "data" : "libelle"
			 },{
				 "data" : "debut",
				 "bVisible" : false
			 },{
				 "data" : "fin",
				 "bVisible" : false
			 }
	     ],
		"fnCreatedRow" : function(nRow, aData,iDataIndex){
			$(nRow).addClass("pointer")
			$(nRow).attr("title","Cliquer pour sélectionner cette plage horaire");
		}
	});

	$('#lst_plages_horaires tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
    		$("#btn_SupprimePlage").attr("disabled","disabled");
        }else {
        	oTablePlageHorairesTest.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
    		$("#btn_SupprimePlage").removeAttr("disabled");
        }
    });
	
	dtp_Debut = $("#datetimepickerdebut").datetimepicker({
		lang:'fr',
		format:'d/m/Y H:i'
	});
	dtp_Fin = $("#datetimepickerfin").datetimepicker({
		lang:'fr',
		format:'d/m/Y H:i'
	});
	
	/**
	 * Enregistre l'ajout ou la modification du test
	 */
	enregistrerModifTest = function(){
		var messageErreur = verifValideTest();
		if(messageErreur == null){
			//Construit le json pour les plages horaires
			var plages = new Array();
			var dataPlages = oTablePlageHorairesTest.fnGetData();
			for(var i=0; i < dataPlages.length;i++){
				plages[i] = dataPlages[i];
			}
			$("#lst_plages")[0].value=JSON.stringify(plages);
			
			//Construit le json pour les sections
			var sections = new Array();
			var y = 0 ;
			$(".section_test").each(function(){
				var idSection = $(this)[0].dataset.id;
								
				var idThemeSection = $("#select_theme_section_"+idSection +" option:selected")[0].value;
				var nbQuestion = $("#nb_questions_section_"+idSection)[0].value;
				sections[y] = {
					"idSection" : i,
					"idTheme" : idThemeSection,
					"nbQuestion" : nbQuestion
				};
				y++;
			});
			$("#lst_sections")[0].value= JSON.stringify(sections);
			
			$("#formGererTest").submit();
		}else{
			afficherErreur("Enregistrer test", messageErreur);
		}
	};
	
	/**
	 * Vérifie que le test est valide
	 * @returns Un message dd'erreur s'il n'est pas valide sinon null
	 */
	verifValideTest = function(){
		var message = "";
		var debutMessage = "Erreur lors de l'enregistrement du test :";
		
		if($("#nom")[0].value == "" || $("#nom")[0].value == null){
			message += "<br/>Le nom doit obligatoirement être saisi"
		}
		
		if($("#duree")[0].value <= 1){
			message += "<br/>La durée du test doit être supérieur à 1 minutes"
		}
		
		if($("#nbSections")[0].value < 1){
			message += "<br/>Le nombre de section doit être supérieur à 1";
		}
		
		if(parseInt($("#seuil1")[0].value) >= parseInt($("#seuil2")[0].value)){
			message += "<br/>Le 1er seuil doit être inférieur au deuxième";
		}
		
		var dataPlage = oTablePlageHorairesTest.fnGetData();
		if(dataPlage.length < 1){
			message += "<br/>Vous devez créer au moins une plage horaire pour le test"
		}
		
		var nbErreur = 0
		$(".valide_nb_question_sections").each(function(){
			if($(this)[0].value == "false")
				nbErreur++;
		});
		
		if(nbErreur > 0){
			message += "<br/>"+nbErreur+" champs section sont incorrect";
		}

		if(message != "")
			return debutMessage + message;
		else return null;
		
	};

	
	/**
	 * Ajoute une plage dans la datatable
	 */
	ajouterPlage = function(){
		$("#btn_SupprimePlage").attr("disabled","disabled");
		if(validePlage()){
			var add = {"idPlage" : 0,"libelle":dtp_Debut.val() + " - " + 
					dtp_Fin.val(),"debut":dtp_Debut.val(),"fin":dtp_Fin.val()};
			oTablePlageHorairesTest.fnAddData(add);
		} else{
			afficherErreur("Sélection plage horaire", "La date de début du test doit être antérieur à celle de fin. Veuillez modifier les horaires sélectionnés")
		}
	};

	/**
	 * Supprime une plage dans la datatable
	 */
	supprimerPlage = function(){
		var row = $("#lst_plages_horaires tbody tr.selected");
		oTablePlageHorairesTest.fnDeleteRow(row);
	}
	
	
	/**
	 * Vérifie que la plage horaire est valide
	 */
	validePlage = function(){
		return dtp_Debut.val() < dtp_Fin.val();
	}
	
	var valueBaseNbSection = $("#nbSections")[0].defaultValue;
	/**
	 * Le changement du nombre de section impacte sur le nombre de section a saisir
	 */
	changeNbSection = function(element){
		var value = parseInt($(element)[0].value);
		//on ajoute une section si la nouvelle valeur est plus grande que l'ancienne
		//Sinon on en supprime une
		if(value > valueBaseNbSection){
			var div = $( ".new_section_test" ).eq(0).clone();
			div.removeClass("hide");
			div.removeClass("new_section_test");
			div.addClass("section_test");
			div.appendTo( "#sections_test");
		}else{
			//problème
			$( ".section_test" ).last().remove();
		}
		
		valueBaseNbSection = value;
	};
	
	/**
	 * La changement du nombre de question entraine une vérification du nombre maxi de questions possibles
	 */
	changeNbQuestion = function(element){
		var id = $(element)[0].id.split("_").pop();
		if(id != 0){
			
			var idTheme = $("#select_theme_section_"+id+" option:selected")[0].value;

			$.ajax({
				url : "./gestionTests?action=valideQuestion",
				method : "POST",
				data : "idTheme="+idTheme+"&nbQuestion="+$(element)[0].value,
				success : function(data){
					if(parseInt(data) === 1){
						$("#valide_section_"+id)[0].value = true;
						$("#img_valide_section_"+id).attr("src","/QCM/images/valide.png");
					}else{
						$("#valide_section_"+id)[0].value = false;
						$("#img_valide_section_"+id).attr("src","/QCM/images/novalide.png");
					}
				}
			});
			
		}else{
			
		}
	};
	
	/**
	 * Retour à la page de selection des tests
	 */
	retourGestionTest = function(){
		window.location.href = "./gestionTests";
	};
	


	
});
/*****************************************************************/
/************************ GESTION TEST ***************************/
/*****************************************************************/
$(document).ready(function(){


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
        }else {
        	oTableTest.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    });
	
	
	/**
	 * Méthode en charge d'afficher l détail d'un test après sa sélection
	 * @param row Ligne sélectionnée
	 */
	SelectionTest = function(row){
		$("#details_test").toggleClass("hide show")
		aaData = oTableTest.fnGetData($(row));

		//Alimentation des données statiques du test sélectionné
		$("#idTest")[0].value = aaData.id;
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
		if(verifValideTest() == null){
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
				var idSection = $(this)[0].dataset.id
				var idThemeSection = $("#select_theme_section_"+idSection +" option:selected")[0].value;
				var nbQuestion = $("#nb_questions_section_"+idSection)[0].value;
				sections[y] = {
					"idSection" : idSection,
					"idTheme" : idThemeSection,
					"nbQuestion" : nbQuestion
				};
				y++;
			});
			$("#lst_sections")[0].value= JSON.stringify(sections);
			
			$("#formGererTest").submit();
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
			if($(this)[0].value == false)
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
	 * Ajoute une plage dans la datagridview
	 */
	ajouterPlage = function(){
		$("#btn_SupprimePlage").attr("disabled","disabled");
		if(validePlage()){
			var add = {"idPlage" : 0,"libelle":dtp_Debut.val() + " - " + 
					dtp_Fin.val(),"debut":dtp_Debut.val(),"fin":dtp_Fin.val()};
			oTablePlageHorairesTest.fnAddData(add);
		}
	};

	
	/**
	 * Vérifie que la plage horaire est valide
	 */
	validePlage = function(){
		return dtp_Debut.val() < dtp_Fin.val();
	}
	
});
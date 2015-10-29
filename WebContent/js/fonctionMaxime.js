
$(document).ready(function() {
	
	///////// Tableau des TESTS /////////	
	
	oTableTests = $("#list_tests").dataTable({
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
    			 "data" : "id",
    			 "bVisible" : false
    		 },
    		 {
    			 "data" : "libelle"
    		 }
         ],
		"sAjaxSource" : "./inscription?action=getTests&id="+$("#themes option:selected")[0].value
	});
	
	// FONCTION appelée losqu'on coche une checkbox du tableau CANDIDAT
	idCandidats = document.getElementById("idCandidats");
	selectCandidat = function(){
		var selected = new Array();
		$("input:checkbox[name=select_candidats]:checked").each(function() {
		       selected.push($(this)[0].value);
		  });			
		console.log(selected);
		
		idCandidats.setAttribute("value", JSON.stringify(selected));
	}
	
	// suivant la LIGNE SELECTIONNEE, on SET les input caché suivant l'ID et le LIBELLE du TEST
    idTest = document.getElementById("idTest");
    libelleTest = document.getElementById("libelleTest");

    $('#list_tests tbody').on( 'click', 'tr', function () {
    	if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
            idTest.setAttribute("value", "0");
        }else {
            oTableTests.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
            idTest.setAttribute("value", oTableTests.fnGetData($(this)).id);
            libelleTest.setAttribute("value", oTableTests.fnGetData($(this)).libelle);
        }
    });
	
	// RAFRAICHISSEMENT du tableau des tests suivant la liste déroulante
	SelectionThemeForTest = function(){
		oTableTests.fnReloadAjax("./inscription?action=getTests&id="+$("#themes option:selected")[0].value);
	}
	
	// AFFICHAGE du dernier tableau de la page
	oTableTestsSelectionnes = $("#tabTestsSelect").DataTable({
		"bSort" : false,
		"bFilter" : false,
		"bInfo" : false,
		"bLengthChange" : false,
		"iDisplayLength": 5,
		"language" : {
			"url" : "../Tools/French.json"
		},
		"columnDefs": [
           {
               "targets": [0],
               "visible": false,
               "searchable": false
           },
           {
               "targets": [2],
               "visible": false,
               "searchable": false
           }
       ]
	});
	
	// Rendre possible la SELECTION du tableau
	var table = $('#tabTestsSelect').DataTable(); 
    $('#tabTestsSelect tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
	});
    
    // SUPPRESSION d'une ligne
    $('#deleteButton').click( function () {
        table.row('.selected').remove().draw( false );
    });
	
    // FONCTION appelé lorsqu'on clique sur une LIGNE du tableau des TESTS
    SelectionTest = function( ){		
		// APPEL de la fonction afficherPlageHoraire (pop-up)
		$("#ajouterCandidatToTheme").attr("onclick", "afficherPlageHoraire()");
	};
	
	// FONCTION appelé lorsqu'on clique sur le bouton "Ajouter"
	RechargerPlages = function()
	{	
		afficherPlageHoraire();
		var $id = document.getElementById("idTest").value;										
		oTableTestsPlageHoraire.fnReloadAjax("./inscription?action=getPlageHoraire&id="+$id);		
	};
    
    // AFFICHE la POP-UP (voir onClick du bouton "ajouterCandidatToTheme")
    afficherPlageHoraire = function(){		
		dialogchoixPlage = $("#choixPlage").dialog({
			autoOpen: false,
	        height: 350,
	        resizable : false,
	        width: 450,
	        modal: true,
	        position : {
	        	my: "right top",
	        	at: "right bottom",
	        	of: $("#choixPlage") 
	    	},
	        open: function() {
	        	listTestsPlageHoraire();
	        }
	    });		
		if(dialogchoixPlage.dialog( "isOpen" ))
			dialogchoixPlage.dialog( "close" );
		else
			dialogchoixPlage.dialog( "open" );		
	};
	
	// FONCTION appelé lorsqu'on clique sur le bouton "Valider" de la POP UP
	tableTestsSelect = $("#tabTestsSelect").DataTable();	 
    $('#validerAjoutTest').on( 'click', function () {
    	var $idTest = document.getElementById("idTest").value;
    	var $libelleTest = document.getElementById("libelleTest").value;
		var $idPlage = document.getElementById("idPlage").value;
		var $dateDebutPlage = document.getElementById("dateDebutPlage").value;
		var $dateFinPlage = document.getElementById("dateFinPlage").value;

		// TODO : delete this, bitch !
		console.log($idTest);
		console.log($libelleTest);
		console.log($idPlage);
		console.log($dateDebutPlage);
		console.log($dateFinPlage);
		
		tableTestsSelect.row.add( [
			$idTest, 
			$libelleTest,
			$idPlage,
			$dateDebutPlage,
			$dateFinPlage
        ] ).draw(false);
		dialogchoixPlage.dialog("close");
    } );
	
    // AFFICHE le contenu du tableau des PLAGE HORAIRE de la POP-UP
    var oTableTestsPlageHoraire = null;
    $idTest = document.getElementById("idTest").value;
	listTestsPlageHoraire = function(){
		if (oTableTestsPlageHoraire == null) {
			oTableTestsPlageHoraire = $("#tabPlagesHoraires").dataTable({
    		"bSort" : false,
    		"bFilter" : false,
    		"bInfo" : false,
    		"bLengthChange" : false,
			"paging" : false,
    		"language" : {
    			"url" : "../Tools/French.json"
    		},
    		"sAjaxSource" : "./inscription?action=getPlageHoraire&id="+$idTest,
    		"columns" : [
	    		{
	   				"data" : "idPlageHoraire",
		   			visible : false
	   		 	},
   				{
		   			"data" : "dateDebut"
		   		},
		   		{
		   			"data" : "dateFin"
		   		}
	         ]
			});
		}
		else {
			oTableTestsPlageHoraire.fnReloadAjax("./inscription?action=getPlageHoraire&id="+$idTest);
		}
	}
	
	// SET les attributs du tableau (tableau des plages horaire de la POP UP) à l'input correspondant
    $('#tabPlagesHoraires tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }else {
        	oTableTestsPlageHoraire.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
        var plageSelect = document.getElementById("idPlage");
        var dateDebutPlageSelect = document.getElementById("dateDebutPlage");
        var dateFinPlageSelect = document.getElementById("dateFinPlage");
        plageSelect.setAttribute("value", oTableTestsPlageHoraire.fnGetData($(this)).idPlageHoraire);
        dateDebutPlageSelect.setAttribute("value", oTableTestsPlageHoraire.fnGetData($(this)).dateDebut);
        dateFinPlageSelect.setAttribute("value", oTableTestsPlageHoraire.fnGetData($(this)).dateFin);
	    });
	
    // DATETIMEPICKER
    dtp_Debut = $("#InscriptionDatetimepickerdebut").datetimepicker({
		lang:'fr',
		format:'d/m/Y H:i'
	});
	dtp_Fin = $("#InscriptionDatetimepickerfin").datetimepicker({
		lang:'fr',
		format:'d/m/Y H:i'
	});
	
	/**
	 * Ajoute une plage dans la datatable
	 */
	ajouterPlage = function(){
		if(validePlage()){
			var add = {"idPlageHoraire" : 0,"dateDebut":dtp_Debut.val(),"dateFin":dtp_Fin.val()};
			oTableTestsPlageHoraire.fnAddData(add);
		} else{
			alert("Saisie invalide");
		}
	};
	
	/**
	 * Vérifie que la plage horaire est valide
	 */
	validePlage = function(){
		return dtp_Debut.val() < dtp_Fin.val();
	}
	
});

/**
 * 
 */
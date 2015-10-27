
$(document).ready(function() {
	
//	 Handle click on Select all control
	$("#select-all").on("click", function(){
//	       Get all rows with search applied
	      var rows = table.rows({ "search": "applied" }).nodes();
//	       Checkuncheck checkboxes for all rows in the table
	      $("input[type=checkbox]", rows).prop("checked", this.checked);
	   });
	
//	 Handle click on checkbox to set state of Select all control
	$("#tableauCandidat tbody").on("change", "input[type=checkbox]", function(){
//	       If checkbox is not checked
	      if(!this.checked){
	         var el = $("#select-all").get(0);
//	          If Select all control is checked and has 'indeterminate' property
	         if(el && el.checked && ("indeterminate" in el)){
//	             Set visual state of Select all control 
//	             as 'indeterminate'
	            el.indeterminate = true;
	         }
	      }
	   });
	
//	 Handle form submission event
	$("#frm-tableauCandidat").on("submit", function(e){
	      var form = this;

//	       Iterate over all checkboxes in the table
	      table.$("input[type=checkbox]").each(function(){
//	          If checkbox doesn't exist in DOM
	         if(!$.contains(document, this)){
//	             If checkbox is checked
	            if(this.checked){
//	                Create a hidden element 
	               $(form).append(
	                  $("input")
	                     .attr("type", "hidden")
	                     .attr("name", this.name)
	                     .val(this.value)
	               );
	            }
	         } 
	      });
	   });
	
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
		"sAjaxSource" : "./inscription?action=getTests&id="+$("#themes option:selected")[0].value,
		"fnCreatedRow" : function(nRow, aData,iDataIndex){
			$(nRow).addClass("pointer")
			$(nRow).attr("title","Cliquer pour sélectionner un test");
			$(nRow).on( "click", SelectionTest );
		}
	});
	
	// AJOUT de l'attribut au bouton "AJOUTER"
	SelectionTest = function( ){		
		 var sData = oTableTests.fnGetData( this );		
		//Ajout d'un attribut définissant le test sélectionné au bouton "Ajouter"
		$("#ajouterCandidatToTheme").attr("onclick", "afficherPlageHoraire("+ sData.id +")");
	};
	
	// RAFRAICHISSEMENT du tableau des tests suivant la liste déroulante
	SelectionThemeForTest = function(){
		oTableTests.fnReloadAjax("./inscription?action=getTests&id="+$("#themes option:selected")[0].value);
	}
	
	// suivant la ligne sélectionnée, on set les input caché suivant l'id et le libelle du test
	var table = $('#list_tests').DataTable(); 
    var idTest = document.getElementById("idTest");
    var libelleTest = document.getElementById("libelleTest");

    $('#list_tests tbody').on( 'click', 'tr', function () {
    	if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
            idTest.setAttribute("value", "0");
        }else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
            idTest.setAttribute("value", table.cell('.selected', 0).data());
            libelleTest.setAttribute("value", table.cell('.selected', 1).data());
        }
    });
	
    // AFFICHE la pop-up des plage horaire
	afficherPlageHoraire = function(idTest){
		
		dialogAjoutCandidatToTheme = $("#ajoutCandidatToTheme").dialog({
			autoOpen: false,
	        height: 350,
	        resizable : false,
	        width: 450,
	        modal: true,
	        position : {
	        	my: "right top",
	        	at: "right bottom",
	        	of: $("#ajoutCandidatToTheme") 
	    	},
	    	buttons : {
	    		"Valider" : function(){
	    			var $idTest = document.getElementById("idTest").value;
	    			console.log($idTest);
	    			var $libelleTest = document.getElementById("libelleTest").value;
	    			console.log($libelleTest);
	    			
//	    			var $dateFin = document.getElementById("dateFin").value;	    			
	    			oTableTestsSelectionnes.row.add( [$idTest, $libelleTest] ).draw(false);
	    		},
	    		"Annuler" : function(){
	    			$("#formAjoutCandidatToTheme")[0].reset();
	  	          dialogAjoutCandidatToTheme.dialog("close");
	    		}
	        },
	        close: function() {
	          $("#formAjoutCandidatToTheme")[0].reset();
	        },
	        open: function() {
	        	listTestsPlageHoraire(idTest);
	        }
	    });
		
		if(dialogAjoutCandidatToTheme.dialog( "isOpen" ))
			dialogAjoutCandidatToTheme.dialog( "close" );
		else
			dialogAjoutCandidatToTheme.dialog( "open" );
		
		
	};
	
	// A COMPLETER : Suivant la ligne sélectionnée, on set les attributs
	var table = $('#list_tests_plage_horaire').DataTable(); 
    $('#list_tests_plage_horaire tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
        var plageSelect = document.getElementById("idPlage");
        var dateDebutPlageSelect = document.getElementById("dateDebutPlage");
        var dateFinPlageSelect = document.getElementById("dateFinPlage");
        plageSelect.setAttribute("value", table.cell('.selected', 0).data());
        dateDebutPlageSelect.setAttribute("value", table.cell('.selected', 1).data());
        dateFinPlageSelect.setAttribute("value", table.cell('.selected', 2).data());
	    });
	
    // fonction qui affiche le contenu du tableau des plage d'horaire
	var oTableTestsPlageHoraire = null;
	listTestsPlageHoraire = function(idTest){
		if (oTableTestsPlageHoraire == null) {
				oTableTestsPlageHoraire = $("#list_tests_plage_horaire").dataTable({
	    		"bSort" : false,
	    		"bFilter" : false,
	    		"bInfo" : false,
	    		"bLengthChange" : false,
	    		"iDisplayLength": 5,
	    		"language" : {
	    			"url" : "../Tools/French.json"
	    		},
	    		"sAjaxSource" : "./inscription?action=getPlageHoraire&id="+idTest,
	    		"columns" : [
		    		{
		   				"data" : "id",
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
		else{
			oTableTestsPlageHoraire.fnReloadAjax("./inscription?action=getPlageHoraire&id="+idTest);
		}
	}
	
	// A SUPPRIMER SANS DOUTE
    var table = $('#list_tests_plage_horaire').DataTable(); 
    $('#list_tests_plage_horaire tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
        var plageSelect = document.getElementById("idPlage");
        var dateDebutPlageSelect = document.getElementById("dateDebutPlage");
        var dateFinPlageSelect = document.getElementById("dateFinPlage");
        plageSelect.setAttribute("value", table.cell('.selected', 0).data());
        dateDebutPlageSelect.setAttribute("value", table.cell('.selected', 1).data());
        dateFinPlageSelect.setAttribute("value", table.cell('.selected', 2).data());
	    });
	
    // Définit les date picker
	$(function() {
		$("#date_picker_debut").datepicker();
	  });
	
	$(function() {
		$("#date_picker_fin").datepicker();
	  });	
	
//	$('#list_tests tbody').on( 'click', 'tr', function () {
//        if ( $(this).hasClass('selected') ) {
//            $(this).removeClass('selected');
//        }else {
//        	oTableTests.$('tr.selected').removeClass('selected');
//            $(this).addClass('selected');
//        }
//	    });	
	
	// AFFICHAGE du dernier tableau de la page
	oTableTestsSelectionnes = $("#list_tests_selectionnes").dataTable({
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
	
	// Rendre possible la sélection du tableau
	var table = $('#list_tests_selectionnes').DataTable(); 
    $('#list_tests_selectionnes tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
	    });
    // A COMPRENDRE : Suppression d'une ligne
    $('#deleteButton').click( function () {
        table.row('.selected').remove().draw( false );
    } );
	
});

/**
 * 
 */
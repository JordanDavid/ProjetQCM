
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
		"sAjaxSource" : "./inscription?action=getTests&id="+$("#themes option:selected")[0].value
	});
	
	// suivant la LIGNE SELECTIONNEE, on SET les input caché suivant l'ID et le LIBELLE du TEST
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
	
    // SUIVANT
	function RechargerPlages()
	{	
		var $id = document.getElementById("idTest").value;										
		tablePlagesHoraires.fnReloadAjax("./inscription?action=getPlageHoraire&id="+id);
	};
	
	// RAFRAICHISSEMENT du tableau des tests suivant la liste déroulante
	SelectionThemeForTest = function(){
		oTableTests.fnReloadAjax("./inscription?action=getTests&id="+$("#themes option:selected")[0].value);
	}
	
	// AFFICHAGE du dernier tableau de la page
	oTableTestsSelectionnes = $("#tabTestsSelect").dataTable({
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
	var table = $('#tabTestsSelect').DataTable(); 
    $('#tabTestsSelect tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
	    });
    // A COMPRENDRE : Suppression d'une ligne ?
    $('#deleteButton').click( function () {
        table.row('.selected').remove().draw( false );
    } );
	
    // AFFICHE la POP-UP (voir onClick du bouton "ajouterCandidatToTheme")
    afficherPlageHoraire = function(idTest){		
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
//	    	buttons : {
//	    		"Valider" : function(){
//	    			tableTestsSelect = $("#tabTestsSelect").DataTable();
//	    			
//	    			var $idTest = document.getElementById("idTest").value;
//			    	var $libelleTest = document.getElementById("libelleTest").value;
//					var $idPlage = document.getElementById("idPlage").value;
//					var $dateDebutPlage = document.getElementById("dateDebutPlage").value;
//					var $dateFinPlage = document.getElementById("dateFinPlage").value;
//					
//					// TODO : delete this, bitch !
//					console.log($idTest);
//					console.log($libelleTest);
//					console.log($idPlage);
//					console.log($dateDebutPlage);
//					console.log($dateFinPlage);
//					
//					tableTestsSelect.row.add( [
//						$idTest, 
//						$libelleTest,
//						$idPlage,
//						$dateDebutPlage,
//						$dateFinPlage
//			        ] ).draw(false);
//	    		},
//	    		"Annuler" : function(){
//	    			$("#choixPlage")[0].reset();
//	    			dialogchoixPlage.dialog("close");
//	    		}	    		
//	        },
//	        close: function() {
//	          $("#choixPlage")[0].reset();
//	        },
	        open: function() {
	        	listTestsPlageHoraire(idTest);
	        }
	    });		
		if(dialogchoixPlage.dialog( "isOpen" ))
			dialogchoixPlage.dialog( "close" );
		else
			dialogchoixPlage.dialog( "open" );		
	};
	
	
	tableTestsSelect = $("#tabTestsSelect").DataTable();	 
    $('#validerAjoutTest').on( 'click', function () {
    	var $idTest = document.getElementById("idTest").value;
    	console.log($idTest);
    	var $libelleTest = document.getElementById("libelleTest").value;
    	console.log($libelleTest);
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
    } );
	
    // AFFICHE le contenu du tableau des PLAGE HORAIRE
	var oTableTestsPlageHoraire = null;
	console.log(idTest);
	listTestsPlageHoraire = function(idTest){
		if (oTableTestsPlageHoraire == null) {
				oTableTestsPlageHoraire = $("#tabPlagesHoraires").dataTable({
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
	
	// SET les attributs du tableau à l'input correspondant
    var table = $('#tabPlagesHoraires').DataTable(); 
    $('#tabPlagesHoraires tbody').on( 'click', 'tr', function () {
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
});

/**
 * 
 */
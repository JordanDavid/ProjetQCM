
$(document).ready(function() {
	
	afficherCandidats = function(){
		$('#tableauCandidat').DataTable( {
			"bInfo":  false,
			"bLengthChange": false,
			"oLanguage": {
				"url": "../Tools/French.json"
			},
			 "searchable":false,
		});		
	};	
	
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
    			 "data" : "idTest",
    			 "bVisible" : false
    		 },
    		 {
    			 "data" : "libelle"
    		 }
         ],
		"sAjaxSource" : "./inscription?action=getTests&id="+$("#themes option:selected")[0].value,
		"fnCreatedRow" : function(nRow, aData,iDataIndex){
			$(nRow).addClass("pointer")
			$(nRow).attr("onclick","SelectionTest(this);");
			$(nRow).attr("title","Cliquer pour sélectionner un test");
		}
	});
	
	SelectionThemeForTest = function(){
		oTableTests.fnReloadAjax("./inscription?action=getTests&id="+$("#themes option:selected")[0].value);
	}
	
	SelectionTest = function(element){
		aadata = oTableTests.fnGetData($(element));
		
		//Ajout d'un attribut définissant le test sélectionné au bouton "Ajouter"
		$("#ajouterCandidatToTheme").attr("onclick", "afficherPlageHoraire("+ aadata.idTest +")");
	};
	
	
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
	    			$("#formAjoutCandidatToTheme").submit();
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
	
	listTestsPlageHoraire = function(idTest){
		oTableTestsPlageHoraire = $("#list_tests_plage_horaire").dataTable({
    		"bSort" : false,
    		"bFilter" : false,
    		"bInfo" : false,
    		"bLengthChange" : false,
    		"iDisplayLength": 5,
    		"language" : {
    			"url" : "../Tools/French.json"
    		},
    		"columns" : [
//        		 {
//        			 "data" : "idTest",
//        			 "bVisible" : false
//        		 },
//        		 {
//        			 "data" : "libelle"
//        		 },
				{
				'targets': 0,
			    'searchable': false,
			    'orderable': false,
			    'className': 'dt-body-center',
			    'render': function (data, type, full, meta){
				        return '<input type="checkbox" name="id[]" value="' + $('<div/>').text(data).html() + '">';
				        }
				},
				{
					"data" : "idPlageHoraire",
					"bVisible" : false
				},
        		 {
        			 "data" : "dateDebut"
        		 },
        		 {
        			 "data" : "dateDebut"
        		 }
             ],
    		"sAjaxSource" : "./inscription?action=getPlageHoraire&id="+idTest
    	});	
	}
	
	$(function() {
		$("#date_picker").datepicker();
	  });
	
	
	
	
	
	
	$('#list_tests tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }else {
        	oTableTests.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
        alert(oTableTests.cell('.selected', 0).data());
	    });
	
});

/**
 * 
 */

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
	
	oTableTests = $("#list_tests").dataTable({
		"bSort" : false,
		"bFilter" : false,
		"bInfo" : false,
		select : true,
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
			$(nRow).attr("onclick","SelectionQuestion(this);");
			$(nRow).attr("title","Cliquer pour afficher le détail de la question");
		}
	});
	SelectionThemeForTest = function(){
		oTableTests.fnReloadAjax("./inscription?action=getTests&id="+$("#themes option:selected")[0].value);
	}
	
//	oTableTestsPlageHoraire = $("#list_tests_plage_horaire").datatable({
//		"bSort" : false,
//		"bFilter" : false,
//		"bInfo" : false,
//		"bLengthChange" : false,
//		"iDisplayLength": 5,
//		"language" : {
//			"url" : "../Tools/French.json"
//		},
//		"columns" : [
//    		 {
//    			 "data" : "idTest",
//    			 "bVisible" : false
//    		 },
//    		 {
//    			 "data" : "libelle"
//    		 },
//    		 {
//    			 "data" : "idPlageHoraire",
//    				 "bVisible" : false
//    		 },
//    		 {
//    			 "data" : "dateDebut"
//    		 },
//    		 {
//    			 "data" : "dateFin"
//    		 }
//         ],
//		"sAjaxSource" : "./inscription?action=getTestsPlageHoraire"
//	});
	
	dialogAjoutCandidatToTheme = $("#ajoutCandidatToTheme").dialog({
		autoOpen: false,
        height: 230,
        resizable : false,
        width: 350,
        modal: true,
        position : {
        	my: "left top",
        	at: "left bottom",
        	of: $("#ajouterThemeToCandidat") 
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
          $("#formAjoutCandidatToTheme")[0].reset();
        }
    });
	
	AfficherAjoutCandidatToTheme = function(){
		
		if(dialogAjoutCandidatToTheme.dialog( "isOpen" ))
			dialogAjoutCandidatToTheme.dialog( "close" );
		else
			dialogAjoutCandidatToTheme.dialog( "open" );
	};
	
});

/**
 * 
 */






























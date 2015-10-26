/*****************************************************************/
/************************ GESTION TEST ***************************/
/*****************************************************************/
$(document).ready(function(){

	/**
	 * Gestion de l'affichage du menu
	 */
	SelectionMenu = function(li){
		//On enleve l'ancien li active
		$(".liMenu.active").removeClass("active");
		//ON place le nouveau li active
		$(li).addClass("active");	
	}

	
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
			console.log("ok");
			$(nRow).addClass("pointer")
			$(nRow).attr("onclick","SelectionTest(this);");
			$(nRow).attr("title","Cliquer pour afficher le détail de la question");
		}
	})
});
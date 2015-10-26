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
	})
	
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
		aaData = oTableTest.fnGetData($(row));
		console.log(aaData);
		//Alimentation des données statiques du test sélectionné
		$("#titre_test").html(aaData.libelle);
		$("#duree_test").html("Durée : " + aaData.duree);
		$("#seuil1_test").html("Seuil n°1 : " + aaData.seuil_maximum);
		$("#seuil2_test").html("Seuil n°2 : " + aaData.seuil_minimum);
		
		//Récupération des plages horaires du test
		$.ajax({
			action : "/gestionTest",
			method : "POST",
			data : "action=getPlagesHoraires&idTest="+aaData.id,
			processData : false,
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
				var section = "";
				
			}
		});
	}
});
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
	
	$('#tableauCandidat').DataTable( {
		"jQueryUI": true	
	});
	
	
});
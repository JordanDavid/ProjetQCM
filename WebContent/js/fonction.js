/**
 * Contient l'ensemble des fonctions javascripts utilisées
 */

/**
 * Gestion de l'affichage du menu
 */
SelectionMenu = function(li){
	//On enleve l'ancien li active
	$(".liMenu.active").removeClass("active");
	//ON place le nouveau li active
	$(li).addClass("active");	
}
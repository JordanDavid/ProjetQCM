/**
 * Fonctions générales
 */
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
	

	// FONCTION qui ouvre une POP-UP en affichant l'erreur
	afficherErreur = function(titre, message){
		dialogchoixPlage = $("#erreur_dialog").dialog({
			autoOpen: false,
	        height: 250,
	        resizable : false,
	        width: 400,
	        title : titre,
	        modal: true,
		       open : function(){
		    	   $("#message_erreur").html("<p>"+message+"</p>");
		       }
		});
		
		if(dialogchoixPlage.dialog( "isOpen" ))
			dialogchoixPlage.dialog( "close" );
		else
			dialogchoixPlage.dialog( "open" );
	}
	
});
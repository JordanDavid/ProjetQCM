$(document).ready(function(){

	/**
	 * Graphique du nombre de bonne réponse
	 */
	$('#charts_bonnes_reponses').highcharts({
        chart: {
            type: 'pie'
        },
        title: {
            text: 'Pourcentage de bonnes réponses'
        },
        series: [{
            name: 'Brands',
            colorByPoint: true,
            data: [{
                name: 'Bonne réponse',
                y: ($("#charts_bonnes_reponses")[0].dataset.bonnereponse *100) / $("#charts_bonnes_reponses")[0].dataset.totalbonnereponse
            }, {
                name: 'Mauvaise réponse',
                y: 100-(($("#charts_bonnes_reponses")[0].dataset.bonnereponse *100) / $("#charts_bonnes_reponses")[0].dataset.totalbonnereponse),
            }]
        }]
    });
});
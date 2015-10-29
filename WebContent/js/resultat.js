$(document).ready(function(){

	/**
	 * Graphique du nombre de bonne réponse
	 */
	$('#charts_bonnes_reponses').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: 'Pourcentage bonne réponse',
        exporting: { enabled: false },
        credits: { enabled: false },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: { enabled: false }
            }
        },
        series: [{
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
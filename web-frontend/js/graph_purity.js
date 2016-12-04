/**
 * Created by willstuckey on 12/3/16.
 */

var purityReportData;

/**
 *
 */
function populatePurityReportData() {
    purityReportData = [];

    var data = "reporttype=purity&action=GET&reportid=ALL";
    $.ajax({
        type: "POST",
        url: "api/report.php",
        dataType: "text",
        data: data,
        success: function(msg) {
            var response = parseResponse(msg);
            for (var key in response) {
                if (key.indexOf('vp') != (-1)) {
                    purityReportData.push(response[key]);
                }
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            showError("XHR Status: " + XMLHttpRequest.status + "\ntext: " + textStatus + "\nerror: " + errorThrown);
        }
    });
}

/**
 *
 */
function drawChart(keywords) {
    var data = new google.visualization.DataTable();
    data.addColumn('date', 'Date');
    data.addColumn('number', 'Virus PPM');
    data.addColumn('string', 'Virus Title');
    data.addColumn('string', 'Virus Text');
    data.addColumn('number', 'Contaminant PPM');
    data.addColumn('string', 'Contaminant Title');
    data.addColumn('string', 'Contaminant Text');
    var rptsData = [];
    for (var i = 0; i < purityReportData.length; i++) {
        if (keywords != null) {
            var foundKeyword = false;
            for (var j = 0; j < keywords.length; j++) {
                if (purityReportData[i]['location'].toLowerCase().indexOf(keywords[j]) !== (-1)) {
                    foundKeyword = true;
                    break;
                }
            }

            if (!foundKeyword) {
                continue;
            }
        }

        var rpt = [];
        rpt.push(new Date(Date.parse(purityReportData[i]['reportdt'].replace(' ', 'T'))));
        rpt.push(parseInt(purityReportData[i]['virusppm']));
        rpt.push(undefined); rpt.push(undefined);
        rpt.push(parseInt(purityReportData[i]['contaminantppm']));
        rpt.push(undefined); rpt.push(undefined);
        rptsData.push(rpt);
    }
    data.addRows(rptsData);

    var options = {
        title: 'Historical Report',
        curveType: 'function',
        legend: { position: 'bottom' },
        height: 520
    };

    var chart = new google.visualization.AnnotationChart(document.getElementById('chart'));
    chart.draw(data, options);
}

/**
 *
 */
function chartReady() {
    if ($('#display_button').hasClass('disabled')) {
        $('#display_button').removeClass('disabled');
    }
}

/**
 *
 */
function graphPurity_init() {
    $('#display_button').addClass('disbaled');

    populatePurityReportData();
    google.charts.load('current', {'packages':['corechart']});
    google.charts.load('current', {'packages':['annotationchart']});
    google.charts.setOnLoadCallback(chartReady);

    $('#refresh_button').click(function() {
       populatePurityReportData();
    });

    $('#display_button').click(function() {
        var keywords = $('#keywords_input').val().toLowerCase();
        if (!keywords) {
            keywords = '';
        }
        drawChart(keywords.split(' '));
    });
}
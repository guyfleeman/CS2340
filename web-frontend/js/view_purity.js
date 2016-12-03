function simpleDt(datetime) {
    var sp = datetime.split(' ');
    if (sp.length == 2) {
        return sp[0];
    } else {
        return datetime;
    }
}

/**
 *
 */
function viewPurity_init() {
    $('#report_table').empty();

    var data = "reporttype=purity&action=GET&reportid=ALL";
    $.ajax({
        type: "POST",
        url: "api/report.php",
        dataType: "text",
        data: data,
        success: function(msg) {
            var response = parseResponse(msg);;
            for (var key in response) {
                if (key.indexOf('vp') != (-1)) {
                    var reportMap = response[key];
                    var tableEntry = '';
                    if (parseInt(reportMap['virusppm']) > 10000
                        || parseInt(reportMap['contaminantppm']) > 10000) {
                        tableEntry += '<tr class="error">';
                    } else if (parseInt(reportMap['virusppm']) < 500
                        && parseInt(reportMap['contaminantppm']) < 500) {
                        tableEntry += '<tr class="success">';
                    } else {
                        tableEntry += '<tr>';
                    }
                    tableEntry += '<td>' + simpleDt(reportMap['reportdt']) + '</td>';
                    tableEntry += '<td>' + reportMap['reportid'] + '</td>';
                    tableEntry += '<td>' + reportMap['username'] + '</td>';
                    tableEntry += '<td>' + reportMap['location'] + '</td>';
                    tableEntry += '<td>' + reportMap['cond'] + '</td>';
                    tableEntry += '<td>' + reportMap['virusppm'] + '</td>';
                    tableEntry += '<td>' + reportMap['contaminantppm'] + '</td>';
                    tableEntry += '</tr>';
                    $('#report_table').append(tableEntry);
                }
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            alert("XHR Status: " + XMLHttpRequest.status + "\ntext: " + textStatus + "\nerror: " + errorThrown);
        }
    });

}
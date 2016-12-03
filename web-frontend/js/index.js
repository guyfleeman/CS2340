/**
 * Created by willstuckey on 12/3/16.
 */

/**
 *
 * @param str
 * @returns {boolean}
 */
function isFloat(str) {
    return (!(isNaN(parseFloat(str))));
}

/**
 *
 * @param str
 * @returns {boolean}
 */
function validCoordinates(str) {
    var latLongArr = str.replace(' ', '').split(',');
    if (latLongArr.length == 2) {
        if (isFloat(latLongArr[0]) && isFloat(latLongArr[1])) {
            if (parseFloat(latLongArr[0]) < -85
                || parseFloat(latLongArr[0]) > 85) {
                return false;
            }

            if (parseFloat(latLongArr[1]) < -180
                || parseFloat(latLongArr[1]) > 180) {
                return false;
            }

            return true;
        }

        return false
    }

    return false;
}

var markerData;

/**
 *
 */
function populateMarkerData() {
    markerData = [];

    var data = "reporttype=source&action=GET&reportid=ALL";
    $.ajax({
        type: "POST",
        url: "api/report.php",
        dataType: "text",
        data: data,
        success: function(msg) {
            var response = parseResponse(msg);
            for (var key in response) {
                if (key.indexOf('vp') != (-1)) {
                    var reportMap = response[key];

                    var data = {};
                    if (!validCoordinates(reportMap['location'])) {

                        // try to translate

                    }

                    if (validCoordinates(reportMap['location'])) {
                        data['lat'] = parseFloat(reportMap['location'].split(',')[0]);
                        data['long'] = parseFloat(reportMap['location'].split(',')[1]);
                        data['title'] = reportMap['name'];
                        data['type'] = reportMap['type'];
                        data['condition'] = reportMap['cond'];
                        data['description'] = reportMap['description'];
                        markerData.push(data);
                    }
                }
            }

            initMap();
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            showError("XHR Status: " + XMLHttpRequest.status + "\ntext: " + textStatus + "\nerror: " + errorThrown);
        }
    });
}

/**
 *
 */
function index_init() {
    populateMarkerData();
}

var ATL_LAT = 33.7795;
var ATL_LONG = -84.4020;
var ATL_ZOOM = 6;

/**
 *
 */
function initMap() {
    var atl = {lat: ATL_LAT, lng: ATL_LONG};
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: ATL_ZOOM,
        center: atl
    });

    for (var i = 0; i < markerData.length; i++) {
        var rpt = markerData[i];

        var markerDescription = '';
        markerDescription += '<div id="content">';
        markerDescription += '<div id="siteNotice"></div>';
        markerDescription += '<div id="bodyContent">';
        markerDescription += '<h2>' + rpt['title'] + '</h2>';
        // markerDescription += '<p>Type: ' + rpt['type'] + '</p>';
        // markerDescription += '<p>Condition: ' + rpt['condition'] + '</p>';
        markerDescription += '<p>';
        markerDescription += 'Type: ' + rpt['type'];
        markerDescription += '<br>';
        markerDescription += 'Condition: ' + rpt['condition'];
        markerDescription += '</p>';
        markerDescription += '<p>' + rpt['description'] + '</p>';
        markerDescription += '</div>';
        markerDescription += '</div>';

        var infowindow = new google.maps.InfoWindow({
            content: markerDescription
        });

        var loc = { lat: rpt['lat'], lng: rpt['long'] };
        var marker = new google.maps.Marker({
            position: loc,
            title: rpt['title'],
            map: map
        });

        marker.addListener('click', function (inner_iw, inner_m) {
            return function() {
                inner_iw.open(map, inner_m);
            }
        }(infowindow, marker));
    }

    // var marker = new google.maps.Marker({
    //     position: atl,
    //     map: map
    // });
}
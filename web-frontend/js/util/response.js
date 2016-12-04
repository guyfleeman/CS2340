var BEGIN_VP = "--- BEGIN ---";
var END_VP = "--- END ---";

/**
 * Created by willstuckey on 12/2/16.
 */
function parseResponse(text) {
    var inVp = false;
    var retMap = {};
    var kvpArr = text.match(/[^\r\n]+/g);
    for (var i = 0; i < kvpArr.length; i++) {
        if (kvpArr[i] == BEGIN_VP) {
            inVp = true;
        } else if (kvpArr[i] == END_VP) {
            inVp = false;
        } else if (inVp) {
            var idDatSpl = kvpArr[i].split(/:(.+)?/);
            if (idDatSpl.length == 3) {
                var datMap = {};
                var idNum = idDatSpl[0];
                var dat = idDatSpl[1].split('|');
                for (var j = 0; j < dat.length; j++) {
                    var vpKvpSpl = dat[j].split('=');
                    if (vpKvpSpl.length == 2) {
                        var value = vpKvpSpl[1];
                        if (value == "NULL") {
                            value = "";
                        }
                        datMap[vpKvpSpl[0]] = value;
                    }
                }
                retMap['vp' + idNum] = datMap;
            }
        } else {
            var kvpSpl = kvpArr[i].split('=');
            if (kvpSpl.length == 2) {
                retMap[kvpSpl[0]] = kvpSpl[1];
            }
        }
    }
    return retMap;
}
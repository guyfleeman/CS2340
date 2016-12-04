/**
 * Created by willstuckey on 12/3/16.
 */
function getCurrentDTFormatted() {
    return (new Date().toISOString().split('.')[0].replace('T', ' '));
}
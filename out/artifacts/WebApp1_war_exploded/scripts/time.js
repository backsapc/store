dateTime = document.getElementById('dateTime');
var myVar = setInterval( function() { myTimer() }, 1000);

function myTimer() {
    var newUrl = 'date';
    ajax.get(newUrl, {}, updateDate, true);
}

function updateDate(result) {
    dateTime.innerHTML = result;
}
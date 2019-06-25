google.maps.event.addDomListener(window, 'load', search);
google.maps.event.addDomListener(window, 'load', preference);

function search() {
    var input = document.getElementById('autocompleteLocation');
    var autocomplete = new google.maps.places.Autocomplete(input);

    autocomplete.addListener('place_changed', function () {
        var place = autocomplete.getPlace();
        console.log(place);
    });
}

function preference() {
    var input = document.getElementById('inputpreferedjobloc');
    var autocomplete = new google.maps.places.Autocomplete(input);

    autocomplete.addListener('place_changed', function () {
        var place = autocomplete.getPlace();
        console.log(place);
    });
}
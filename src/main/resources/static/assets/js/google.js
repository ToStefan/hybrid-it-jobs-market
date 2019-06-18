google.maps.event.addDomListener(window, 'load', initialize);

function initialize() {
    var input = document.getElementById('autocompleteLocation');
    var autocomplete = new google.maps.places.Autocomplete(input);

    autocomplete.addListener('place_changed', function () {
        var place = autocomplete.getPlace();
        console.log(place);
    });
}
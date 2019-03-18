function createMuseumsMap(){
    fetch('/museum-data').then(function (response) {
        return response.json();
    }).then((museumMap) => {

        const map = new google.maps.Map(document.getElementById('map'), {
            center: {lat: 40.7591704, lng: -74.0392711},
            zoom: 12
        });

    museumMap.forEach((museum) => {
        var marker = new google.maps.Marker({
            position: {lat: museum.lat, lng: museum.lng},
            title: museum.name,
            map: map
        });

        var infoWindow = new google.maps.InfoWindow({
            content: marker.title
        });

        marker.addListener('click', function() {
            infoWindow.open(map, marker);
        });
    });
    });
}
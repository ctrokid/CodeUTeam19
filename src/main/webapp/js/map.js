/** Creates a map centered at lat, lng*/
function createMap(){
    const map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 42.4498712, lng: -76.4880942},
        zoom: 14.5
    });

    addLandmark(map, 42.4472258, -76.4849071, 'McGraw Tower', '151 year-old clocktower where the daily Cornell Chimes concert takes place');
    addLandmark(map, 42.4478777, -76.4865068, 'Uris Library', 'Otherwise known as the Harry Potter library');
    addLandmark(map, 42.4476163, -76.4748309, 'Cornell Dairy Bar', 'Sells ice cream made at the College of Agriculture')
}

/** Adds a marker that shows an InfoWindow when clicked. */
function addLandmark(map, lat, lng, title, description){

    const marker = new google.maps.Marker({
        position: {lat: lat, lng: lng},
        map: map,
        title: title
    });

    var infoWindow = new google.maps.InfoWindow({
        content: description
    });
    marker.addListener('click', function() {
        infoWindow.open(map, marker);
    });
}

/** Creates a map centered at lat, lng*/
function createMap(title, description, lat, lng){
    const map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: lat, lng: lng},
        zoom: 13.5
    });

    addLandmark(map, lat, lng, title, description);
}
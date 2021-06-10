// Note: This example requires that you consent to location sharing when
// prompted by your browser. If you see the error "The Geolocation service
// failed.", it means you probably did not give permission for the browser to
// locate you.

let map, infoWindow;
var marker;

const greenMarker = 'https://maps.google.com/mapfiles/ms/icons/green-dot.png';
const yellowMarker = 'https://maps.google.com/mapfiles/ms/icons/yellow-dot.png';
const blueMarker = 'https://maps.google.com/mapfiles/ms/icons/blue-dot.png';
const redMarker = 'https://maps.google.com/mapfiles/ms/icons/red-dot.png';


function initMap(listener) {

    const warsaw = {lat: 52.237049, lng: 21.017532};
    // Stwórz nowy obiekt google maps o nazwie "map"
    map = new google.maps.Map(document.getElementById("map"), {
        center: warsaw,
        zoom: 8,                                  // Przybliżenie skala 1 - świat, 20 - budynek
    });

    var oneLocation = [];
    const markers = [];
    var i;
    for (i = 0; i < (locationsList.length); i++) {
        oneLocation = locationsList[i];
        var latL = oneLocation.lat;
        var lngL = oneLocation.lng;
        var avgTime = oneLocation.timeAvg;
        var latLng = {lat: latL, lng: lngL};
        markers.push(placeMarker(latLng, avgTime, oneLocation))
    }

    new MarkerClusterer(map, markers, {
        imagePath:
            "https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m",
    });

    //Stworz nowy obiekt okno informacyjne o nazwie "infoWindow"
    infoWindow = new google.maps.InfoWindow();
    //Stworz przycisk - HTML element o nazwie locationButton
    const locationButton = document.createElement("button");
    //Napis wyswietlany dla uzytkownika w przycisku
    locationButton.textContent = "Locate me.";
    //Dodanie HTMLowej nazwy klasy dla przycisku
    locationButton.classList.add("custom-map-control-button");
    //Lokalizacja przycisku - Gora srodek
    map.controls[google.maps.ControlPosition.TOP_CENTER].push(locationButton);
    //Zrob cos po wcisnieciu przycisku
    locationButton.addEventListener("click", () => {
        // Sprobuj geolokaliacji HTML5 - uzytkownik moze ja miec wylaczona
        if (navigator.geolocation) {
            //Wez aktualna lokalizacje
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    const pos = {
                        lat: position.coords.latitude,  // Szerokosc geograficzna
                        lng: position.coords.longitude, // Dlugosc geograficzna
                    };
                    infoWindow.setPosition(pos);        // Ustaw pozycje na pobrane wartosci
                    infoWindow.setContent("Location found."); //Wyswietli na wyszukanej lokalizacji napis w srodku
                    infoWindow.open(map); // Otworz mape
                    map.setCenter(pos);   // Ustaw pozycje na pobrana pozycje uzytkownika
                },
                () => {
                    handleLocationError(true, infoWindow, map.getCenter());
                }
            );
        } else {
            // Przegladarka nie wspiera Geolocation
            handleLocationError(false, infoWindow, map.getCenter());
        }
    });

    const newSpotButton = document.createElement("button");
    newSpotButton.textContent = "Add spot.";
    newSpotButton.classList.add("custom-map-control-button");
    map.controls[google.maps.ControlPosition.TOP_CENTER].push(newSpotButton);
    //Kliknij przycisk "add spot", aby dodac marker
    newSpotButton.addEventListener("click", () => {
        //Dodaj marker w miejscu gdzie znajdujee sie kursos na mapie.

        google.maps.event.addListener(map, 'click', function (event) {
            placeNewMarker(map, event.latLng);
        }, {once: true});
    }, {once: true})
}

function placeMarker(location, avgTime, place) {
    var marker = new google.maps.Marker({
        position: location,
        icon: changeMarkerColour(avgTime)
    });
    var postsUrl = 'place?id=' + place.id;
    var editUrl = 'place/edit/' + place.id;
    var deleteUrl = 'place/delete/' + place.id;
    var showPhotoUrl = '/place/show-photo-by-id/' + place.id;

    var markerInfo = '<h3 class="text-center mb-3">' + place.name + '</h3>' +
        '<p class="text-center">' + place.description + '</p>' +
        '<img src="' + showPhotoUrl + '" class="float-right" style="max-width: 140px" alt="Photo of the place"/>' +
        '<p>Average time: ' + place.timeAvg +
        '<br>Average rate: ' + place.rateAvg +
        '<br><button class="addPost btn-success">Add post</button>' +
        '<br><a href="' + postsUrl + '"><input type="button" value="Comments" class="btn-info"></a>' +
        '<br><a href="' + editUrl + '"><input type="button" value="Edit" class="btn-warning"></a>' +
        '<a href="' + deleteUrl + '"><input type="button" value="Delete" class="btn-danger"></a></p>';


    var infowindow = new google.maps.InfoWindow({
        content: markerInfo,
        maxWidth: 300
    });

    marker.addListener("click", () => {
        infowindow.open(map, marker);
        addPost(marker, place);
    });
    return marker;
}

function addPost(position, place) {
    var placeName = place.name;
    $('body').on('click', '.addPost', function () {
        document.getElementById("placeName").innerHTML = placeName;
        document.getElementById("placeId").value = place.id;
        var addPostForm = document.getElementById("addPostForm");
        addPostForm.className = 'show';

        const addPostWindow = new google.maps.InfoWindow({
            content: addPostForm,
            maxWidth: 300
        });

        addPostWindow.open(map, position);
    });
}

function changeMarkerColour(time) {
    if (time < 60) {
        return greenMarker;
    } else if (time < 180) {
        return yellowMarker;
    }
    return redMarker;
}

function placeNewMarker(map, location) {
    if (marker) {
        marker.setPosition(location);
    } else {
        marker = new google.maps.Marker({
            position: location,
            map: map,
            icon: blueMarker
        });
    }

    document.getElementById("latitude").value = location.lat();
    document.getElementById("longitude").value = location.lng();
    document.getElementById("postForm").className = 'show';
    var infowindow = new google.maps.InfoWindow({
        content: document.getElementById("postForm")
    });
    infowindow.open(map, marker);
}

function handleLocationError(browserHasGeolocation, infoWindow, pos) {
    infoWindow.setPosition(pos);
    infoWindow.setContent(
        browserHasGeolocation
            ? "Error: The Geolocation service failed."
            : "Error: Your browser doesn't support geolocation."
    );
    infoWindow.open(map);
}

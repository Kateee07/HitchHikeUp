// Note: This example requires that you consent to location sharing when
// prompted by your browser. If you see the error "The Geolocation service
// failed.", it means you probably did not give permission for the browser to
// locate you.

// import MarkerClusterer from '@googlemaps/markerclustererplus';
let map, infoWindow;

function initMap(listener) {

    const warsaw = {lat: 52.237049, lng: 21.017532};
    // Stworz nowy obiekt google maps o nazwie "map"
    map = new google.maps.Map(document.getElementById("map"), {
        center: warsaw,
        zoom: 8,                                  // Przybliżenie skala 1 - świat, 20 - budynek
    });

    const greenMarker = 'https://maps.google.com/mapfiles/ms/icons/green-dot.png';
    const yellowMarker = 'https://maps.google.com/mapfiles/ms/icons/yellow-dot.png';
    const blueMarker = 'https://maps.google.com/mapfiles/ms/icons/blue-dot.png';
    const redMarker = 'https://maps.google.com/mapfiles/ms/icons/red-dot.png';

     //  var locationsList = [[${places}]]; //locationList - zostało zmienną globalną zainicjalizowaną w index.html, tu już nie trzeba jej inicjalizować
    var oneLocation = [];
    //var locations = [];         To do clusterow
    // var marker;
    var i;
    for (i = 0; i < (locationsList.length); i++) {
        oneLocation = locationsList[i];
        var latL = oneLocation.lat;
        var lngL = oneLocation.lng;
        var avgTime = oneLocation.timeAvg;
        var latLng = {lat: latL, lng: lngL};
        //locations = locations + latLng + ',';     Nie wiem czy to dobrze zadziala w clusterach.
        placeMarker(map, latLng, avgTime);
        //html: document.getElementById("infoForm")

    };

    // const markerClusterer = new MarkerClusterer(map, locations, {
    //     imagePath:
    //         "https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m",
    // });
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
            console.log(event.latLng);
        }, {once: true});

    }, {once: true})

    function placeMarker(map, location, avgTime) {
        var marker = new google.maps.Marker({
            position: location,
            map: map,
            icon: changeMarkerColour(avgTime)
        });
        var infowindow = new google.maps.InfoWindow({
            content: 'Latitude: ' + location.lat +
                '<br>Longitude: ' + location.lng
        });
        marker.addListener("click", () => {
            infowindow.open(map, marker);
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
        var marker = new google.maps.Marker({
            position: location,
            map: map,
            icon: blueMarker
        });

        const contentString =
            '<div id="content">' +
            '<div id="siteNotice">' +
            "</div>" +
            '<h1 id="firstHeading" class="firstHeading">Uluru</h1>' +
            '<div id="bodyContent">' +
            fetchServer(location) +
            "</div>" +
            "</div>";

        var infowindow = new google.maps.InfoWindow({
            content: contentString
        });
        infowindow.open(map, marker);
    }

    // let base64 = require('base-64');
    function fetchServer(location) {
        let url = 'http://localhost:8080/post/coordinates';
        let username = 'user1';
        let password = 'pass1';
        let dataBody = '{"lat": ' + location.lat() + ', "lng": ' + location.lng() + '}';
        console.log(dataBody);
        let headers = new Headers();
        requirejs(["scripts/base64.js"], function (encode) {
            headers.append('Authorization', 'Basic ' + base64.encode(username + ":" + password));
        });
        headers.append('Accept', 'application/json, text/plain, */*');
        headers.append('Content-Type', 'application/json');

        let requestPost = new Request(url, {
            method: 'POST',
            body: dataBody,
            headers: headers
        })

        fetch(requestPost)
            .then(res => {
                if (res.ok) {
                    return res.text()
                } else {
                    return Promise.reject(`Http error: ${res.status}`);
                    //lub rzucając błąd
                    //throw new Error(`Http error: ${res.status}`);
                }
            })
            .then(res => {
                console.log(res)
                return res;
            })
            .catch(error => {
                console.error(error)
            });

    }

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

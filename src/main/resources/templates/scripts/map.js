// Note: This example requires that you consent to location sharing when
// prompted by your browser. If you see the error "The Geolocation service
// failed.", it means you probably did not give permission for the browser to
// locate you.
let map, infoWindow;

function initMap(listener) {
    const warsaw = {lat: 52.237049, lng: 21.017532};
    // Stworz nowy obiekt google maps o nazwie "map"
    map = new google.maps.Map(document.getElementById("map"), {
        center: warsaw,
        zoom: 8,                                  // Przybliżenie skala 1 - świat, 20 - budynek
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

    // Lokalizacja Gdanska
    const gdansk = {lat: 54.372158, lng: 18.638306};
    // Marker z pozycja na Gdansku
    const marker = new google.maps.Marker({
        position: gdansk,
        map: map,
    });

    const newSpotButton = document.createElement("button");
    newSpotButton.textContent = "Add spot.";
    newSpotButton.classList.add("custom-map-control-button");
    map.controls[google.maps.ControlPosition.TOP_CENTER].push(newSpotButton);
    //Kliknij przycisk "add spot", aby dodac marker
    newSpotButton.addEventListener("click", () => {
        //Dodaj marker w miejscu gdzie znajdujee sie kursos na mapie.
        google.maps.event.addListener(map, 'click', function (e) {
            //Znajdz lokalizacje klikniecia
            var location = e.latLng;
            //Stworz marker i wstaw go na mape
            var marker = new google.maps.Marker({
                position: location,
                map: map
            })
        })
    })
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

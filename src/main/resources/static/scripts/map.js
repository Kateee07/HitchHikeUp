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
        zoom: 8,
    });

    var locationsList = [[${places}]];
    var oneLocation = [];
    var marker;
    infoWindow = new google.maps.InfoWindow();

    var i;
    for (i = 0; locationsList.length; i++) {
        oneLocation = locationsList[i];
        var lat = oneLocation.lat;
        var lng = oneLocation.lng;
        console.log(oneLocation.lat);
        console.log(oneLocation.lng);
        marker = new google.maps.Marker({
            position: new google.maps.LatLng(lat, lng),
            map: map,
            //html: document.getElementById("infoForm")
        });
    }

    const locationButton = document.createElement("button");
    locationButton.textContent = "Locate me.";
    locationButton.classList.add("custom-map-control-button");
    map.controls[google.maps.ControlPosition.TOP_CENTER].push(locationButton);
    locationButton.addEventListener("click", () => {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    const pos = {
                        lat: position.coords.latitude,
                        lng: position.coords.longitude,
                    };
                    infoWindow.setPosition(pos);
                    infoWindow.setContent("Location found.");
                    infoWindow.open(map);
                    map.setCenter(pos);
                },
                () => {
                    handleLocationError(true, infoWindow, map.getCenter());
                }
            );
        } else {
            handleLocationError(false, infoWindow, map.getCenter());
        }
    });

    const newSpotButton = document.createElement("button");
    newSpotButton.textContent = "Add spot.";
    newSpotButton.classList.add("custom-map-control-button");
    map.controls[google.maps.ControlPosition.TOP_CENTER].push(newSpotButton);
    newSpotButton.addEventListener("click", () => {
        google.maps.event.addListener(map, 'click', function (e) {
            location = e.latLng;

            marker = new google.maps.Marker({
                position: location,
                map: map,
                //html: document.getElementById("postForm")
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

package pkp.hhu.coordinates;

public class Coordinates {
    private Float lat;
    private Float lng;

    public Float getLat() {
        return lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}

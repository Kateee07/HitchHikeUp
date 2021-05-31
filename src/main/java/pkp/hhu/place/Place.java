package pkp.hhu.place;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="places")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(max=255, message="Maksymalnie {max} znaków.")
    private String name;
    private float lat; //szerokość
    private float lng; //długość
    @Size(max=2, message="Maksymalnie {max} znaki. Kierunek określ kierunkami geograficznymi N S W E lub ich połączeniem np NW")
    private String direction;
    private double rateAvg;
    private int timeAvg;
    @Size(max=255, message="Maksymalnie {max} znaków.")
    private String description;
    private String photo;

    public Place(String name, float lat, float lng, String direction, double rateAvg, int timeAvg, String description, String photo) {
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.direction = direction;
        this.rateAvg = rateAvg;
        this.timeAvg = timeAvg;
        this.description = description;
        this.photo = photo;
    }

    public Place() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }

    public String getDirection() {
        return direction;
    }

    public double getRateAvg() {
        return rateAvg;
    }

    public int getTimeAvg() {
        return timeAvg;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setRateAvg(double rateAvg) {
        this.rateAvg = rateAvg;
    }

    public void setTimeAvg(int timeAvg) {
        this.timeAvg = timeAvg;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

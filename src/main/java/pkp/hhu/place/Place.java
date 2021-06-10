package pkp.hhu.place;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Arrays;

@Entity
@Table(name = "places")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(max = 255, message = "Maksymalnie {max} znaków.")
    private String name;
    @Column(precision = 9, scale = 6, columnDefinition = "DECIMAL(9,6)")
    //  @Digits(integer = 9 /*precision*/, fraction = 6 /*scale*/)
    private BigDecimal lat; //szerokość
    @Column(precision = 9, scale = 6, columnDefinition = "DECIMAL(9,6)")
    // @Digits(integer = 9 /*precision*/, fraction = 6 /*scale*/)
    private BigDecimal lng; //długość
    @Size(max = 2, message = "Maksymalnie {max} znaki. Kierunek określ kierunkami geograficznymi N S W E lub ich połączeniem np NW")
    private String direction;
    private float rateAvg;
    private int timeAvg;
    @Size(max = 255, message = "Maksymalnie {max} znaków.")
    private String description;
    @Lob
    private byte[] photo;

    public Place(String name, BigDecimal lat, BigDecimal lng, String direction, float rateAvg, int timeAvg, String description, byte[] photo) {
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

    public BigDecimal getLat() {
        return lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public String getDirection() {
        return direction;
    }

    public float getRateAvg() {
        return rateAvg;
    }

    public int getTimeAvg() {
        return timeAvg;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setRateAvg(float rateAvg) {
        this.rateAvg = rateAvg;
    }

    public void setTimeAvg(int timeAvg) {
        this.timeAvg = timeAvg;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", name='" + name +
                ", lat=" + lat +
                ", lng=" + lng +
                ", direction='" + direction +
                ", rateAvg=" + rateAvg +
                ", timeAvg=" + timeAvg +
                ", description='" + description +
                ", image=" + Arrays.toString(photo) +
                '}';
    }
}

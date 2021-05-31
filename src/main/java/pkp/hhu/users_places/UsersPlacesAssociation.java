package pkp.hhu.users_places;

import org.springframework.data.annotation.CreatedDate;
import pkp.hhu.place.Place;
import pkp.hhu.user.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users_places")
@IdClass(UsersPlacesAssociationId.class)
public class UsersPlacesAssociation {
    @Id
    private int personId;
    @Id
    private int placeId;

    @Column(name = "rate")
    private int rate;

    @Column(name = "time")
    private int time;

    @Column(name = "date")
    @CreatedDate()
    private LocalDate date;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "users.id")
    private User user;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "place_id", referencedColumnName = "places.id")
    private Place place;
}

package pkp.hhu.post;

import org.springframework.data.annotation.CreatedDate;
import pkp.hhu.place.Place;
import pkp.hhu.user.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Place place;
    private int rate;
    private int time;
    private String comment;
    @CreatedDate()
    private LocalDate date;

}

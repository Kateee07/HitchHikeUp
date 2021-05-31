package pkp.hhu.post;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int personId;
    private int placeId;
    private int rate;
    private int time;
    private String comment;
    @CreatedDate()
    private LocalDate date;

}

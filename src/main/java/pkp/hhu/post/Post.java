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

    public Post(User user, Place place, int rate, int time, String comment, LocalDate date) {
        this.user = user;
        this.place = place;
        this.rate = rate;
        this.time = time;
        this.comment = comment;
        this.date = date;
    }

    public Post() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user=" + user +
                ", place=" + place +
                ", rate=" + rate +
                ", time=" + time +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                '}';
    }
}

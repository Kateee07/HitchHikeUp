package pkp.hhu.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pkp.hhu.users_places.UsersPlacesAssociation;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    @Size(min=4, message="Nazwa użytkownika musi mieć conajmniej {min} znaki")
    private String username;
    @Size(min=6, message="Hasło musi mieć conajmniej {min} znaki")
    private String password;
    private String role;
    @OneToMany(mappedBy="users")
    private List<UsersPlacesAssociation> places;

    public User(String username, String password, String role, List<UsersPlacesAssociation> places) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.places = places;
    }

    public User() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPlaces(List<UsersPlacesAssociation> places) {
        this.places = places;
    }

    public Integer getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public List<UsersPlacesAssociation> getPlaces() {
        return places;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}

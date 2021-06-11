package pkp.hhu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pkp.hhu.place.Place;
import pkp.hhu.place.PlaceRepository;
import pkp.hhu.user.User;
import pkp.hhu.user.UserRepository;

import java.math.BigDecimal;

@Component
public class DbInit implements InitializingBean {
    private final static Logger LOGGER = LoggerFactory.getLogger(DbInit.class);

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private PlaceRepository placeRepository;

    public DbInit(UserRepository userRepository, PasswordEncoder passwordEncoder, PlaceRepository placeRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.placeRepository = placeRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("Kod wykonywany po inicjalizacji beana");
      
        User admin1 = new User("admin1", passwordEncoder.encode("pass1"), "ADMIN");
        User user1 = new User("user1", passwordEncoder.encode("pass11"), "USER");
      
        userRepository.save(admin1);
        userRepository.save(user1);

        Place place1 = new Place("Rondo Dmowskiego", BigDecimal.valueOf(52.229878), BigDecimal.valueOf(21.010653), "W", 3, 70, "Koło patelni", null);
        Place place2 = new Place("Mak przed Łomiankami", BigDecimal.valueOf(52.308780), BigDecimal.valueOf(20.9274978), "N", 6, 30, "Droga na Gdańsk koło McDonalds przed Łomiankami", null);

        placeRepository.save(place1);
        placeRepository.save(place2);
    }

}

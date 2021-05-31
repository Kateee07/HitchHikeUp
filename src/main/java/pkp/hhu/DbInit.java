package pkp.hhu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pkp.hhu.user.User;
import pkp.hhu.user.UserRepository;

@Component
public class DbInit implements InitializingBean {
    private final static Logger LOGGER = LoggerFactory.getLogger(DbInit.class);

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public DbInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("Kod wykonywany po inicjalizacji beana");
        User admin1 = new User("admin1", passwordEncoder.encode("pass1"), "ADMIN");
        userRepository.save(admin1);
        User user1 = new User("user1", passwordEncoder.encode("pass1"), "USER");
        userRepository.save(user1);
    }
}

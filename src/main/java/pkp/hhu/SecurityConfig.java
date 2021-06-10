package pkp.hhu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import pkp.hhu.user.UserService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserService userService;
    private PasswordEncoderConfig passwordEncoderConfig;

    public SecurityConfig(UserService userService, PasswordEncoderConfig passwordEncoderConfig) {
        this.userService = userService;
        this.passwordEncoderConfig = passwordEncoderConfig;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password(passwordEncoderConfig.passwordEncoder().encode("password")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoderConfig.passwordEncoder().encode("admin")).roles("ADMIN");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic()
                .and()
                .formLogin()
                //             .loginPage("/")
//                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                //            .failureUrl("/")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .authorizeRequests()
                .antMatchers("/after-login")
                .authenticated()
                .antMatchers("/post")
                .authenticated()
                .antMatchers("/post/coordinates")
                .authenticated()
                .antMatchers("/**")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }
}

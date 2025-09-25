package pl.marcinkielbik.car_rental_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pl.marcinkielbik.car_rental_backend.model.User; // Twoja encja User
import pl.marcinkielbik.car_rental_backend.repository.UserRepository;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form.permitAll())
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // jawnie używamy Twojego encjowego User (by uniknąć konfliktów importów)
            pl.marcinkielbik.car_rental_backend.model.User user =
                    userRepository.findByEmail(username)
                            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // Tworzymy listę uprawnień (ROLE_...)
            List<GrantedAuthority> authorities =
                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

            // Zwracamy obiekt UserDetails od Spring Security (unikamy buildera -> zero konfliktów)
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    authorities
            );
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}

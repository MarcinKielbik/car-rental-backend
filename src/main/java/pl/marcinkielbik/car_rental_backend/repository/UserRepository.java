package pl.marcinkielbik.car_rental_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcinkielbik.car_rental_backend.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

}

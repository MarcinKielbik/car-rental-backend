package pl.marcinkielbik.car_rental_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.marcinkielbik.car_rental_backend.model.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
}

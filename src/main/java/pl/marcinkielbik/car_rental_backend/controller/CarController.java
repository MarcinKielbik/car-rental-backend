package pl.marcinkielbik.car_rental_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.marcinkielbik.car_rental_backend.model.Car;
import pl.marcinkielbik.car_rental_backend.model.CarStatus;
import pl.marcinkielbik.car_rental_backend.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    // Pobranie wszystkich samochodów
    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    // Pobranie konkretnego samochodu po id
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return carService.getCarById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // Dodanie nowego samochodu (admin)
    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        return ResponseEntity.ok(carService.createCar(car));
    }

    // Aktualizacja samochodu
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car car) {
        car.setId(id);
        return ResponseEntity.ok(carService.updateCar(car));
    }

    // Usunięcie samochodu
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }


    // Zmiana statusu samochodu (dostępny/wypożyczony)
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> setCarStatus(@PathVariable Long id, @RequestParam CarStatus status) {
        carService.setCarStatus(id, status);
        return ResponseEntity.ok().build();
    }
}

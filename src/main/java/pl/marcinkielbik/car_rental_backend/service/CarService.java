package pl.marcinkielbik.car_rental_backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.marcinkielbik.car_rental_backend.model.Car;
import pl.marcinkielbik.car_rental_backend.model.CarStatus;
import pl.marcinkielbik.car_rental_backend.repository.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;


    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    public Car createCar(Car car) {
        car.setStatus(CarStatus.AVAILABLE); // automatycznie ustawiamy dostępność
        return carRepository.save(car);
    }

    public Car updateCar(Car car) {
        return carRepository.save(car);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public void setCarStatus(Long carId, CarStatus status) {
        Optional<Car> carOpt = carRepository.findById(carId);
        carOpt.ifPresent(car -> {
            car.setStatus(status);
            carRepository.save(car);
        });
    }
}

package pl.marcinkielbik.car_rental_backend.model;

import jakarta.persistence.*;

public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private Double pricePerDay;

    @Enumerated(EnumType.STRING)
    private CarStatus status;

    public enum CarStatus {
        AVAILABLE,
        RENTED
    }

}

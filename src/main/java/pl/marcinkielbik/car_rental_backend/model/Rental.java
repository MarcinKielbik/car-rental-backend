package pl.marcinkielbik.car_rental_backend.model;

import jakarta.persistence.*;

import java.time.LocalDate;

public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private RentalStatus status;


    public enum RentalStatus {
        ACTIVE,
        RETURNED
    }
}

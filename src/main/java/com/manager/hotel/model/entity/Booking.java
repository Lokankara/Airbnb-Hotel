package com.manager.hotel.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long finalBill;
    private boolean earlyDeparture;
    private LocalDateTime arrival;
    private LocalDateTime departure;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    @OneToOne
    private Room room;
    @OneToOne
    private Guest guest;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return id.equals(booking.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

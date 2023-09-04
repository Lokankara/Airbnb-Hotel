package com.manager.hotel.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedEntityGraph(name = "Booking.guest",
        attributeNodes = @NamedAttributeNode("guest"))
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long finalBill;
    @CreationTimestamp
    private Timestamp arrival;
    @CreationTimestamp
    private Timestamp checkInDate;
    @CreationTimestamp
    private Timestamp checkOutDate;
    private boolean earlyDeparture;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    private Guest guest;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Booking booking = (Booking) o;
        return id.equals(booking.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

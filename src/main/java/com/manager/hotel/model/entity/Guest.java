package com.manager.hotel.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "guestWithRoom", attributeNodes = @NamedAttributeNode("room"))
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "passport_data")
    private String passportData;
    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;
    @Column(name = "departure_date")
    private LocalDateTime departureDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;
    @OneToOne
    private Passport passport;

    public int getRate() {
        return room.getRoomType().getRate();
    }
}

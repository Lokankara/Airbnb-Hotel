package com.manager.hotel.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.manager.hotel.model.enums.GuestStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NamedEntityGraph(
        name = "guest-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("room"),
                @NamedAttributeNode("passport")
        }
)
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_id")
    private Long id;
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "passport_data", length = 128, nullable = false)
    private String passportData;
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "arrival_date", length = 128, nullable = false)
    private LocalDateTime arrivalDate;
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "departure_date", length = 128, nullable = false)
    private LocalDateTime departureDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    @JsonBackReference
    private Room room;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passport_id")
    private Passport passport;
    @Enumerated(EnumType.STRING)
    @Column(name = "guest_status", length = 64, nullable = false)
    private GuestStatus guestStatus;

    public int getRate() {
        return room.getRoomType().getRate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return id.equals(guest.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

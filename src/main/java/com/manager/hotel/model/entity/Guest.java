package com.manager.hotel.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.manager.hotel.model.dto.RoomDto;
import com.manager.hotel.model.enums.Gender;
import com.manager.hotel.model.enums.GuestStatus;
import com.manager.hotel.model.enums.RoomStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
//@NamedEntityGraph(name = "guest-entity-graph",
//        attributeNodes = {
//                @NamedAttributeNode(value = "room",
//                        subgraph = "rooms-subgraph")
//        },
//        subgraphs = {
//                @NamedSubgraph(
//                        name = "rooms-subgraph",
//                        attributeNodes = {
//                                @NamedAttributeNode("passport")
//                        })
//        }
//)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_id")
    private Long id;
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "passport_data", length = 128)
    private String passportData;
    @NotNull
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "arrival_date", length = 128)
    private Timestamp arrivalDate;
    @NotNull
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "check_in", length = 128)
    private Timestamp checkIn;
    @NotNull
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "departure_date", length = 128)
    private Timestamp departureDate;
    @ToString.Exclude
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "guest", fetch = FetchType.EAGER)
    private Set<Room> rooms;
    @JoinColumn(name = "passport_id")
    @OneToOne(fetch = FetchType.EAGER)
    private Passport passport;
    @ToString.Exclude
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "guest", fetch = FetchType.EAGER)
    private Set<Booking> bookings;
    @Enumerated(EnumType.STRING)
    @Column(name = "guest_status", length = 64, nullable = false)
    private GuestStatus guestStatus;
    @Column(name = "feedback", length = 1024)
    private String feedback;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 64)
    private Gender gender;

    public void addRoom(Room room) {
        rooms.add(room);
        room.setGuest(this);
    }
    public void removeRoom(Room room) {
        rooms.remove(room);
        room.setGuest(null);
    }

    public int getRate() {
        return rooms.stream()
                    .filter(room -> room.getRoomStatus() == RoomStatus.OCCUPIED)
                    .findFirst()
                    .map(room -> room.getRoomType().getRate())
                    .orElse(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Guest guest = (Guest) o;
        return id.equals(guest.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

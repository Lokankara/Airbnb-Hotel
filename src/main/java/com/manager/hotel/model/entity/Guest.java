package com.manager.hotel.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.manager.hotel.model.enums.Gender;
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
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "guest-entity-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "rooms"),
                @NamedAttributeNode(value = "passport")
        }
)
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "departure", length = 128)
    private Timestamp departure;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "check_in", length = 128)
    private Timestamp checkIn;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "check_out", length = 128)
    private Timestamp checkOut;
    @ToString.Exclude
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "guest", fetch = FetchType.EAGER)
    private Set<Room> rooms;
    @ToString.Exclude
    @JsonBackReference
    @JoinColumn(name = "passport_id")
    @OneToOne(fetch = FetchType.EAGER)
    private Passport passport;
    @Builder.Default
    @ToString.Exclude
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "guest", fetch = FetchType.EAGER)
    private Set<Booking> bookings = new HashSet<>();
    @Enumerated(EnumType.STRING)
    @Column(name = "guest_status", length = 64, nullable = false)
    private GuestStatus guestStatus;
    @Column(name = "feedback", length = 1024)
    private String feedback;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 64)
    private Gender gender;

    public void addRoom(Room room) {
        if (rooms == null) {
            rooms = new HashSet<>();
        }
        rooms.add(room);
        room.setGuest(this);
    }

    public void removeRoom(Room room) {
        rooms.remove(room);
        room.setGuest(null);
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

    public void addBooking(Booking booking) {
        if (bookings == null) {
            bookings = new HashSet<>();
        }
        bookings.add(booking);
    }
}

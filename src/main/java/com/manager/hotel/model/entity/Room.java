package com.manager.hotel.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.manager.hotel.model.enums.RoomStatus;
import com.manager.hotel.model.enums.RoomType;
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
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "room-entity-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "guest", subgraph = "guest-subgraph")
        },
        subgraphs = {
                @NamedSubgraph(name = "guest-subgraph", attributeNodes = {
                        @NamedAttributeNode("passport"),
                        @NamedAttributeNode("bookings")
                })
        }
)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;
    private int capacity;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", length = 128, nullable = false)
    private RoomType roomType;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "room_status", length = 128, nullable = false)
    private RoomStatus roomStatus;
    @Column(name = "path")
    private String path;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guest_id")
    private Guest guest;
    @ToString.Exclude
    @OneToOne
    @JsonBackReference
    private Booking booking;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Room room = (Room) o;
        return id.equals(room.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

package com.manager.hotel.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.manager.hotel.model.enums.RoomStatus;
import com.manager.hotel.model.enums.RoomType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "room-entity-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "guests", subgraph = "guests-subgraph")
        },
        subgraphs = {
                @NamedSubgraph(name = "guests-subgraph", attributeNodes = {
                        @NamedAttributeNode("passport")
                })
        }
)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;
    private int capacity;
    @NotNull
    @Size(min = 1, max = 128)
    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", length = 128, nullable = false)
    private RoomType roomType;
    @NotNull
    @Size(min = 1, max = 128)
    @Enumerated(EnumType.STRING)
    @Column(name = "room_status", length = 128, nullable = false)
    private RoomStatus roomStatus;
    @ToString.Exclude
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
    private Set<Guest> guests;
    private String path;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id.equals(room.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

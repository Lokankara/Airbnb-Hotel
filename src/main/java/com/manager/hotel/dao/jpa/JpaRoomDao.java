package com.manager.hotel.dao.jpa;

import com.manager.hotel.dao.RoomDao;
import com.manager.hotel.exception.RoomNotFoundException;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Room;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.Subgraph;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.manager.hotel.dao.jpa.Constant.FETCH_GRAPH;

@Repository
@RequiredArgsConstructor
public class JpaRoomDao extends RoomDao {

    @PersistenceUnit
    private final EntityManagerFactory factory;

    @Override
    public List<Room> findByRoomTypeAndCapacity(
            Criteria criteria) {
        try (EntityManager entityManager =
                     factory.createEntityManager()) {
            CriteriaBuilder builder =
                    entityManager.getCriteriaBuilder();
            CriteriaQuery<Room> query =
                    builder.createQuery(Room.class);
            Root<Room> root = query.from(Room.class);
            query.select(root).where(builder
                    .and(builder.equal(root.get("roomType"),
                                    criteria.getRoom().getRoomType()),
                            builder.greaterThanOrEqualTo(
                                    root.get("capacity"),
                                    criteria.getRoom().getCapacity())));
            return entityManager.createQuery(query).getResultList();
        }
    }

    public Room getById(final Long id) {
        return findById(id).orElseThrow(() ->
                new RoomNotFoundException(
                        "Room not found with ID " + id));
    }

    @Override
    public List<Room> findAll() {
        try (EntityManager entityManager =
                     factory.createEntityManager()) {
            EntityGraph<Room> entityGraph =
                    entityManager.createEntityGraph(Room.class);
            Subgraph<Guest> guestSubgraph =
                    entityGraph.addSubgraph("guest");
            guestSubgraph.addSubgraph("passport");
            guestSubgraph.addSubgraph("bookings");
            CriteriaQuery<Room> query = entityManager
                    .getCriteriaBuilder()
                    .createQuery(Room.class);
            query.select(query.from(Room.class));
            return entityManager.createQuery(query)
                                .setHint(FETCH_GRAPH, entityGraph)
                                .getResultList();
        }
    }
}

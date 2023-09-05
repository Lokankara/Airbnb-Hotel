package com.manager.hotel.dao.jpa;

import com.manager.hotel.dao.RoomDao;
import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Room;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.Subgraph;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.manager.hotel.dao.jpa.Constant.FETCH_GRAPH;

@Repository
@RequiredArgsConstructor
public class JpaRoomDao extends RoomDao {

    @PersistenceUnit
    private final EntityManagerFactory factory;

    public List<Room> findByCriteria(Criteria criteria) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Room> query = builder.createQuery(Room.class);
            Root<Room> root = query.from(Room.class);
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getId() != null) {
                predicates.add(builder.equal(root.get("id"), criteria.getId()));
            }
            if (criteria.getCapacity() != null) {
                predicates.add(builder.equal(root.get("capacity"), criteria.getCapacity()));
            }
            if (criteria.getRoomType() != null) {
                predicates.add(builder.equal(root.get("roomType"), criteria.getRoomType()));
            }
            if (criteria.getRoomStatus() != null) {
                predicates.add(builder.equal(root.get("roomStatus"), criteria.getRoomStatus()));
            }
            if (!predicates.isEmpty()) {
                query.where(predicates.toArray(new Predicate[0]));
            }
            return entityManager.createQuery(query).getResultList();
        }
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

    public Optional<Room> update(Room room) {
        try (EntityManager entityManager =
                     factory.createEntityManager()) {
            EntityTransaction transaction =
                    entityManager.getTransaction();
            try {
                transaction.begin();
                Room updated = entityManager.merge(room);
                transaction.commit();
                return Optional.of(updated);
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                return Optional.empty();
            }
        }
    }
}

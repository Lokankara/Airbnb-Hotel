package com.manager.hotel.dao;

import com.manager.hotel.model.entity.Room;
import com.manager.hotel.model.entity.RoomType;
import com.manager.hotel.exception.RoomNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoomDao {

    @PersistenceUnit
    private final EntityManagerFactory factory;

    public List<Room> findAll() {
        try (EntityManager entityManager = factory.createEntityManager()) {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Room> query = builder.createQuery(Room.class);
            query.select(query.from(Room.class));
            return entityManager.createQuery(query).getResultList();
        }
    }

    public Room getById(Long id) {
        return findById(id).orElseThrow(() ->
                new RoomNotFoundException(
                        "Room not found with ID " + id));
    }

    public List<Room> findByRoomTypeAndCapacity(
            RoomType roomType,
            int capacity) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Room> query = criteriaBuilder.createQuery(Room.class);
            Root<Room> root = query.from(Room.class);
            query.select(root).where(criteriaBuilder
                    .and(criteriaBuilder.equal(
                                    root.get("roomType"), roomType),
                            criteriaBuilder.greaterThanOrEqualTo(
                                    root.get("capacity"),
                                    capacity)));
            return entityManager.createQuery(query).getResultList();
        }
    }

    public Optional<Room> findById(Long id) {
        try (EntityManager entityManager =
                     factory.createEntityManager()) {
            return Optional.ofNullable(
                    entityManager.find(Room.class, id));
        }
    }
}

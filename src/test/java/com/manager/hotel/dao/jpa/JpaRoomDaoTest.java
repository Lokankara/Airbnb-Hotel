package com.manager.hotel.dao.jpa;

import com.manager.hotel.model.entity.Criteria;
import com.manager.hotel.model.entity.Room;
import com.manager.hotel.model.enums.RoomStatus;
import com.manager.hotel.model.enums.RoomType;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Subgraph;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.manager.hotel.dao.jpa.Constant.FETCH_GRAPH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JpaRoomDaoTest {

    private JpaRoomDao roomDao;
    @Mock
    private EntityManagerFactory factory;
    @Mock
    private EntityManager entityManager;
    @Mock
    Predicate idPredicate;
    @Mock
    Predicate capacityPredicate;
    @Mock
    Predicate roomTypePredicate;
    @Mock
    Predicate roomStatusPredicate;
    @Mock
    CriteriaBuilder builder;
    @Mock
    CriteriaQuery<Room> query;
    @Mock
    TypedQuery<Room> typedQuery;
    @Mock
    Root<Room> root;
    @Mock
    EntityGraph<Room> entityGraph;

    @Mock
    Subgraph<Object> guestSubgraph;

    Room room = new Room();
    Room expectedRoom = new Room();

    List<Room> expectedRooms = Arrays.asList(new Room(), new Room());

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        roomDao = new JpaRoomDao(factory);
    }

    @Test
    @DisplayName("Given search criteria, When finding Rooms by criteria, Then a list of matching Rooms should be returned")
    void testFindByCriteria() {
        Criteria criteria = new Criteria();
        criteria.setId(1L);
        criteria.setCapacity(2);
        criteria.setRoomType(RoomType.STANDARD);
        criteria.setRoomStatus(RoomStatus.VACANT);
        when(factory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getCriteriaBuilder()).thenReturn(builder);
        when(builder.createQuery(Room.class)).thenReturn(query);
        when(query.from(Room.class)).thenReturn(root);
        when(builder.equal(root.get("id"), criteria.getId())).thenReturn(idPredicate);
        when(builder.equal(root.get("capacity"), criteria.getCapacity())).thenReturn(capacityPredicate);
        when(builder.equal(root.get("roomType"), criteria.getRoomType())).thenReturn(roomTypePredicate);
        when(builder.equal(root.get("roomStatus"), criteria.getRoomStatus())).thenReturn(roomStatusPredicate);
        when(query.where(idPredicate, capacityPredicate, roomTypePredicate, roomStatusPredicate)).thenReturn(query);
        when(entityManager.createQuery(query)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(expectedRooms);
        List<Room> actualRooms = roomDao.findByCriteria(criteria);

        assertEquals(expectedRooms, actualRooms);
        verify(factory).createEntityManager();
        verify(entityManager).getCriteriaBuilder();
        verify(builder).createQuery(Room.class);
        verify(query).from(Room.class);
        verify(builder).equal(root.get("id"), criteria.getId());
        verify(builder).equal(root.get("capacity"), criteria.getCapacity());
        verify(builder).equal(root.get("roomType"), criteria.getRoomType());
        verify(builder).equal(root.get("roomStatus"), criteria.getRoomStatus());
    }

    @Test
    @DisplayName("When finding all Rooms, Then a list of all Rooms should be returned")
    void testFindAll() {
        when(factory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.createEntityGraph(Room.class)).thenReturn(entityGraph);
        when(entityGraph.addSubgraph("guest")).thenReturn(guestSubgraph);
        when(guestSubgraph.addSubgraph("passport")).thenReturn(mock(Subgraph.class));
        when(guestSubgraph.addSubgraph("bookings")).thenReturn(mock(Subgraph.class));
        when(entityManager.getCriteriaBuilder()).thenReturn(builder);
        when(builder.createQuery(Room.class)).thenReturn(query);
        when(query.from(Room.class)).thenReturn(root);
        when(query.select(root)).thenReturn(query);
        when(entityManager.createQuery(query)).thenReturn(typedQuery);
        when(typedQuery.setHint(FETCH_GRAPH, entityGraph)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(expectedRooms);
        List<Room> actualRooms = roomDao.findAll();

        assertEquals(expectedRooms, actualRooms);
        verify(factory).createEntityManager();
        verify(entityManager).createEntityGraph(Room.class);
        verify(entityGraph).addSubgraph("guest");
        verify(guestSubgraph).addSubgraph("passport");
        verify(guestSubgraph).addSubgraph("bookings");
        verify(entityManager).getCriteriaBuilder();
        verify(builder).createQuery(Room.class);
        verify(query).from(Room.class);
        verify(query).select(root);
        verify(entityManager).createQuery(query);
    }

    @Test
    @DisplayName("Given a Room to update, When updating the Room, Then the Room should be updated successfully")
    void testUpdate() {
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(factory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(entityManager.merge(room)).thenReturn(expectedRoom);
        JpaRoomDao roomDao = new JpaRoomDao(factory);
        Optional<Room> actualRoom = roomDao.update(room);

        assertTrue(actualRoom.isPresent());
        assertEquals(expectedRoom, actualRoom.get());
        verify(factory).createEntityManager();
        verify(entityManager).getTransaction();
        verify(transaction).begin();
        verify(entityManager).merge(room);
        verify(transaction).commit();
    }

    @Test
    @DisplayName("Given a Room update that fails, When updating the Room, Then the update should be rolled back")
    void testUpdateRollback() {
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(factory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(transaction);
        when(entityManager.merge(room)).thenThrow(new PersistenceException("Error updating Room"));
        when(transaction.isActive()).thenReturn(true);
        Optional<Room> actualRoom = roomDao.update(room);

        assertFalse(actualRoom.isPresent());
        verify(factory).createEntityManager();
        verify(entityManager).getTransaction();
        verify(transaction).begin();
        verify(entityManager).merge(room);
        verify(transaction, never()).commit();
        verify(transaction).rollback();
    }
}

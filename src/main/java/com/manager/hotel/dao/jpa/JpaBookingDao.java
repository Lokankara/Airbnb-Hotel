package com.manager.hotel.dao.jpa;

import com.manager.hotel.dao.BookingDao;
import com.manager.hotel.model.dto.PostBookingDto;
import com.manager.hotel.model.entity.Booking;
import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Passport;
import com.manager.hotel.model.entity.Room;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

import static com.manager.hotel.dao.jpa.Constant.SELECT_LATEST;
import static java.sql.Timestamp.valueOf;
import static java.time.LocalDate.parse;

@Repository
@RequiredArgsConstructor
public class JpaBookingDao extends BookingDao {

    @PersistenceUnit
    private final EntityManagerFactory factory;

    public List<Booking> findLatestDeals(
            final Timestamp fromDate) {
        try (EntityManager entityManager =
                     factory.createEntityManager()) {
            return entityManager
                    .createQuery(SELECT_LATEST, Booking.class)
                    .setParameter("fromDate", fromDate)
                    .getResultList();
        }
    }

    @Override
    public List<Booking> findAll() {
        try (EntityManager entityManager = factory.createEntityManager()) {
            TypedQuery<Long> idQuery = entityManager.createQuery("SELECT b.guest.id FROM Booking b", Long.class);
            List<Long> guestIds = idQuery.getResultList();

            EntityGraph<Guest> guestGraph = entityManager.createEntityGraph(Guest.class);
            guestGraph.addAttributeNodes("rooms", "passport");

            TypedQuery<Guest> guestQuery = entityManager.createQuery("SELECT g FROM Guest g WHERE g.id IN :ids", Guest.class);
            guestQuery.setParameter("ids", guestIds);
            guestQuery.setHint("javax.persistence.fetchgraph", guestGraph);

            List<Guest> guests = guestQuery.getResultList();

            TypedQuery<Booking> bookingQuery = entityManager.createQuery("SELECT b FROM Booking b WHERE b.guest.id IN :ids", Booking.class);
            bookingQuery.setParameter("ids", guestIds);

            List<Booking> bookings = bookingQuery.getResultList();
            for (Booking booking : bookings) {
                Guest guest = guests.stream()
                                    .filter(g -> g.getId().equals(booking.getGuest().getId()))
                                    .findFirst()
                                    .orElse(null);
                booking.setGuest(guest);
            }
            return bookings;
        }
    }

    public void save(PostBookingDto dto) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            Passport passport = new Passport();
            passport.setAddress(dto.getAddress());
            passport.setCreditCard(dto.getCreditCard());
            passport.setEmail(dto.getEmail());
            passport.setFirstName(dto.getFirstname());
            passport.setGender(dto.getGender());
            passport.setLastName(dto.getLastname());
            passport.setPhone(dto.getPhone());

            Guest guest = new Guest();
            guest.setCheckIn(valueOf(parse(
                    dto.getCheckin())
                    .atStartOfDay()));
            guest.setDepartureDate(valueOf(parse(
                    dto.getCheckout())
                    .atStartOfDay()));
            guest.setGender(dto.getGender());
            guest.setGuestStatus(dto.getGuestStatus());
            guest.setPassport(passport);

            Room room = new Room();
            room.setCapacity(dto.getCapacity());
            room.setRoomType(dto.getRoomType());

            TypedQuery<Guest> query = entityManager.createQuery("SELECT g FROM Guest g WHERE g.passport.firstName = :firstName AND g.passport.lastName = :lastName", Guest.class);
            query.setParameter("firstName", dto.getFirstname());
            query.setParameter("lastName", dto.getLastname());
            List<Guest> existingGuests = query.getResultList();

            if (existingGuests.isEmpty()) {
                entityManager.persist(passport);
                entityManager.persist(guest);
                entityManager.persist(room);
                room.setGuest(guest);
                guest.addRoom(room);
            } else {
                Guest existingGuest = existingGuests.get(0);
                entityManager.persist(room);
                room.setGuest(existingGuest);
                existingGuest.getRooms().add(room);
            }
        }
    }
}

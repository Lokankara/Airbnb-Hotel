package com.manager.hotel.dao.jpa;

public final class Constant {
    private Constant() {
    }

    public static final String FETCH_GRAPH = "jakarta.persistence.fetchgraph";
    public static final String SELECT_ALL_GUESTS = "SELECT g FROM Guest g JOIN FETCH g.room";
    public static final String SELECT_ALL_BOOKING = "SELECT b FROM Booking b JOIN FETCH b.guest";
    public static final String SELECT_ALL_ROOMS = "SELECT r FROM Room r";
    public static final String SELECT_BY_FULL_NAME = "SELECT g FROM Guest g WHERE g.passportData  = :full";
    public static final String SELECT_ALL_ORDERS = "SELECT b.guest.id FROM Booking b";
    public static final String SELECT_ORDERS_BY_IDS = "SELECT b FROM Booking b WHERE b.guest.id IN :ids";
    public static final String SELECT_BOOKING_BY_ROOM_ID = "SELECT b FROM Booking b JOIN b.guest g JOIN g.rooms r WHERE r.id = :roomId";
    public static final String SELECT_BY_IDS = "SELECT g FROM Guest g WHERE g.id IN :ids";
    public static final String SELECT_GUESTS_BY_CRITERIA = "SELECT g FROM Guest g WHERE ";
    public static final String SELECT_PASSPORT = "SELECT p FROM Passport p " +
            "WHERE p.firstname = :firstname AND p.lastname = :lastname";
}

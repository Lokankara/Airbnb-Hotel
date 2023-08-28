package com.manager.hotel.dao.jpa;

public final class Constant {
    private Constant() {
    }

    public static final String FETCH_GRAPH = "jakarta.persistence.fetchgraph";
    public static final String SELECT_ALL_GUESTS = "SELECT g FROM Guest g JOIN FETCH g.room";
    public static final String SELECT_ALL_ROOMS = "SELECT r FROM Room r";
    public static final String SELECT_LATEST = "SELECT b FROM Booking b WHERE b.arrival >= :fromDate OR b.departure >= :fromDate OR b.checkInDate >= :fromDate OR b.checkOutDate >= :fromDate";
    public static final String SELECT_GUESTS_BY_CRITERIA = "SELECT g FROM Guest g WHERE ";
    public static final String SELECT_PASSPORT = "SELECT p FROM Passport p WHERE p.firstName = :firstName AND p.lastName = :lastName";
}

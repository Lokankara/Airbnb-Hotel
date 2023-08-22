package com.manager.hotel.dao.jpa;

public class Constant {
    private Constant() {
    }

    public static final String FETCH_GRAPH = "jakarta.persistence.fetchgraph";
    public static final String SELECT_ALL_GUESTS = "SELECT g FROM Guest g JOIN FETCH g.room";
    public static final String SELECT_ALL_ROOMS = "SELECT r FROM Room r JOIN FETCH r.guests g";
    public static final String SELECT_GUESTS_BY_CRITERIA = "SELECT g FROM Guest g WHERE ";
}

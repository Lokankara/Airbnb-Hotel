package com.manager.hotel.dao;

import com.manager.hotel.model.entity.Guest;
import com.manager.hotel.model.entity.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestDao extends JpaRepository<Guest, Long> {

    Optional<Guest> findByPassport(Passport passport);
}

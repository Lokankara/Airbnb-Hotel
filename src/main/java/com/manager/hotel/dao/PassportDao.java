package com.manager.hotel.dao;

import com.manager.hotel.model.entity.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassportDao extends JpaRepository<Passport, Long> {

    Optional<Passport> findByFirstnameAndLastname(String name, String surname);
}

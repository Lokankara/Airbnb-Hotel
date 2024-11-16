package com.manager.hotel.dao;

import com.manager.hotel.model.entity.ListingPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingPictureRepository extends JpaRepository<ListingPicture, Long> {
}

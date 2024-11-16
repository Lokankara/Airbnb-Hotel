package com.manager.hotel.service;

import com.manager.hotel.dao.ListingPictureRepository;
import com.manager.hotel.mapper.ListingPictureMapper;
import com.manager.hotel.model.dto.PictureDTO;
import com.manager.hotel.model.entity.Listing;
import com.manager.hotel.model.entity.ListingPicture;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PictureService {

    private final ListingPictureRepository listingPictureRepository;

    private final ListingPictureMapper listingPictureMapper;

    public PictureService(ListingPictureRepository listingPictureRepository, ListingPictureMapper listingPictureMapper) {
        this.listingPictureRepository = listingPictureRepository;
        this.listingPictureMapper = listingPictureMapper;
    }

    public List<PictureDTO> saveAll(List<PictureDTO> pictures, Listing listing) {
//        Set<ListingPicture> listingPictures = listingPictureMapper.pictureDTOsToListingPictures(pictures);
        Set<ListingPicture> listingPictures = new HashSet<>();

        boolean isFirst = true;

        for (ListingPicture listingPicture : listingPictures) {
            listingPicture.setCover(isFirst);
            listingPicture.setListing(listing);
            isFirst = false;
        }

        listingPictureRepository.saveAll(listingPictures);
//        return listingPictureMapper.listingPictureToPictureDTO(listingPictures.stream().toList());
        return new ArrayList<>();
    }
}

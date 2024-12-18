package com.manager.hotel.model.dto;

import com.manager.hotel.model.enums.BookingCategory;
import com.manager.hotel.model.vo.PriceVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DisplayListingDTO {

    private DescriptionDTO description;
    private List<PictureDTO> pictures;
    private ListingInfoDTO infos;
    private PriceVO price;
    private BookingCategory category;
    private String location;
    private LandlordListingDTO landlord;

}

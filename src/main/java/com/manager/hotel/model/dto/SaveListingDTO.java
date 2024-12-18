package com.manager.hotel.model.dto;

import com.manager.hotel.model.enums.BookingCategory;
import com.manager.hotel.model.vo.PriceVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SaveListingDTO {

    @NotNull
    BookingCategory category;

    @NotNull
    String location;

    @NotNull
    @Valid
    ListingInfoDTO infos;

    @NotNull
    @Valid
    DescriptionDTO description;

    @NotNull
    @Valid
    PriceVO price;

    @NotNull
    List<PictureDTO> pictures;
}

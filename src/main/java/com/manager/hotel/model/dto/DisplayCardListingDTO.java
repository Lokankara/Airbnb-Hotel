package com.manager.hotel.model.dto;


import com.manager.hotel.model.enums.BookingCategory;
import com.manager.hotel.model.vo.PriceVO;

import java.util.UUID;

public record DisplayCardListingDTO(PriceVO price,
                                    String location,
                                    PictureDTO cover,
                                    BookingCategory bookingCategory,
                                    UUID publicId) {
}

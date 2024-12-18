package com.manager.hotel.model.dto;

import com.manager.hotel.model.vo.PriceVO;

import java.util.UUID;

public record ListingCreateBookingDTO(
        UUID listingPublicId, PriceVO price) {
}

package com.manager.hotel.model.dto;

import com.manager.hotel.model.vo.BathsVO;
import com.manager.hotel.model.vo.BedroomsVO;
import com.manager.hotel.model.vo.BedsVO;
import com.manager.hotel.model.vo.GuestsVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ListingInfoDTO(
        @NotNull @Valid GuestsVO guests,
        @NotNull @Valid BedroomsVO bedrooms,
        @NotNull @Valid BedsVO beds,
        @NotNull @Valid BathsVO baths) {
}

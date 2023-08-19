package com.manager.hotel.service;

import com.manager.hotel.model.dto.CheckOutDto;

public interface CheckOutService {
    CheckOutDto checkOutGuest(Long guestId, boolean earlyDeparture);
}

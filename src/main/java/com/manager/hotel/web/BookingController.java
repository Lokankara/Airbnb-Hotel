package com.manager.hotel.web;

import com.manager.hotel.config.SecurityUtils;
import com.manager.hotel.model.dto.BookedDateDTO;
import com.manager.hotel.model.dto.BookedListingDTO;
import com.manager.hotel.model.dto.NewBookingDTO;
import com.manager.hotel.service.BookingService;
import com.manager.hotel.state.State;
import com.manager.hotel.state.StatusNotification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("create")
    public ResponseEntity<Boolean> create(@Valid @RequestBody NewBookingDTO newBookingDTO) {
        State<Void, String> createState = bookingService.create(newBookingDTO);
        return createState.getStatus().equals(StatusNotification.ERROR)
            ? ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, createState.getError())).build()
            : ResponseEntity.ok(true);
    }

    @GetMapping("check-availability")
    public ResponseEntity<List<BookedDateDTO>> checkAvailability(@RequestParam UUID listingPublicId) {
        return ResponseEntity.ok(bookingService.checkAvailability(listingPublicId));
    }

    @GetMapping("get-booked-listing")
    public ResponseEntity<List<BookedListingDTO>> getBookedListing() {
        return ResponseEntity.ok(bookingService.getBookedListing());
    }

    @DeleteMapping("cancel")
    public ResponseEntity<UUID> cancel(
        @RequestParam UUID bookingPublicId,
        @RequestParam UUID listingPublicId,
        @RequestParam boolean byLandlord) {
        State<UUID, String> cancelState = bookingService.cancel(bookingPublicId, listingPublicId, byLandlord);
        return cancelState.getStatus().equals(StatusNotification.ERROR)
            ? ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, cancelState.getError())).build()
            : ResponseEntity.ok(bookingPublicId);
    }

    @GetMapping("get-booked-listing-for-landlord")
    @PreAuthorize("hasAnyRole('" + SecurityUtils.ROLE_LANDLORD + "')")
    public ResponseEntity<List<BookedListingDTO>> getBookedListingForLandlord() {
        return ResponseEntity.ok(bookingService.getBookedListingForLandlord());
    }
}

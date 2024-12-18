package com.manager.hotel.model.vo;

import jakarta.validation.constraints.NotNull;

public record GuestsVO(@NotNull(message = "Guests value must be present") int value) {
}

package com.manager.hotel.model.vo;

import jakarta.validation.constraints.NotNull;

public record BedsVO(@NotNull(message = "beds value must be present") int value) {
}

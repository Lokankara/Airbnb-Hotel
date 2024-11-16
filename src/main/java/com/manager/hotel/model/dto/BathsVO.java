package com.manager.hotel.model.dto;

import jakarta.validation.constraints.NotNull;

public record BathsVO(@NotNull(message = "Bath value must be present") int value) {
}

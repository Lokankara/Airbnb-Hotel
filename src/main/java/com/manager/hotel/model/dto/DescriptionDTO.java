package com.manager.hotel.model.dto;

import com.manager.hotel.model.vo.DescriptionVO;
import com.manager.hotel.model.vo.TitleVO;
import jakarta.validation.constraints.NotNull;

public record DescriptionDTO(
        @NotNull TitleVO title,
        @NotNull DescriptionVO description
) {
}

package com.manager.hotel.model.dto;

import com.manager.hotel.model.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassportDto {
    @Enumerated(EnumType.STRING)
    private String firstName;
    private String lastName;
    private Gender gender;
    private String address;
    private String email;
    private String phone;
    private String creditCard;
}

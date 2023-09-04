package com.manager.hotel.model.dto;

import com.manager.hotel.model.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PassportDto {
    @Enumerated(EnumType.STRING)
    private String firstname;
    private String lastname;
    private Gender gender;
    private String address;
    private String email;
    private String phone;
    private String creditCard;
}

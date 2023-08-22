package com.manager.hotel.model.entity;

import com.manager.hotel.model.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passport_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotNull
    @Size(min = 3, max = 128)
    @Column(name = "address", length = 128, nullable = false)
    private String address;
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "country", length = 128, nullable = false)
    private String country;
    @NotNull
    @Size(min = 3, max = 128)
    @Column(name = "contacts", length = 128, nullable = false)
    private String contacts;
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "last_name", length = 128, nullable = false)
    private String lastName;
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "first_name", length = 128, nullable = false)
    @NotNull
    private String firstName;
    @Column(name = "birth_date", length = 128)
    private LocalDate birthDate;
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "credit_card", length = 128, nullable = false)
    private String creditCard;
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "nationality", length = 128, nullable = false)
    private String nationality;
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "passport_number", length = 128, nullable = false)
    private String passportNumber;
    @OneToOne(mappedBy = "passport", fetch = FetchType.LAZY)
    private Guest guest;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passport passport = (Passport) o;
        return id.equals(passport.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

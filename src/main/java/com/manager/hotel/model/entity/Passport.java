package com.manager.hotel.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @Column(name = "email", length = 128, nullable = false)
    private String email;
    @NotNull
    @Size(min = 3, max = 128)
    @Column(name = "phone", length = 128, nullable = false)
    private String phone;
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "last_name", length = 128, nullable = false)
    private String lastname;
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "first_name", length = 128, nullable = false)
    @NotNull
    private String firstname;
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "credit_card", length = 128, nullable = false)
    private String creditCard;
    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Passport passport = (Passport) o;
        return id.equals(passport.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

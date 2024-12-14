package com.manager.hotel.model.entity;
import jakarta.persistence.*;

import com.manager.hotel.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class Book extends BaseEntity {

    private Long id;

}

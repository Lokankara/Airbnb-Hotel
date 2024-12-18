package com.manager.hotel.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class
BaseEntity implements Serializable {

  public abstract Long getId();

  @CreatedDate
  @Column(nullable=false, updatable = false, name = "created_date")
  private Instant createdDate = Instant.now();

  @LastModifiedDate
  @Column(insertable = false, name = "last_modified_date")
  private Instant lastModifiedDate = Instant.now();

//    @CreatedBy
//    @Column(nullable = false, updatable = false)
//    private String createdBy;
//
//    @LastModifiedBy
//    @Column(insertable = false)
//    private String lastModifiedBy;
}

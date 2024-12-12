package com.manager.hotel.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity<T> implements Serializable {

  public abstract T getId();

  @CreatedDate
  @Column(updatable = false, name = "created_date")
  private Instant createdDate = Instant.now();

  @LastModifiedDate
  @Column(updatable = false, name = "last_modified_date")
  private Instant lastModifiedDate = Instant.now();
}

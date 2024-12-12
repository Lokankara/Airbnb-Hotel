package com.manager.hotel.model.entity;

import com.manager.hotel.model.AbstractAuditingEntity;
import com.manager.hotel.model.enums.BookingCategory;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "listing")
public class Listing extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "listingSequenceGenerator")
  @SequenceGenerator(name = "listingSequenceGenerator", sequenceName = "listing_generator", allocationSize = 1)
  @Column(name = "id")
  private Long id;
  @UuidGenerator
  @Column(name = "public_id", nullable = false)
  private UUID listingPublicId;
  @Column(name = "title")
  private String title;
  @Column(name = "description")
  private String description;
  @Column(name = "guests")
  private int guests;
  @Column(name = "bedrooms")
  private int bedrooms;
  @Column(name = "beds")
  private int beds;
  @Column(name = "bathrooms")
  private int bathrooms;
  @Column(name = "price")
  private int price;
  @Enumerated(EnumType.STRING)
  @Column(name = "category")
  private BookingCategory bookingCategory;
  @Column(name = "location")
  private String location;
  @Column(name = "landlord_public_id")
  private UUID landlordPublicId;

  @OneToMany(mappedBy = "listing", cascade = CascadeType.REMOVE)
  private Set<ListingPicture> pictures = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Listing listing = (Listing) o;
    return guests == listing.guests && bedrooms == listing.bedrooms && beds == listing.beds && bathrooms == listing.bathrooms && price == listing.price && Objects.equals(title, listing.title) && Objects.equals(description, listing.description) && bookingCategory == listing.bookingCategory && Objects.equals(location, listing.location) && Objects.equals(landlordPublicId, listing.landlordPublicId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, description, guests, bedrooms, beds, bathrooms, price, bookingCategory, location, landlordPublicId);
  }

  @Override
  public String toString() {
    return "Listing{" +
      "title='" + title + '\'' +
      ", description='" + description + '\'' +
      ", guests=" + guests +
      ", bedrooms=" + bedrooms +
      ", beds=" + beds +
      ", bathrooms=" + bathrooms +
      ", price=" + price +
      ", bookingCategory=" + bookingCategory +
      ", location='" + location + '\'' +
      ", landlordPublicId=" + landlordPublicId +
      '}';
  }
}

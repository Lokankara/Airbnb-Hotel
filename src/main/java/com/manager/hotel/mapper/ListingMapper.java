package com.manager.hotel.mapper;

import com.manager.hotel.model.dto.CreatedListingDTO;
import com.manager.hotel.model.dto.DisplayCardListingDTO;
import com.manager.hotel.model.dto.DisplayListingDTO;
import com.manager.hotel.model.dto.ListingCreateBookingDTO;
import com.manager.hotel.model.dto.PriceVO;
import com.manager.hotel.model.dto.SaveListingDTO;
import com.manager.hotel.model.entity.Listing;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ListingPictureMapper.class})
public interface ListingMapper {

//    @Mapping(target = "landlordPublicId", ignore = true)
//    @Mapping(target = "publicId", ignore = true)
//    @Mapping(target = "lastModifiedDate", ignore = true)
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdDate", ignore = true)
//    @Mapping(target = "pictures", ignore = true)
//    @Mapping(target = "title", source = "description.title.value")
//    @Mapping(target = "description", source = "description.description.value")
//    @Mapping(target = "bedrooms", source = "infos.bedrooms.value")
//    @Mapping(target = "guests", source = "infos.guests.value")
//    @Mapping(target = "bookingCategory", source = "category")
//    @Mapping(target = "beds", source = "infos.beds.value")
//    @Mapping(target = "bathrooms", source = "infos.baths.value")
//    @Mapping(target = "price", source = "price.value")
//    Listing saveListingDTOToListing(SaveListingDTO saveListingDTO);

    CreatedListingDTO listingToCreatedListingDTO(Listing listing);

    //    @Mapping(target = "cover", source = "pictures", qualifiedByName = "extract-cover")
    default DisplayCardListingDTO listingToDisplayCardListingDTO(Listing listing) {
        return new DisplayCardListingDTO(null, null, null, null, null);
    }

    @Mapping(target = "cover", source = "pictures")
    List<DisplayCardListingDTO> listingToDisplayCardListingDTOs(List<Listing> listings);


    default PriceVO mapPriceToPriceVO(int price) {
        return new PriceVO(price);
    }

    //    @Mapping(target = "landlord", ignore = true)
//    @Mapping(target = "description.title.value", source = "title")
//    @Mapping(target = "description.description.value", source = "description")
//    @Mapping(target = "infos.bedrooms.value", source = "bedrooms")
//    @Mapping(target = "infos.guests.value", source = "guests")
//    @Mapping(target = "infos.beds.value", source = "beds")
//    @Mapping(target = "infos.baths.value", source = "bathrooms")
//    @Mapping(target = "category", source = "bookingCategory")
//    @Mapping(target = "price.value", source = "price")
    default DisplayListingDTO listingToDisplayListingDTO(Listing listing) {
        return new DisplayListingDTO();
    }

//    @Mapping(target = "listingPublicId", source = "publicId")
    ListingCreateBookingDTO mapListingToListingCreateBookingDTO(Listing listing);
}

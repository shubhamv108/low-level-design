package lowleveldesign.system.management.hotel.api.models.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lowleveldesign.system.management.hotel.api.core.entities.Hotel;
import lowleveldesign.system.management.hotel.api.core.entities.HotelFacility;
import lowleveldesign.system.management.hotel.api.core.entities.RoomFacility;
import lowleveldesign.system.management.hotel.api.core.entities.RoomType;
import lowleveldesign.system.management.hotel.api.core.entities.address.Area;
import lowleveldesign.system.management.hotel.api.core.entities.address.PinCode;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Filter {

    private String keyword;
    private Hotel hotel;
    private RoomType roomType;
    private PriceRange priceRange;
    private List<HotelFacility> hotelFacilities;
    private List<RoomFacility> roomFacilities;
    private Area area;
    private PinCode pinCode;

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class PriceRange {
        private Double startPrice;
        private Double endPrice;
    }

}

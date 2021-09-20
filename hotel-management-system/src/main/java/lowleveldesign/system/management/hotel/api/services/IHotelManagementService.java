package lowleveldesign.system.management.hotel.api.services;

import lowleveldesign.system.management.hotel.api.core.entities.Hotel;

public interface IHotelManagementService {

    Hotel put(Hotel hotel);
    Hotel fetch(Long id);
    Hotel update(Long id, Hotel hotel);

}

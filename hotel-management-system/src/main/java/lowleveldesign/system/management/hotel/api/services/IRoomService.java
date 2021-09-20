package lowleveldesign.system.management.hotel.api.services;

import lowleveldesign.system.management.hotel.api.core.entities.Room;

public interface IRoomService {

    Room put(Room room);
    Room update(Long id, Room room);
    Room fetch(Long id);

}

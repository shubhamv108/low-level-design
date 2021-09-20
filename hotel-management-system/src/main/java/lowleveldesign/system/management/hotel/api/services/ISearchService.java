package lowleveldesign.system.management.hotel.api.services;

import lowleveldesign.system.management.hotel.api.core.entities.Hotel;
import lowleveldesign.system.management.hotel.api.models.search.Filter;

import java.util.List;

public interface ISearchService {

    List<Hotel> getPaginated(Filter filter, Long offset, Long size);

}

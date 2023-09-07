package code.shubham.bookmyshow.api.core.services;

import code.shubham.bookmyshow.api.core.entities.ShowSeat;
import code.shubham.bookmyshow.api.core.entities.User;

public interface IBookingService {

	code.shubham.bookmyshow.api.core.entities.Booking book(ShowSeat showSeat, User user);

}

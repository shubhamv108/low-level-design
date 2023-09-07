package code.shubham.bookmyshow.api.core.services;

import code.shubham.bookmyshow.api.core.entities.Coupon;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ICouponService {

	Set<Coupon> getCouponsSortedByDiscountDescending(code.shubham.bookmyshow.api.core.entities.Booking booking);

}

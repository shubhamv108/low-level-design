package code.shubham.bookmyshow.api.core.services.implementations;

import code.shubham.bookmyshow.api.core.entities.Coupon;
import code.shubham.bookmyshow.api.core.entities.MovieShow;
import code.shubham.bookmyshow.api.core.services.ICouponService;
import code.shubham.bookmyshow.api.core.utils.comparators.CouponComparatorOnDiscountDescending;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.TreeSet;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CouponService implements ICouponService {

	@Override
	public Set<Coupon> getCouponsSortedByDiscountDescending(
			final code.shubham.bookmyshow.api.core.entities.Booking booking) {

		CouponComparatorOnDiscountDescending comparator = new CouponComparatorOnDiscountDescending(
				booking.getTotalPrice());
		Set<Coupon> couponsSortedByDiscountDescending = new TreeSet<>(comparator);

		Set<Coupon> userCoupon = booking.getBookingViewer().getCoupons();

		MovieShow movieShow = booking.getMovieShow();
		Set<Coupon> movieShowCoupons = movieShow.getCoupons();
		Set<Coupon> movieCoupons = movieShow.getMovie().getCoupons();
		Set<Coupon> cinemaCoupons = movieShow.getAuditorium().getCinema().getCoupons();

		couponsSortedByDiscountDescending.addAll(userCoupon);
		couponsSortedByDiscountDescending.addAll(movieShowCoupons);
		couponsSortedByDiscountDescending.addAll(movieCoupons);
		couponsSortedByDiscountDescending.addAll(cinemaCoupons);

		return couponsSortedByDiscountDescending;
	}

}

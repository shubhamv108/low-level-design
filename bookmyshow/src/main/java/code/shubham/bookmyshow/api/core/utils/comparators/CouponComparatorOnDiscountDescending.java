package code.shubham.bookmyshow.api.core.utils.comparators;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Comparator;
import java.util.Optional;

@AllArgsConstructor
@Getter
public class CouponComparatorOnDiscountDescending
		implements Comparator<code.shubham.bookmyshow.api.core.entities.Coupon> {

	private final double price;

	@Override
	public int compare(code.shubham.bookmyshow.api.core.entities.Coupon coupon1,
			code.shubham.bookmyshow.api.core.entities.Coupon coupon2) {
		if (coupon1 == null && coupon2 == null)
			return 0;
		if (coupon1 == null && coupon2 != null)
			return 1;

		double discountPrice1, discountPrice2;

		discountPrice1 = this.getPrice() - Optional.ofNullable(coupon1.getDiscountAmount()).orElse(0d);
		discountPrice2 = this.getPrice()
				- Optional.ofNullable(coupon1.getDiscountPercentage()).map(this::percentageOf).orElse(0d);
		double discountPriceO1 = discountPrice1 < discountPrice2 ? discountPrice1 : discountPrice2;

		discountPrice1 = this.getPrice() - Optional.ofNullable(coupon2.getDiscountAmount()).orElse(0d);
		discountPrice2 = this.getPrice()
				- Optional.ofNullable(coupon2.getDiscountPercentage()).map(this::percentageOf).orElse(0d);
		double discountPriceO2 = discountPrice1 < discountPrice2 ? discountPrice1 : discountPrice2;

		return (int) Math.ceil(discountPriceO1 - discountPriceO2);

	}

	private double percentageOf(double percentage) {
		return this.price * (percentage / 100);
	}

}

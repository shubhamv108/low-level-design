package code.shubham.swiggy.cart.repositories;

import code.shubham.swiggy.cart.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    default Cart clear(final Cart cart) {
        cart.clear();
        return this.save(cart);
    }

}

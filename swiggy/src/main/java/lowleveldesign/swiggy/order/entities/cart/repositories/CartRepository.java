package lowleveldesign.swiggy.order.entities.cart.repositories;

import lowleveldesign.swiggy.order.entities.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    default Cart clear(final Cart cart) {
        cart.clear();
        return this.save(cart);
    }

}

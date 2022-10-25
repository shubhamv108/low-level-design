package code.shubham.wallet;

import java.util.Date;

public class IdentityCard implements ICard {
    private Card card;

    @Override
    public String getName() {
        return card.getName();
    }

    @Override
    public String getNumber() {
        return card.getNumber();
    }

    @Override
    public Date getExpiry() {
        return card.getExpiry();
    }
}

package code.shubham.wallet;

import lombok.Getter;

import java.util.Date;

@Getter
public class PaymentCard implements ICard {

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

    enum CardType {
        DEBIT, CREDIT
    }
    private String cvv;
    private CardType type;
}

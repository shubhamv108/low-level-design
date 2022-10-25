package code.shubham.wallet;

import java.util.Date;

public class Card implements ICard {
    private String name;
    private String number;
    private Date expiry;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public Date getExpiry() {
        return expiry;
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", expiry=" + expiry +
                '}';
    }
}

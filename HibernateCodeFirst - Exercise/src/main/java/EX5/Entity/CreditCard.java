package EX5.Entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "credit_cards")

public class CreditCard extends BillingDetail {
    private CardType cardType;
    private int expirationMonth;
    private int expirationYear;

    private BankAccount account;

    public CreditCard() {
    }
    @Enumerated(EnumType.STRING)
    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    @Column(name = "expiration_month")
    public int getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }
    @Column(name = "expiration_year")
    public int getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }
    @OneToOne
    @JoinColumn(name = "account_id")
    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }
}

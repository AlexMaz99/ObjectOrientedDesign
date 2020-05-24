package pl.edu.agh.internetshop.MoneyTransfer;

import pl.edu.agh.internetshop.MoneyTransfer.MoneyTransfer;
import pl.edu.agh.internetshop.PaymentMethod;

public interface MoneyTransferPaymentTransaction extends PaymentMethod {
    boolean validate(MoneyTransfer transfer);
}

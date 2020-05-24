package pl.edu.agh.internetshop;


import pl.edu.agh.internetshop.MoneyTransfer.MoneyTransfer;

public interface PaymentMethod {
    boolean commit(MoneyTransfer transfer);
}

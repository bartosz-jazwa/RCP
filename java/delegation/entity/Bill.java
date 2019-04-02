package delegation.entity;

import java.time.LocalDate;
import java.util.Currency;

public class Bill {
    private LocalDate date;
    private Currency currency;
    private String subject;
    private boolean payment; //true - cash;
}

package delegation.entity;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;

public class TravelBack {
    private LocalDateTime crossBorderTime;
    private LocalDateTime finishTime;

    {
        Currency currency = Currency.getInstance(Locale.GERMANY);
    }
}

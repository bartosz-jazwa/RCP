import delegation.entity.Bill;
import delegation.entity.Delegation;
import delegation.entity.TravelBack;
import delegation.entity.TravelTo;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class DelegationTest {

    @Test
    void shouldCalculateDurationInHours(){
        Delegation delegation = new Delegation();
        TravelTo travelTo = new TravelTo();
        travelTo.setCrossBorderTime(LocalDateTime.of(2019,1,1,12,0));
        TravelBack travelBack = new TravelBack();
        travelBack.setCrossBorderTime(LocalDateTime.of(2019,1,6,12,0));
        delegation.setTravelTo(travelTo);
        delegation.setTravelBack(travelBack);
        delegation.calcDiet(50.0f);
    }

    @Test
    void shouldGetExchangeRateFromNbp() throws IOException {
        Delegation delegation = new Delegation();
        Float rate= delegation.getExchangeRate(LocalDate.now(), Currency.getInstance("EUR"));
    }

    @Test
    void shouldCalculateTotalSumToPay(){
        Delegation delegation = new Delegation();
        delegation.setCountry(Locale.FRANCE);

        TravelTo travelTo = new TravelTo();
        travelTo.setCrossBorderTime(LocalDateTime.of(2019,1,1,12,0));
        TravelBack travelBack = new TravelBack();
        travelBack.setCrossBorderTime(LocalDateTime.of(2019,1,7,12,0));
        delegation.setTravelTo(travelTo);
        delegation.setTravelBack(travelBack);

        Bill hotel = new Bill(travelTo.getCrossBorderTime().toLocalDate(),Currency.getInstance(delegation.getCountry()),"hotel",60.0f,true);
        Bill fuel = new Bill(travelBack.getCrossBorderTime().toLocalDate(),Currency.getInstance(delegation.getCountry()),"fuel",30.0f,true);

        List<Bill> bills = new ArrayList<>();
        bills.add(hotel);
        bills.add(fuel);
        delegation.setBills(bills);
        delegation.setFileDate(LocalDate.now());
        delegation.calculateSumToPay();
    }
}

import delegation.entity.Delegation;
import delegation.entity.TravelBack;
import delegation.entity.TravelTo;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

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
}

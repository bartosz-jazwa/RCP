package currencies;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.time.LocalDate;

public class AvgRate {
    @JacksonXmlProperty(localName = "EffectiveDate")
    String effectiveDate;
    @JacksonXmlProperty(localName = "Mid")
    String avgRate;

    public Float getRate(){
        return Float.parseFloat(avgRate);
    }
    public LocalDate getDate(){
        return LocalDate.parse(effectiveDate);
    }
}

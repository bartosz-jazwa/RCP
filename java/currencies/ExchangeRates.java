package currencies;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ExchangeRates {
    @JacksonXmlProperty(localName = "Code")
    String code;
    @JacksonXmlElementWrapper(useWrapping = true)
    @JacksonXmlProperty(localName = "Rates")
    List<AvgRate> rates;

    public String getCode() {
        return code;
    }

    public List<AvgRate> getRates() {
        return rates;
    }
}

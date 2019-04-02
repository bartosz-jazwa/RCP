package delegation.entity;


public enum  Country {
    POLAND(Currency.PLN),
    GERMANY(Currency.EUR),
    FRANCE(Currency.EUR),
    SLOVAKIA(Currency.EUR),
    CZECH(Currency.CZK),
    SPAIN(Currency.EUR),
    HUNGARY(Currency.HUF);

    Currency currency;
    private Country(Currency currency){
        this.currency = currency;
    }
}

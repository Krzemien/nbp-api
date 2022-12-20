package pl.filipkrzym.nbp.currency.domain;

import pl.filipkrzym.nbp.currency.domain.dto.CurrencyRequest;
import pl.filipkrzym.nbp.currency.domain.dto.CurrencySumResponse;
import pl.filipkrzym.nbp.currency.domain.dto.NpbApiClientResponse;

import java.time.LocalDate;

public interface ICurrencyService {
    CurrencyResponse getAverageExchangeRateOfTheDay(String code, LocalDate date);
    CurrencySumResponse getSumAverageExchangeRateOfTheDay(CurrencyRequest currencyRequest);
    NpbApiClientResponse cacheData(String code, LocalDate date);
}

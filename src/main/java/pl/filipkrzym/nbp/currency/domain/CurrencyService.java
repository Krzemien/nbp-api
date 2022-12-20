package pl.filipkrzym.nbp.currency.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.filipkrzym.nbp.currency.NbpApiClient;
import pl.filipkrzym.nbp.currency.domain.dto.CurrencyRequest;
import pl.filipkrzym.nbp.currency.domain.dto.CurrencySumResponse;
import pl.filipkrzym.nbp.currency.domain.dto.NpbApiClientResponse;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyService implements ICurrencyService {
    private final NbpApiClient nbpApiClient;
    private final CurrencyRepository currencyRepository;

    @Override
    public CurrencyResponse getAverageExchangeRateOfTheDay(String code, LocalDate date) {
        Currency currency = currencyRepository.findByCodeAndEffectiveDate(code.toUpperCase(), date);
        if (currency == null) {
            NpbApiClientResponse currencyFromNbpApi = this.cacheData(code, date);
            return CurrencyResponse.builder().date(date).code(code).mid(currencyFromNbpApi.getMid()).build();
        }
        return CurrencyResponse.builder().date(date).code(code).mid(currency.getMid()).build();
    }

    @Override
    public CurrencySumResponse getSumAverageExchangeRateOfTheDay(CurrencyRequest currencyRequest) {
        currencyRequest.setCodes(currencyRequest.getCodes().stream().map(String::toUpperCase).collect(Collectors.toSet()));
        Set<Currency> currenciesFromCache = currencyRepository.findByCodeInAndEffectiveDate(currencyRequest.getCodes(), currencyRequest.getDate());
        Set<NpbApiClientResponse> currenciesFromNbpApi = new HashSet<>();
        if (currenciesFromCache.isEmpty() || currenciesFromCache.size() < currencyRequest.getCodes().size()) {
            Set<String> existingCodes = currenciesFromCache.stream().map(Currency::getCode).collect(Collectors.toSet());
            currencyRequest.getCodes().removeAll(existingCodes);
            for (String code : currencyRequest.getCodes()) {
                currenciesFromNbpApi.add(this.cacheData(code, currencyRequest.getDate()));
            }
            System.out.println(currenciesFromNbpApi);
        }
        HashMap<String, Double> currencies = new HashMap<>();
        for (Currency currency : currenciesFromCache) {
            currencies.put(currency.getCode(), currency.getMid());
        }
        for (NpbApiClientResponse currency : currenciesFromNbpApi) {
            currencies.put(currency.getCode(), currency.getMid());
        }
        double totalRate = currencies.values().stream().mapToDouble(d->d).sum();
        return CurrencySumResponse.builder().codes(currencies).date(currencyRequest.getDate()).totalRate(totalRate).build();
    }

    @Override
    public NpbApiClientResponse cacheData(String code, LocalDate date) {
        NpbApiClientResponse data = nbpApiClient.getData("a", code, date);
        if (data != null) {
            Currency currency = new Currency();
            currency.setEffectiveDate(data.getEffectiveDate());
            currency.setCode(data.getCode());
            currency.setMid(data.getMid());
            currencyRepository.save(currency);
            return data;
        }
        return new NpbApiClientResponse();
    }
}

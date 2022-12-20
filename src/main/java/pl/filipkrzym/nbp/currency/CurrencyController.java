package pl.filipkrzym.nbp.currency;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import pl.filipkrzym.nbp.currency.domain.CurrencyService;
import pl.filipkrzym.nbp.currency.domain.dto.CurrencyRequest;

import java.time.LocalDate;

@RestController
@RequestMapping(CurrencyController.URL)
@RequiredArgsConstructor
public class CurrencyController {
    public static final String URL = "/api/currency";
    private final CurrencyService currencyService;

    @GetMapping("/{code}/{date}")
    public ResponseEntity getAverageExchangeRateOfTheDay(@PathVariable String code, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        try {
            return ResponseEntity.ok(currencyService.getAverageExchangeRateOfTheDay(code, date));
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity getAverageExchangeRateOfTheDay(@RequestBody CurrencyRequest currencyRequest) {
        try {
            return ResponseEntity.ok(currencyService.getSumAverageExchangeRateOfTheDay(currencyRequest));
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

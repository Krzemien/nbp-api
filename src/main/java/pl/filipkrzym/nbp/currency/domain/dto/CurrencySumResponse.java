package pl.filipkrzym.nbp.currency.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencySumResponse {
    private LocalDate date;
    private Map<String, Double> codes;
    private Double totalRate;
}

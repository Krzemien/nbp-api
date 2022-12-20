package pl.filipkrzym.nbp.currency.domain.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NpbApiClientResponse {
    private String code;
    private LocalDate effectiveDate;
    private Double mid;

    @JsonAlias("rates")
    public void setRates(List<Rate> rates) {
        this.effectiveDate = rates.get(0).effectiveDate;
        this.mid = rates.get(0).mid;
    }

    @Data
    static class Rate {
        private LocalDate effectiveDate;
        private Double mid;
    }

}

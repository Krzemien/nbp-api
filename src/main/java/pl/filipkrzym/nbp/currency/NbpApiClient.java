package pl.filipkrzym.nbp.currency;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.filipkrzym.nbp.currency.domain.dto.NpbApiClientResponse;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class NbpApiClient {
    private final RestTemplate restTemplate;
    private final String NBP_API_URL = "http://api.nbp.pl/api/exchangerates/rates";

    public NpbApiClientResponse getData(String table, String code, LocalDate date) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("format", "json");
        final UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(NBP_API_URL+"/"+table+"/"+code+"/"+date)
                .queryParams(params);
        System.out.println(uriBuilder.toUriString());
        try {
            return restTemplate.getForObject(uriBuilder.toUriString(), NpbApiClientResponse.class);
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}

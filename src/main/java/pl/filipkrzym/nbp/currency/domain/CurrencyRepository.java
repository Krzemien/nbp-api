package pl.filipkrzym.nbp.currency.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findByCodeAndEffectiveDate(String code, LocalDate date);
    Set<Currency> findByCodeInAndEffectiveDate(Collection<String> codes, LocalDate date);
}

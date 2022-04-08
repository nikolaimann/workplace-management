package de.ehex.workplacemanagment.buchungen;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BuchungRepository extends JpaRepository<Buchung, Long> {

    boolean existsByArbeitsplatzIdAndDatum(long id, LocalDate datum);

    List<Buchung> findByMitarbeiterId(long id);
}
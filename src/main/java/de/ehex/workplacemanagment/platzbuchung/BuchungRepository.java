package de.ehex.workplacemanagment.platzbuchung;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface BuchungRepository extends JpaRepository<Buchung, Long> {

    public boolean existsByArbeitsplatzIdAndDatum(long id, LocalDate datum);
}
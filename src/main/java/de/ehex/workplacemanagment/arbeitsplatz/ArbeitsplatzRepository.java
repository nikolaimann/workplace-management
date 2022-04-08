package de.ehex.workplacemanagment.arbeitsplatz;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArbeitsplatzRepository extends JpaRepository<Arbeitsplatz, Long> {

    List<Arbeitsplatz> findByBeschreibung(String beschreibung);

}

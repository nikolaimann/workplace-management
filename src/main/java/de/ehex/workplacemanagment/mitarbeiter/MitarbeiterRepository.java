package de.ehex.workplacemanagment.mitarbeiter;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MitarbeiterRepository extends JpaRepository<Mitarbeiter, Long> {

    Mitarbeiter findMitarbeiterByBenutzername(String benutzername);

    Optional<Mitarbeiter> findOptionalMitarbeiterByBenutzername(String benutzername);
}
package de.ehex.workplacemanagment.mitarbeiter;

public class MitarbeiterNotFoundException extends RuntimeException {

    public MitarbeiterNotFoundException(Long id) {
        super("Mitarbeiter mit der ID " + id + "konnte nicht gefunden werden");
    }
}
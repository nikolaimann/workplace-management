package de.ehex.workplacemanagment.mitarbeiter;

public class MitarbeiterNotFoundException extends RuntimeException {

    public MitarbeiterNotFoundException(Long id) {
        super("Mitarbeiter mit der ID " + id + "konnte nicht gefunden werden");
    }
    public MitarbeiterNotFoundException(String username) {
        super("Mitarbeiter mit dem Username " + username + "konnte nicht gefunden werden");
    }

}
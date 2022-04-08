package de.ehex.workplacemanagment.arbeitsplatz;

public class ArbeitsplatzNotFoundException extends RuntimeException {
    public ArbeitsplatzNotFoundException(Long id) {
        super("Arbeitsplatz mit der ID " + id + "konnte nicht gefunden werden");
    }
}

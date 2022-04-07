package de.ehex.workplacemanagment.buchungen;

public class BuchungNotFoundException extends RuntimeException {

    public BuchungNotFoundException(Long id) {
        super("Buchung mit der ID " + id + "konnte nicht gefunden werden");
    }
}

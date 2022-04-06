package de.ehex.workplacemanagment.platzbuchung;

public class BuchungNotFoundException extends RuntimeException {

    public BuchungNotFoundException(Long id) {
        super("Buchung mit der ID " + id + "konnte nicht gefunden werden");
    }
}

package de.ehex.workplacemanagment.platzbuchung;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ArbeitsplatzBelegtException extends RuntimeException {

    public ArbeitsplatzBelegtException(Long id, LocalDate datum) {
        super("Der Arbeitsplatz mit der ID " + id + " ist am " + datum.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " schon belegt");
    }

}

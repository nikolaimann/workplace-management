package de.ehex.workplacemanagment.platzbuchung;

import java.time.LocalDate;

public class CreateBuchung {

    private long mitarbeiterId;
    private long arbeitsplatzId;
    private LocalDate datum;

    public long getMitarbeiterId() {
        return mitarbeiterId;
    }

    public void setMitarbeiterId(long mitarbeiterId) {
        this.mitarbeiterId = mitarbeiterId;
    }

    public long getArbeitsplatzId() {
        return arbeitsplatzId;
    }

    public void setArbeitsplatzId(long arbeitsplatzId) {
        this.arbeitsplatzId = arbeitsplatzId;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }
}

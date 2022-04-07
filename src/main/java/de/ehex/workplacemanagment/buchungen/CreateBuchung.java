package de.ehex.workplacemanagment.buchungen;

import java.time.LocalDate;

public class CreateBuchung {

    private long mitarbeiterId;
    private long arbeitsplatzId;
    private String datum;

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

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

}

package de.ehex.workplacemanagment.buchungen;

public class CreateBuchung {

    private long arbeitsplatzId;
    private String datum;

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

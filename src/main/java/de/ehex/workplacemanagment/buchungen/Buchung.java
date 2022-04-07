package de.ehex.workplacemanagment.buchungen;

import de.ehex.workplacemanagment.arbeitsplatz.Arbeitsplatz;
import de.ehex.workplacemanagment.mitarbeiter.Mitarbeiter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "buchung")
public class Buchung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    @ManyToOne
    @JoinColumn(name = "mitarbeiter_id")
    private Mitarbeiter mitarbeiter;
    @ManyToOne
    @JoinColumn(name = "arbeitsplatz_id")
    private Arbeitsplatz arbeitsplatz;
    private LocalDate datum;

    Buchung() {

    }

    Buchung(Mitarbeiter mitarbeiter, Arbeitsplatz arbeitsplatz, LocalDate datum) {
        this.mitarbeiter = mitarbeiter;
        this.arbeitsplatz = arbeitsplatz;
        this.datum = datum;
    }

    public Arbeitsplatz getArbeitsplatz() {
        return arbeitsplatz;
    }

    public Mitarbeiter getMitarbeiter() {
        return mitarbeiter;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public long getId() {
        return id;
    }

    public void setMitarbeiter(Mitarbeiter mitarbeiter) {
        this.mitarbeiter = mitarbeiter;
    }

    public void setArbeitsplatz(Arbeitsplatz arbeitsplatz) {
        this.arbeitsplatz = arbeitsplatz;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public void setId(long id) {
        this.id = id;
    }
}
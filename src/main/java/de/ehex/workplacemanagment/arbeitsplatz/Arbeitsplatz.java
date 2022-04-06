package de.ehex.workplacemanagment.arbeitsplatz;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ARBEITSPLATZ")
public class Arbeitsplatz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    private long anzahlBildschirme;
    private long raum;
    private String beschreibung;

    public Arbeitsplatz() {
    }

    public Arbeitsplatz(long raum, long anzahlBildschirme, String beschreibung) {
        this.raum = raum;
        this.anzahlBildschirme = anzahlBildschirme;
        this.beschreibung = beschreibung;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAnzahlBildschirme() {
        return anzahlBildschirme;
    }

    public void setAnzahlBildschirme(long anzahlBildschirme) {
        this.anzahlBildschirme = anzahlBildschirme;
    }

    public long getRaum() {
        return raum;
    }

    public void setRaum(long raum) {
        this.raum = raum;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        } else if (!(o instanceof Arbeitsplatz)) {
            return false;
        }
        Arbeitsplatz m = (Arbeitsplatz) o;
        return Objects.equals(this.id, m.id) && Objects.equals(this.anzahlBildschirme, m.anzahlBildschirme)
                && Objects.equals(this.raum, m.raum) && Objects.equals(this.beschreibung, m.beschreibung);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.anzahlBildschirme, this.raum, this.beschreibung);
    }

    @Override
    public String toString() {
        return "Mitarbeiter{" + "id=" + this.id + ", anzahlBildschirme='" + this.anzahlBildschirme
                + '\'' + ", raum='" + this.raum + '\'' + ", beschreibung='" + this.beschreibung
                + '\'' + '}';
    }
}



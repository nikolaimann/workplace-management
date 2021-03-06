package de.ehex.workplacemanagment.mitarbeiter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "MITARBEITER")
public class Mitarbeiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    private String vorname;
    private String name;
    private String benutzername;
    private String passwort;

    public Mitarbeiter() {
    }

    public Mitarbeiter(String vorname, String name, String passwort, String benutzername) {
        this.vorname = vorname;
        this.name = name;
        this.passwort = passwort;
        this.benutzername = benutzername;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        } else if (!(o instanceof Mitarbeiter)) {
            return false;
        }
        Mitarbeiter m = (Mitarbeiter) o;
        return Objects.equals(this.id, m.id)
                && Objects.equals(this.name, m.name)
                && Objects.equals(this.vorname, m.vorname)
                && Objects.equals(this.passwort, m.passwort)
                && Objects.equals(this.benutzername, m.benutzername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.vorname, this.name, this.passwort, this.benutzername);
    }

    @Override
    public String toString() {
        return "Mitarbeiter{" + "id=" + this.id
                + ", vorname='" + this.vorname
                + "', name='" + this.name
                + ", passwort '" + this.passwort
                + ", benutzername '" + this.benutzername
                + "'}";
    }
}
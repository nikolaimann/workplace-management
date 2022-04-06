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
    private String vorName;
    private String name;

    public Mitarbeiter() {
    }

    public Mitarbeiter(String vorName, String name) {
        this.vorName = vorName;
        this.name = name;
    }

    public String getVorName() {
        return vorName;
    }

    public void setVorName(String vorName) {
        this.vorName = vorName;
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

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        } else if (!(o instanceof Mitarbeiter)) {
            return false;
        }
        Mitarbeiter m = (Mitarbeiter) o;
        return Objects.equals(this.id, m.id) && Objects.equals(this.name, m.name) && Objects.equals(this.vorName, m.vorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.vorName, this.name);
    }

    @Override
    public String toString() {
        return "Mitarbeiter{" + "id=" + this.id + ", vorName='" + this.vorName + '\'' + ", name='" + this.name
                + '\'' + '}';
    }
}
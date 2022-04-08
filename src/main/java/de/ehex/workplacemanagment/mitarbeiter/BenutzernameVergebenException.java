package de.ehex.workplacemanagment.mitarbeiter;

public class BenutzernameVergebenException extends RuntimeException {

    public BenutzernameVergebenException(String benutzername) {
        super("Der Benutzername "+ benutzername + " ist leider schon vergeben");
    }
}

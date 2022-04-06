package de.ehex.workplacemanagment.mitarbeiter;

class MitarbeiterNotFoundException extends RuntimeException {

    MitarbeiterNotFoundException(Long id) {
        super("Mitarbeiter mit der ID " + id + "konnte nicht gefunden werden");
    }
}
package de.ehex.workplacemanagment;

import de.ehex.workplacemanagment.arbeitsplatz.Arbeitsplatz;
import de.ehex.workplacemanagment.arbeitsplatz.ArbeitsplatzRepository;
import de.ehex.workplacemanagment.mitarbeiter.Mitarbeiter;
import de.ehex.workplacemanagment.mitarbeiter.MitarbeiterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ArbeitsplatzRepository arbeitsplatzRepository, MitarbeiterRepository mitarbeiterRepository) {

        return args -> {
            if (mitarbeiterRepository.count() == 0) {
                mitarbeiterRepository.save(new Mitarbeiter("Max", "Mustermann","geheim", "mustermann"));
                mitarbeiterRepository.save(new Mitarbeiter("Josia", "Menger","geheim", "menger"));
                mitarbeiterRepository.save(new Mitarbeiter("Nikolai", "Mann","geheim", "mann"));
                mitarbeiterRepository.save(new Mitarbeiter("Alexander", "Jorde","geheim", "jorde"));
                mitarbeiterRepository.save(new Mitarbeiter("Admin", "Admin","admin", "admin"));
                mitarbeiterRepository.findAll().forEach(mitarbeiter -> log.info("Preloading " + mitarbeiter));
            }
            if (arbeitsplatzRepository.count() == 0) {
                arbeitsplatzRepository.save(new Arbeitsplatz(4,  "hinten links",2, "-"));
                arbeitsplatzRepository.save(new Arbeitsplatz(2,  "tür, rechts", 1,"Ultrawide Monitor"));
                arbeitsplatzRepository.findAll().forEach(arbeitsplatz -> log.info("Preloading " + arbeitsplatz));
            }
        };
    }
}

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

//    @Bean
//    CommandLineRunner initDatabase(ArbeitsplatzRepository arbeitsplatzRepository, MitarbeiterRepository mitarbeiterRepository) {
//
//        return args -> {
//          mitarbeiterRepository.save(new Mitarbeiter("Max", "Mustermann"));
//          mitarbeiterRepository.save(new Mitarbeiter("Josia", "Menger"));
//          mitarbeiterRepository.save(new Mitarbeiter("Nikolai", "Mann"));
//          mitarbeiterRepository.save(new Mitarbeiter("Alexander", "Jorde"));
//
//          arbeitsplatzRepository.save(new Arbeitsplatz(4, 2, "hinten links"));
//          arbeitsplatzRepository.save(new Arbeitsplatz(2, 1, "Ultrawide Monitor für Mac geeignet"));
//
//          mitarbeiterRepository.findAll().forEach(mitarbeiter -> log.info("Preloading " + mitarbeiter));
//          arbeitsplatzRepository.findAll().forEach(arbeitsplatz -> log.info("Preloading " + arbeitsplatz));
//
//        };
//    }
}

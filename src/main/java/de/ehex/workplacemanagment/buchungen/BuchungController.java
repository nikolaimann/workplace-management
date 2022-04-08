package de.ehex.workplacemanagment.buchungen;

import de.ehex.workplacemanagment.arbeitsplatz.ArbeitsplatzNotFoundException;
import de.ehex.workplacemanagment.arbeitsplatz.ArbeitsplatzRepository;
import de.ehex.workplacemanagment.mitarbeiter.MitarbeiterNotFoundException;
import de.ehex.workplacemanagment.mitarbeiter.MitarbeiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class BuchungController {

    @Autowired
    MitarbeiterRepository mitarbeiterRepository;
    @Autowired
    ArbeitsplatzRepository arbeitsplatzRepository;
    @Autowired
    BuchungRepository buchungRepository;
    @Autowired
    BuchungModelAssembler assembler;

    Buchung buchungUeberpruefen(CreateBuchung createBuchung, String username)
            throws MitarbeiterNotFoundException, ArbeitsplatzNotFoundException {
        var mitarbeiter = mitarbeiterRepository.findOptionalMitarbeiterByBenutzername(username)
                .orElseThrow(()-> new MitarbeiterNotFoundException(createBuchung.getMitarbeiterId()));
        var arbeitsplatz = arbeitsplatzRepository.findById(createBuchung.getArbeitsplatzId())
                .orElseThrow(()-> new ArbeitsplatzNotFoundException(createBuchung.getArbeitsplatzId()));
        return new Buchung(mitarbeiter, arbeitsplatz, LocalDate.parse(createBuchung.getDatum()));
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/api/buchungen")
    public CollectionModel<EntityModel<Buchung>> all() {

        List<EntityModel<Buchung>> m = buchungRepository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(m, linkTo(methodOn(BuchungController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/api/buchung")
    public ResponseEntity<?> newBuchung(@RequestBody CreateBuchung createBuchung, @CurrentSecurityContext(expression="authentication.name") String username)
            throws ArbeitsplatzBelegtException, ArbeitsplatzNotFoundException, MitarbeiterNotFoundException {
       if (buchungRepository.existsByArbeitsplatzIdAndDatum(createBuchung.getArbeitsplatzId(), LocalDate.parse(createBuchung.getDatum()))) {
           throw new ArbeitsplatzBelegtException(createBuchung.getArbeitsplatzId(), LocalDate.parse(createBuchung.getDatum()));
       }
        Buchung buchung = buchungRepository.save(buchungUeberpruefen(createBuchung, username));

        EntityModel<Buchung> entityModel = assembler.toModel(buchung);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
    // Single item

    @GetMapping("/api/buchung/{id}")
    public EntityModel<Buchung> one(@PathVariable Long id) {

        Buchung buchung = buchungRepository.findById(id)
                .orElseThrow(() -> new BuchungNotFoundException(id));

        return assembler.toModel(buchung);
    }

    @DeleteMapping("/api/buchung/{id}")
    public ResponseEntity<?> deleteBuchung(@PathVariable Long id) {

        buchungRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}

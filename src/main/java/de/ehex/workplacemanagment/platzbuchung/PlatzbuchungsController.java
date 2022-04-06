package de.ehex.workplacemanagment.platzbuchung;

import de.ehex.workplacemanagment.arbeitsplatz.ArbeitsplatzNotFoundException;
import de.ehex.workplacemanagment.arbeitsplatz.ArbeitsplatzRepository;
import de.ehex.workplacemanagment.mitarbeiter.MitarbeiterNotFoundException;
import de.ehex.workplacemanagment.mitarbeiter.MitarbeiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PlatzbuchungsController {

    @Autowired
    MitarbeiterRepository mitarbeiterRepository;
    @Autowired
    ArbeitsplatzRepository arbeitsplatzRepository;
    @Autowired
    BuchungRepository buchungRepository;
    @Autowired
    PlatzbuchungModelAssembler assembler;

    public Buchung buchen(CreateBuchung createBuchung) {
        var mitarbeiter = mitarbeiterRepository.findById(createBuchung.getMitarbeiterId())
                .orElseThrow(()-> new MitarbeiterNotFoundException(createBuchung.getMitarbeiterId()));
        var arbeitsplatz = arbeitsplatzRepository.findById(createBuchung.getArbeitsplatzId())
                .orElseThrow(()-> new ArbeitsplatzNotFoundException(createBuchung.getArbeitsplatzId()));
        return new Buchung(mitarbeiter, arbeitsplatz, createBuchung.getDatum());
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/buchungen")
    CollectionModel<EntityModel<Buchung>> all() {

        List<EntityModel<Buchung>> m = buchungRepository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(m, linkTo(methodOn(PlatzbuchungsController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/buchung")
    ResponseEntity<?> newBuchung(@RequestBody CreateBuchung createBuchung) {
       if (buchungRepository.existsByArbeitsplatzIdAndDatum(createBuchung.getArbeitsplatzId(), createBuchung.getDatum())) {
           throw new ArbeitsplatzBelegtException(createBuchung.getArbeitsplatzId(), createBuchung.getDatum());
       }
        Buchung buchung = buchungRepository.save(buchen(createBuchung));

        EntityModel<Buchung> entityModel = assembler.toModel(buchung);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
    // Single item

    @GetMapping("/buchung/{id}")
    EntityModel<Buchung> one(@PathVariable Long id) {

        Buchung buchung = buchungRepository.findById(id)
                .orElseThrow(() -> new BuchungNotFoundException(id));

        return assembler.toModel(buchung);
    }

    @PutMapping("/buchung/{id}")
    ResponseEntity<?> replaceBuchung(@RequestBody CreateBuchung createBuchung, @PathVariable Long id) {

        Buchung updatedBuchung = buchungRepository.findById(id)
                .map(buchung -> {
                    buchung.setMitarbeiter(mitarbeiterRepository.getById(createBuchung.getMitarbeiterId()));
                    buchung.setArbeitsplatz(arbeitsplatzRepository.getById(createBuchung.getArbeitsplatzId()));
                    return buchungRepository.save(buchung);
                })
                .orElseGet(() -> buchen(createBuchung));

        EntityModel<Buchung> entityModel = assembler.toModel(updatedBuchung);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/buchung/{id}")
    ResponseEntity<?> deleteBuchung(@PathVariable Long id) {

        buchungRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}

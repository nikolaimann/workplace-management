package de.ehex.workplacemanagment.arbeitsplatz;

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
public class ArbeitsplatzController {

    private final ArbeitsplatzRepository repository;
    private final ArbeitsplatzModelAssembler assembler;

    ArbeitsplatzController(ArbeitsplatzRepository repository, ArbeitsplatzModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/arbeitsplatz")
    CollectionModel<EntityModel<Arbeitsplatz>> all() {

        List<EntityModel<Arbeitsplatz>> a = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(a, linkTo(methodOn(ArbeitsplatzController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/arbeitsplatz")
    ResponseEntity<?> newArbeitsplatz(@RequestBody Arbeitsplatz neuerArbeitsplatz) {

        EntityModel<Arbeitsplatz> entityModel = assembler.toModel(repository.save(neuerArbeitsplatz));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
    // Single item

    @GetMapping("/arbeitsplatz/{id}")
    EntityModel<Arbeitsplatz> one(@PathVariable Long id) {

        Arbeitsplatz arbeitsplatz = repository.findById(id)
                .orElseThrow(() -> new ArbeitsplatzNotFoundException(id));

        return assembler.toModel(arbeitsplatz);
    }

    @PutMapping("/arbeitsplatz/{id}")
    ResponseEntity<?> replaceArbeitsplatz(@RequestBody Arbeitsplatz neuerArbeitsplatz, @PathVariable Long id) {

        Arbeitsplatz updatedArbeitsplatz = repository.findById(id)
                .map(arbeitsplatz -> {
                    arbeitsplatz.setAnzahlBildschirme(neuerArbeitsplatz.getAnzahlBildschirme());
                    arbeitsplatz.setBeschreibung(neuerArbeitsplatz.getBeschreibung());
                    arbeitsplatz.setRaum(neuerArbeitsplatz.getRaum());
                    return repository.save(arbeitsplatz);
                }) //
                .orElseGet(() -> {
                    neuerArbeitsplatz.setId(id);
                    return repository.save(neuerArbeitsplatz);
                });

        EntityModel<Arbeitsplatz> entityModel = assembler.toModel(updatedArbeitsplatz);

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/arbeitsplatz/{id}")
    ResponseEntity<?> deleteArbeitsplatz(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}

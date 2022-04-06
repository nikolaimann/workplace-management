package de.ehex.workplacemanagment.mitarbeiter;

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
class MitarbeiterController {

    private final MitarbeiterRepository repository;

    private final MitarbeiterModelAssembler assembler;

    MitarbeiterController(MitarbeiterRepository repository, MitarbeiterModelAssembler assembler) {

        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/mitarbeiter")
    CollectionModel<EntityModel<Mitarbeiter>> all() {

        List<EntityModel<Mitarbeiter>> m = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(m, linkTo(methodOn(MitarbeiterController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/mitarbeiter")
    ResponseEntity<?> newMitarbeiter(@RequestBody Mitarbeiter newEmployee) {

        EntityModel<Mitarbeiter> entityModel = assembler.toModel(repository.save(newEmployee));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
    // Single item

    @GetMapping("/mitarbeiter/{id}")
    EntityModel<Mitarbeiter> one(@PathVariable Long id) {

        Mitarbeiter mitarbeiter = repository.findById(id)
                .orElseThrow(() -> new MitarbeiterNotFoundException(id));

        return assembler.toModel(mitarbeiter);
    }

    @PutMapping("/mitarbeiter/{id}")
    ResponseEntity<?> replaceMitarbeiter(@RequestBody Mitarbeiter neuerMitarbeiter, @PathVariable Long id) {

        Mitarbeiter updatedMitarbeiter = repository.findById(id)
                .map(mitarbeiter -> {
                    mitarbeiter.setName(neuerMitarbeiter.getName());
                    mitarbeiter.setVorName(neuerMitarbeiter.getVorName());
                    return repository.save(mitarbeiter);
                })
                .orElseGet(() -> {
                    neuerMitarbeiter.setId(id);
                    return repository.save(neuerMitarbeiter);
                });

        EntityModel<Mitarbeiter> entityModel = assembler.toModel(updatedMitarbeiter);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/mitarbeiter/{id}")
    ResponseEntity<?> deleteMitarbeiter(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
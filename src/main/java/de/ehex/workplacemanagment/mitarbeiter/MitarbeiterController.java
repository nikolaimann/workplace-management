package de.ehex.workplacemanagment.mitarbeiter;

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
class MitarbeiterController {

    @Autowired
    private MitarbeiterRepository repository;

    @Autowired
    private MitarbeiterModelAssembler assembler;

    private boolean benutzerNameVergeben(Mitarbeiter mitarbeiter) {

        if (repository.findMitarbeiterByBenutzername(mitarbeiter.getBenutzername()) == null) {
            return false;
        }
        return true;
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/api/mitarbeiter")
    CollectionModel<EntityModel<Mitarbeiter>> all() {

        List<EntityModel<Mitarbeiter>> m = repository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(m, linkTo(methodOn(MitarbeiterController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/api/mitarbeiter")
    ResponseEntity<?> newMitarbeiter(@RequestBody Mitarbeiter neuerMitarbeiter) throws BenutzernameVergebenException {

        if (benutzerNameVergeben(neuerMitarbeiter)) {
            throw new BenutzernameVergebenException(neuerMitarbeiter.getBenutzername());
        }

        EntityModel<Mitarbeiter> entityModel = assembler.toModel(repository.save(neuerMitarbeiter));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
    // Single item

    @GetMapping("/api/mitarbeiter/{id}")
    EntityModel<Mitarbeiter> one(@PathVariable Long id) {

        Mitarbeiter mitarbeiter = repository.findById(id)
                .orElseThrow(() -> new MitarbeiterNotFoundException(id));

        return assembler.toModel(mitarbeiter);
    }

    @PutMapping("/api/mitarbeiter/{id}")
    ResponseEntity<?> replaceMitarbeiter(@RequestBody Mitarbeiter neuerMitarbeiter, @PathVariable Long id)
            throws BenutzernameVergebenException {

        if (benutzerNameVergeben(neuerMitarbeiter)) {
            throw new BenutzernameVergebenException(neuerMitarbeiter.getBenutzername());
        }

        Mitarbeiter updatedMitarbeiter = repository.findById(id)
                .map(mitarbeiter -> {
                    mitarbeiter.setName(neuerMitarbeiter.getName());
                    mitarbeiter.setVorname(neuerMitarbeiter.getVorname());
                    mitarbeiter.setPasswort(neuerMitarbeiter.getPasswort());
                    mitarbeiter.setBenutzername(neuerMitarbeiter.getBenutzername());
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

    @DeleteMapping("/api/mitarbeiter/{id}")
    ResponseEntity<?> deleteMitarbeiter(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
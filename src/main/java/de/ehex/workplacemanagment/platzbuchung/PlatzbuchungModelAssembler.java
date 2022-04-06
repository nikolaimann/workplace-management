package de.ehex.workplacemanagment.platzbuchung;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PlatzbuchungModelAssembler implements RepresentationModelAssembler<Buchung, EntityModel<Buchung>> {


    @Override
    public EntityModel<Buchung> toModel(Buchung entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(PlatzbuchungsController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(PlatzbuchungsController.class).all()).withRel("buchungen"));
    }
}

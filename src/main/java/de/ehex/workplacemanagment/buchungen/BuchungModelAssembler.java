package de.ehex.workplacemanagment.buchungen;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BuchungModelAssembler implements RepresentationModelAssembler<Buchung, EntityModel<Buchung>> {


    @Override
    public EntityModel<Buchung> toModel(Buchung entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(BuchungController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(BuchungController.class).all()).withRel("buchungen"));
    }
}

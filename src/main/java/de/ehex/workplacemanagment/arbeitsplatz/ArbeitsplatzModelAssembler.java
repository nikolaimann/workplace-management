package de.ehex.workplacemanagment.arbeitsplatz;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ArbeitsplatzModelAssembler implements RepresentationModelAssembler<Arbeitsplatz, EntityModel<Arbeitsplatz>> {


    @Override
    public EntityModel<Arbeitsplatz> toModel(Arbeitsplatz entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(ArbeitsplatzController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(ArbeitsplatzController.class).all()).withRel("arbeitsplätze"));

    }
}
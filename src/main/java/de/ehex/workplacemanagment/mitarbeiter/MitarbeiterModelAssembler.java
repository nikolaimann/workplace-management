package de.ehex.workplacemanagment.mitarbeiter;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
class MitarbeiterModelAssembler implements RepresentationModelAssembler<Mitarbeiter, EntityModel<Mitarbeiter>> {

    @Override
    public EntityModel<Mitarbeiter> toModel(Mitarbeiter employee) {

        return EntityModel.of(employee,
                linkTo(methodOn(MitarbeiterController.class).one(employee.getId())).withSelfRel(),
                linkTo(methodOn(MitarbeiterController.class).all()).withRel("mitarbeiter"));
    }
}

package de.ehex.workplacemanagment.mitarbeiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MitarbeiterAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    MitarbeiterRepository repository;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        try {
            String name = authentication.getName();
            String password = authentication.getCredentials().toString();

            Mitarbeiter mitarbeiter = repository.findMitarbeiterByBenutzername(name);

            if (mitarbeiter.getPasswort().equals(password)) {

                // use the credentials
                // and authenticate against the third-party system
                return new UsernamePasswordAuthenticationToken(
                        name, password, new ArrayList<>());
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

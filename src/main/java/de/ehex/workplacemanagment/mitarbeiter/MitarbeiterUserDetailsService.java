package de.ehex.workplacemanagment.mitarbeiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MitarbeiterUserDetailsService implements UserDetailsService {

    @Autowired
    MitarbeiterRepository mitarbeiterRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Mitarbeiter mitarbeiter = mitarbeiterRepository.findMitarbeiterByBenutzername(username);
        if (mitarbeiter == null) {
            throw new UsernameNotFoundException("Der Mitarbeiter mit dem Benutzernamen " + username + " konnten nicht gefunden werden");
        }
        return new MitarbeiterUserDetails(mitarbeiter);
    }
}

package de.ehex.workplacemanagment;

import de.ehex.workplacemanagment.arbeitsplatz.Arbeitsplatz;
import de.ehex.workplacemanagment.arbeitsplatz.ArbeitsplatzRepository;
import de.ehex.workplacemanagment.mitarbeiter.Mitarbeiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ArbeitsplatzTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ArbeitsplatzRepository arbeitsplatzRepository;

    @BeforeEach
    public void setup() throws Exception {

        if (arbeitsplatzRepository.findByBeschreibung("testplatz").size() == 0) {
            this.mockMvc
                    .perform(
                            MockMvcRequestBuilders.post("/api/arbeitsplatz")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{\"anzahlBildschirme\": 2,\"raum\": 7,\"tischBezeichnung\": \"hinten links\",\"beschreibung\": \"testplatz\"}")
                    );
        }
    }

//    @Test
//    @WithMockUser(roles = "USER", username="testperson")
//    public void testArbeitsplatzDoppeltHinzufuegen() throws Exception {
//    }

    @Test
    @WithMockUser(roles = "USER", username="testperson")
    public void testBuchungLoeschen() throws Exception {

        Arbeitsplatz arbeitsplatz = arbeitsplatzRepository.findByBeschreibung("testplatz").get(0);

        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.delete("/api/arbeitsplatz/{id}", arbeitsplatz.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @WithMockUser(roles = "USER", username="testperson")
    public void testGetAllBuchungen() throws Exception {

        Arbeitsplatz arbeitsplatz = arbeitsplatzRepository.findByBeschreibung("testplatz").get(0);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/arbeitsplatz")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$._embedded.arbeitsplatzList["+ (arbeitsplatz.getId() - 1) +"].anzahlBildschirme").value(2));
    }

    @Test
    @WithMockUser(roles = "USER", username="testperson")
    public void testGetOneBuchungen() throws Exception {

        Arbeitsplatz arbeitsplatz = arbeitsplatzRepository.findByBeschreibung("testplatz").get(0);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/arbeitsplatz/{id}", arbeitsplatz.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.anzahlBildschirme").value(2))
                .andExpect(jsonPath("$.raum").value(7));
    }

}

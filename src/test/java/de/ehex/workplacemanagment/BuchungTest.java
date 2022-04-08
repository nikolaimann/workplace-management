package de.ehex.workplacemanagment;

import de.ehex.workplacemanagment.buchungen.BuchungRepository;
import de.ehex.workplacemanagment.mitarbeiter.MitarbeiterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class BuchungTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    MitarbeiterRepository mitarbeiterRepository;

    @Autowired
    BuchungRepository buchungRepository;

    long mitarbeiterId = 0;

    @BeforeEach
    @WithMockUser(roles = "USER", username="testperson")
    public void setup() throws Exception {
        if (mitarbeiterRepository.findMitarbeiterByBenutzername("testperson") == null) {
            this.mockMvc
                    .perform(
                            MockMvcRequestBuilders.post("/api/mitarbeiter")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{\"vorname\": \"Max\",\"name\": \"Mustermann\",\"benutzername\": \"testperson\",\"passwort\": \"test\"}")
                    );
        }

        mitarbeiterId = mitarbeiterRepository.findMitarbeiterByBenutzername("testperson").getId();

        if (buchungRepository.findByMitarbeiterId(mitarbeiterId).size()==0) {
            this.mockMvc
                    .perform(
                            MockMvcRequestBuilders.post("/api/buchung")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{\"arbeitsplatzId\": 1,\"datum\": \"2022-04-01\"}")
                    );
        }
    }

    @Test
    @WithMockUser(roles = "USER", username="testperson")
    public void testBuchungHinzufuegen() throws Exception {
                this.mockMvc
                        .perform(
                                MockMvcRequestBuilders.post("/api/buchung")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content("{\"arbeitsplatzId\": 1,\"datum\": \"2022-04-01\"}")
                        )
                        .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(roles = "USER", username="testperson")
    public void testBuchungLoeschen() throws Exception {

        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.delete("/api/buchung/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @WithMockUser(roles = "USER", username="testperson")
    public void testGetAllBuchungen() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/buchungen")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$._embedded.buchungList[0].arbeitsplatz.id").value("1"));
    }

    @Test
    @WithMockUser(roles = "USER", username="testperson")
    public void testGetOneBuchungen() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/buchung/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.arbeitsplatz.id").value("1"))
                .andExpect(jsonPath("$.mitarbeiter.vorname").value("Max"));
    }
}

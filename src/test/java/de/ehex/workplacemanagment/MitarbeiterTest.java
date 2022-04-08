package de.ehex.workplacemanagment;

import de.ehex.workplacemanagment.mitarbeiter.Mitarbeiter;
import de.ehex.workplacemanagment.mitarbeiter.MitarbeiterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MitarbeiterTest {

    @Autowired
    MitarbeiterRepository repository;

    @BeforeEach
    public void testBuchungenHinzufuegen() throws Exception {

        if (repository.findMitarbeiterByBenutzername("testperson") == null) {
            this.mockMvc
                    .perform(
                            MockMvcRequestBuilders.post("/api/mitarbeiter")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{\"vorname\": \"Max\",\"name\": \"Mustermann\",\"benutzername\": \"testperson\",\"passwort\": \"test\"}")
                    );
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "USER", username="testperson")
    public void testMitarbeiterDoppeltHinzufuegen() throws Exception {

        Assertions.assertThrows(NestedServletException.class, () ->
                this.mockMvc
                        .perform(
                                MockMvcRequestBuilders.post("/api/mitarbeiter")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content("{\"vorname\": \"Max\",\"name\": \"Mustermann\",\"benutzername\": \"testperson\",\"passwort\": \"test\"}")
                        )
                        .andExpect(mvcResult -> Assertions.assertTrue(mvcResult instanceof NestedServletException)));
    }

    @Test
    @WithMockUser(roles = "USER", username="testperson")
    public void testBuchungLoeschen() throws Exception {

        Mitarbeiter testperson = repository.findMitarbeiterByBenutzername("testperson");

        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.delete("/api/mitarbeiter/{id}", testperson.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @WithMockUser(roles = "USER", username="testperson")
    public void testGetAllBuchungen() throws Exception {

        Mitarbeiter testperson = repository.findMitarbeiterByBenutzername("testperson");

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/mitarbeiter")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$._embedded.mitarbeiterList["+ (testperson.getId() - 1) +"].name").value("Mustermann"));
    }

    @Test
    @WithMockUser(roles = "USER", username="testperson")
    public void testGetOneBuchungen() throws Exception {

        Mitarbeiter testperson = repository.findMitarbeiterByBenutzername("testperson");

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/mitarbeiter/{id}", testperson.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name").value("Mustermann"))
                .andExpect(jsonPath("$.vorname").value("Max"));
    }
}

package de.ehex.workplacemanagment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class BuchungTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void testBuchungenHinzufuegen() throws Exception {
        try {
            this.mockMvc
                    .perform(
                            MockMvcRequestBuilders.post("/api/buchung")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("{\"arbeitsplatzId\": 2,\"datum\": \"2022-04-06\"}")
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    @WithMockUser(roles = "USER", username="menger")
    public void testBuchungHinzufuegen() throws Exception {

        Assertions.assertThrows(NestedServletException.class, () ->
                this.mockMvc
                        .perform(
                                MockMvcRequestBuilders.post("/api/buchung")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content("{\"arbeitsplatzId\": 2,\"datum\": \"2022-04-06\"}")
                        )
                        .andExpect(mvcResult -> Assertions.assertTrue(mvcResult instanceof NestedServletException)));
    }


    @Test
    @WithMockUser(roles = "USER", username="menger")
    public void testBuchungLoeschen() throws Exception {

        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.delete("/api/buchung/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    @WithMockUser(roles = "USER", username="menger")
    public void testGetAllBuchungen() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/buchungen")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$._embedded.buchungList[0].arbeitsplatz.id").value("2"));
    }

    @Test
    @WithMockUser(roles = "USER", username="menger")
    public void testGetOneBuchungen() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/buchung/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.arbeitsplatz.id").value("2"))
                .andExpect(jsonPath("$.mitarbeiter.vorname").value("Josia"));
    }
}

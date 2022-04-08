package de.ehex.workplacemanagment;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.print.attribute.standard.Media;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BuchungTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(roles = "USER", username="menger")
    public void shouldRejectCreatingReviewsWhenUserIsAnonymous() throws Exception {
        this.mockMvc
                .perform(
                        post("/buchungen")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"mitarbeiterId\": 3,\"arbeitsplatzId\": 2,\"datum\": \"2022-04-06\"}")
                                .with(csrf())
                )
                .andExpect(status().isOk());
    }


}

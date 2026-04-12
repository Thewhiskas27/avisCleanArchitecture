package fr.clelia.avis.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.clelia.avis.application.port.in.EditeurUseCase;
import fr.clelia.avis.domain.Editeur;
import fr.clelia.avis.infrastructure.web.dto.WebDTOs;
import fr.clelia.avis.application.service.EditeurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc  // FIX 1: was commented out — MockMvc won't be injected without this
class EditeurRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EditeurService editeurService;  // FIX 2: keep EditeurService (concrete class), not the interface

    @BeforeEach
    void setUp() {
        // FIX 3: recupererEditeurs() now returns List<Editeur>, not List<EditeurDto>
        List<Editeur> editeurs = editeurService.recupererEditeurs();
        editeurs.forEach(editeur -> editeurService.supprimerEditeur(editeur.getId()));
    }

    @Test
    void testerPostEditeur() throws Exception {
        String nomEditeur = "test";
        String nomLogo = "logo";
        // FIX 4: EditeurDto is gone — use AjouterEditeurRequest (the new web DTO)
        WebDTOs.AjouterEditeurRequest request = new WebDTOs.AjouterEditeurRequest(nomEditeur, nomLogo);

        MockHttpServletRequestBuilder requestBuilder = post("/api/editeurs")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value(nomEditeur))
                .andExpect(MockMvcResultMatchers.jsonPath("$.logo").value(nomLogo))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testerPostEditeurShouldReturn409() throws Exception {
        String nomEditeur = "test";
        String logo = "logo";
        WebDTOs.AjouterEditeurRequest request = new WebDTOs.AjouterEditeurRequest(nomEditeur, logo);

        // Add via service using the use case command
        editeurService.ajouterEditeur(
                new EditeurUseCase.AjouterEditeurCommand(nomEditeur, logo)
        );

        MockHttpServletRequestBuilder requestBuilder = post("/api/editeurs")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testGetEditeur() throws Exception {
        String nom = "test";
        String logo = "logo";
        // ajouterEditeur now takes a command, not a domain object
        Editeur e = editeurService.ajouterEditeur(
                new EditeurUseCase.AjouterEditeurCommand(nom, logo)
        );

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/editeurs/{id}", e.getId());

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(e.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value(e.getNom()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.logo").value(e.getLogo()))
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testGetEditeurs() throws Exception {
        editeurService.ajouterEditeur(new EditeurUseCase.AjouterEditeurCommand("testEditeur1", "logoEditeur1"));
        editeurService.ajouterEditeur(new EditeurUseCase.AjouterEditeurCommand("testEditeur2", "logoEditeur2"));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/editeurs");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value("testEditeur1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].logo").value("logoEditeur1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nom").value("testEditeur2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].logo").value("logoEditeur2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

    @Test
    void testDeleteEditeur() throws Exception {
        Editeur e = editeurService.ajouterEditeur(
                new EditeurUseCase.AjouterEditeurCommand("test", "logo")
        );

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/editeurs/{id}", e.getId());

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    @RepeatedTest(100)
    void testDeleteEditeurShouldReturn404() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/editeurs/100");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }
}
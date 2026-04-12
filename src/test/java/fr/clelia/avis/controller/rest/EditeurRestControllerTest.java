package fr.clelia.avis.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.clelia.avis.domain.Editeur;
import fr.clelia.avis.dto.EditeurDto;
import fr.clelia.avis.application.service.EditeurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

@SpringBootTest // Test d'intégration
//@AutoConfigureMockMvc
class EditeurRestControllerTest {

    // cet objet imite ce que fait Postwoman, Postman, Insomnia, Swagger ou le front
    // Angular
    @Autowired
    private MockMvc mockMvc;

    // L'annotation @Autowired n'est plus recommandée ds src/main/java
    // mais elle est tout à fait acceptée dans les classes de test
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EditeurService editeurService;

    @BeforeEach
    void setUp() {
        List<EditeurDto> editeurs = editeurService.recupererEditeurs();
        editeurs.forEach(editeur -> { editeurService.supprimerEditeur(editeur.id()); });
    }

    @Test
    void testerPostEditeur() throws Exception {
    //Mock data
    String nomEditeur = "test";
    String nomLogo = "logo";
    EditeurDto editeurDto = new EditeurDto(null, nomEditeur, nomLogo);
    // On prépare la requête HTTP qui sera envoyée au contrôleur REST
        MockHttpServletRequestBuilder requestBuilder = post("/api/editeurs")
                .content(objectMapper.writeValueAsString(editeurDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        // On demande au mockMvc d'envoyer la requête au back-end
        mockMvc.perform(requestBuilder) //Send request
                .andExpect(status().isCreated())
                // Check results
                .andExpect(MockMvcResultMatchers.jsonPath("$.nom").value(nomEditeur))
                .andExpect(MockMvcResultMatchers.jsonPath("$.logo").value(nomLogo))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testerPostEditeurShouldReturn409() throws Exception {
        //Mock data
        String nomEditeur = "test";
        String logo = "logo";
        EditeurDto editeurDto = new EditeurDto(null, nomEditeur, logo);

        editeurService.ajouterEditeur(editeurDto);

        //Tests
        MockHttpServletRequestBuilder requestBuilder = post("/api/editeurs")
                .content(objectMapper.writeValueAsString(editeurDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder) //Send request
                .andExpect(status().isConflict())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void testGetEditeur() throws Exception {

        String editeur = "test";
        String logo="logo";
        Editeur e = editeurService.ajouterEditeur(new Editeur(editeur, logo));

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
        String nomEditeur1 = "testEditeur1";
        String logoEditeur1 = "logoEditeur1";
        String nomEditeur2 = "testEditeur2";
        String logoEditeur2 = "logoEditeur1";
        EditeurDto editeurDto1 = new EditeurDto(null, nomEditeur1, logoEditeur1);
        EditeurDto editeurDto2 = new EditeurDto(null, nomEditeur2, logoEditeur2);
        editeurService.ajouterEditeur(editeurDto1);
        editeurService.ajouterEditeur(editeurDto2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/editeurs");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value(nomEditeur1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].logo").value(logoEditeur1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nom").value(nomEditeur2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].logo").value(logoEditeur2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));

    }

    @Test
    void testDeleteEditeur() throws Exception {

        String nom = "test";
        String logo = "logo";
        Editeur e = editeurService.ajouterEditeur(new Editeur(nom, logo));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/editeurs/{id}", e.getId());

        mockMvc.perform(requestBuilder)
                //.andExpect(MockMvcResultMatchers.jsonPath("$").value(true))
                //.andExpect(status().is2xxSuccessful())
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

    @RepeatedTest(100)
    void testDeleteEditeurShouldReturn404() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/editeurs/100");

        mockMvc.perform(requestBuilder)
                //.andExpect(MockMvcResultMatchers.jsonPath("$").value(true))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

}
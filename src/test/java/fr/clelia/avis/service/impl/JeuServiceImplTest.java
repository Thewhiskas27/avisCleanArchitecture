package fr.clelia.avis.service.impl;

import fr.clelia.avis.business.Editeur;
import fr.clelia.avis.business.Jeu;
import fr.clelia.avis.business.Plateforme;
import fr.clelia.avis.exception.JeuInexistantException;
import fr.clelia.avis.repository.JeuRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

class JeuServiceImplTest {
    @Mock
    JeuRepository jeuRepository;
    @InjectMocks
    JeuServiceImpl jeuServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRecupererJeu() {
        // Arrange
        Jeu jeu = new Jeu("nom", LocalDate.of(2025, Month.MAY, 19), "description", true, new Editeur("nom", "logo"), List.of(new Plateforme("nom")));
        // Pour imiter le comportement de la repository JeuRepository, si la méthode findById est invoquée
        // On renvoie un objet de type Optional qui embarque l'objet jeu
        when(jeuRepository.findById(any(Long.class))).thenReturn(Optional.of(jeu));

        // Act
        Jeu result = jeuServiceImpl.recupererJeu(Long.valueOf(1));

        // Assert
        Assertions.assertEquals(jeu, result);
    }

    @Test
    void testRecupererJeuInexistantShouldThrowJeuInexistantException() {
        // Arrange
        when(jeuRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(JeuInexistantException.class, () -> jeuServiceImpl.recupererJeu(Long.valueOf(1)));

    }

    @Test
    void testRecupererJeux() {
        when(jeuRepository.findAll(any(Pageable.class))).thenReturn(null);

        Page<Jeu> result = jeuServiceImpl.recupererJeux(null);
        Assertions.assertEquals(null, result);
    }

    @Test
    void testAjouterImage() throws IOException {
        // Arrange
        Jeu expectedJeu = new Jeu("nom", LocalDate.of(2025, Month.MAY, 19), "description", true,
                new Editeur("nom", "logo"), List.of(new Plateforme("nom")));

        when(jeuRepository.save(any(Jeu.class))).thenReturn(expectedJeu);
        when(jeuRepository.findById(any(Long.class))).thenReturn(Optional.of(expectedJeu));

        // Set the nomDossierImages field using reflection
        ReflectionTestUtils.setField(jeuServiceImpl, "nomDossierImages", "src/test/resources/static/images");

        // Create a mock MultipartFile
        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("1.jpg");
        try {
            when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
        } catch (IOException e) {
            fail("Failed to setup mock: " + e.getMessage());
        }

        // Act
        Boolean result = jeuServiceImpl.ajouterImage(Long.valueOf(1), mockFile);

        // Assert
        Assertions.assertEquals(Boolean.TRUE, result);

        // TODO On teste l'existence du fichier 1.jpg

        // On efface le fichier 1.jpg
        Files.deleteIfExists(Paths.get("src/test/resources/static/images/" + "1.jpg"));
    }

    @Test
    void testEnregistrerJeu() {
        // On programme le comportement de l'objet mock
        // (pour rappel : le mock imite le comportement du vrai objet)
        // Notre service ne va pas faire appel à la vraie repository
        Jeu jeu = new Jeu();
        jeu.setDateDeSortie(LocalDate.of(2025, Month.MAY, 19));
        jeu.setEditeur(new Editeur("nom", "logo"));
        jeu.setNom("nom");
        jeu.setPlateformes(List.of(new Plateforme("nom")));
        jeu.setDescription("description");
        jeu.setPossedeImage(true);
        when(jeuRepository.save(any(Jeu.class))).thenReturn(jeu);

        Jeu result = jeuServiceImpl.enregistrerJeu(new Jeu("nom", LocalDate.of(2025, Month.MAY, 19), "description", true, new Editeur("nom", "logo"), List.of(new Plateforme("nom"))));
        Assertions.assertEquals(new Jeu("nom", LocalDate.of(2025, Month.MAY, 19), "description", true, new Editeur("nom", "logo"), List.of(new Plateforme("nom"))), result);
    }

    @Test
    void testAjouterJeu() {
        when(jeuRepository.save(any(Jeu.class))).thenReturn(new Jeu());

        Jeu result = jeuServiceImpl.ajouterJeu(new Jeu("nom", LocalDate.of(2025, Month.MAY, 19), "description", true, new Editeur("nom", "logo"), List.of(new Plateforme("nom"))));
        Assertions.assertEquals(new Jeu("nom", LocalDate.of(2025, Month.MAY, 19), "description", true, new Editeur("nom", "logo"), List.of(new Plateforme("nom"))), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme
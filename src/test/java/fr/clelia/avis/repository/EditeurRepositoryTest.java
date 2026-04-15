package fr.clelia.avis.repository;

import fr.clelia.avis.application.port.out.EditeurRepositoryPort;
import fr.clelia.avis.domain.Editeur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class EditeurRepositoryTest {

    @Autowired
    EditeurRepositoryPort editeurRepository;

    @Test
    void testSave() {
        // Arrange
        Editeur editeur = new Editeur();
        editeur.setNom("test");

        // Act
        Editeur editeurEnregistre = editeurRepository.save(editeur);

        // Assert
        assertEquals(editeur.getNom(), editeurEnregistre.getNom());
    }

    // TODO : tester les méthodes annotées @Query
}

package fr.clelia.avis.application.service;

import fr.clelia.avis.application.port.in.EditeurUseCase;
import fr.clelia.avis.application.port.out.EditeurRepositoryPort;
import fr.clelia.avis.domain.Editeur;
import fr.clelia.avis.domain.exception.EditeurDejaExistantException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EditeurServiceTest {

    @Mock
    private EditeurRepositoryPort repository;

    @InjectMocks
    private EditeurService service;

    @Test
    void shouldAddEditeurSuccessfully() {
        // GIVEN
        var command = new EditeurUseCase.AjouterEditeurCommand("Nintendo", "logo");

        when(repository.existsByNom("Nintendo")).thenReturn(false);
        when(repository.save(any(Editeur.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // WHEN
        Editeur result = service.ajouterEditeur(command);

        // THEN
        assertThat(result.getNom()).isEqualTo("Nintendo");
        assertThat(result.getLogo()).isEqualTo("logo");

        verify(repository).existsByNom("Nintendo");
        verify(repository).save(any(Editeur.class));
    }

    @Test
    void shouldThrowExceptionWhenEditeurAlreadyExists() {
        // GIVEN
        var command = new EditeurUseCase.AjouterEditeurCommand("Nintendo", "logo");

        when(repository.existsByNom("Nintendo")).thenReturn(true);

        // THEN
        assertThatThrownBy(() -> service.ajouterEditeur(command))
                .isInstanceOf(EditeurDejaExistantException.class);

        verify(repository).existsByNom("Nintendo");
        verify(repository, never()).save(any());
    }

    @Test
    void shouldFindEditeurById() {
        // GIVEN
        UUID id = UUID.randomUUID();
        Editeur editeur = new Editeur(id, "Sony", "logo");

        when(repository.findById(id)).thenReturn(Optional.of(editeur));

        // WHEN
        Optional<Editeur> result = service.recupererEditeur(id);

        // THEN
        assertThat(result).isPresent();
        assertThat(result.get().getNom()).isEqualTo("Sony");
    }

    @Test
    void shouldDeleteEditeur() {
        // GIVEN
        UUID id = UUID.randomUUID();

        when(repository.existsById(id)).thenReturn(true);

        // WHEN
        service.supprimerEditeur(id);

        // THEN
        verify(repository).deleteById(id);
    }
}
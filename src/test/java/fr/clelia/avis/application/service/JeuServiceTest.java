package fr.clelia.avis.application.service;

import fr.clelia.avis.application.port.in.EcrireJeuUseCase;
import fr.clelia.avis.application.port.out.JeuRepositoryPort;
import fr.clelia.avis.domain.Jeu;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JeuServiceTest {

    @Mock
    private JeuRepositoryPort jeuRepository;

    @InjectMocks
    private JeuService jeuService;

    @Test
    void ajouterJeu_shouldSaveAndReturnJeu() {
        // given
        var command = new EcrireJeuUseCase.AjouterJeuCommand(
                "Zelda", LocalDate.of(2023, 5, 12), "Un jeu épique", 1L, List.of(1L)
        );
        Jeu savedJeu = new Jeu();
        savedJeu.setId(1L);
        savedJeu.setNom("Zelda");

        when(jeuRepository.save(any())).thenReturn(savedJeu);

        // when
        Jeu result = jeuService.ajouterJeu(command);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getNom()).isEqualTo("Zelda");
        verify(jeuRepository, times(1)).save(any());
    }

    @Test
    void recupererJeu_shouldReturnJeu_whenExists() {
        // given
        Jeu jeu = new Jeu();
        jeu.setId(1L);
        jeu.setNom("Mario");
        when(jeuRepository.findById(1L)).thenReturn(Optional.of(jeu));

        // when
        Jeu result = jeuService.recupererJeu(1L);

        // then
        assertThat(result.getNom()).isEqualTo("Mario");
    }

    @Test
    void recupererJeu_shouldThrow_whenNotFound() {
        // given
        when(jeuRepository.findById(99L)).thenReturn(Optional.empty());

        // when / then
        assertThatThrownBy(() -> jeuService.recupererJeu(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("99");
    }

    @Test
    void supprimerJeu_shouldCallDelete() {
        // given
        Jeu jeu = new Jeu();
        jeu.setId(1L);
        when(jeuRepository.findById(1L)).thenReturn(Optional.of(jeu));

        // when
        jeuService.supprimerJeu(1L);

        // then
        verify(jeuRepository, times(1)).delete(jeu);
    }
}
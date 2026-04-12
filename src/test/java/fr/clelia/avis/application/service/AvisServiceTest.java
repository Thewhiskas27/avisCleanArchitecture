package fr.clelia.avis.application.service;

import fr.clelia.avis.application.port.in.AvisUseCase;
import fr.clelia.avis.application.port.out.AvisRepositoryPort;
import fr.clelia.avis.application.port.out.JeuRepositoryPort;
import fr.clelia.avis.domain.Avis;
import fr.clelia.avis.domain.Jeu;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvisServiceTest {

    @Mock
    private AvisRepositoryPort avisRepository;

    @Mock
    private JeuRepositoryPort jeuRepository;

    @InjectMocks
    private AvisService avisService;

    @Test
    void redigerAvis_shouldSaveAvis_whenJeuExists() {
        // given
        Jeu jeu = new Jeu();
        jeu.setId(1L);
        jeu.setNom("Zelda");

        Avis savedAvis = new Avis();
        savedAvis.setId(1L);
        savedAvis.setNote(18f);
        savedAvis.setJeu(jeu);

        when(jeuRepository.findById(1L)).thenReturn(Optional.of(jeu));
        when(avisRepository.save(any())).thenReturn(savedAvis);

        var command = new AvisUseCase.RedigerAvisCommand("Super jeu !", 18f, 1L, 1L);

        // when
        Avis result = avisService.redigerAvis(command);

        // then
        assertThat(result.getNote()).isEqualTo(18f);
        verify(avisRepository, times(1)).save(any());
    }

    @Test
    void redigerAvis_shouldThrow_whenJeuNotFound() {
        // given
        when(jeuRepository.findById(99L)).thenReturn(Optional.empty());
        var command = new AvisUseCase.RedigerAvisCommand("Super jeu !", 18f, 99L, 1L);

        // when / then
        assertThatThrownBy(() -> avisService.redigerAvis(command))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void approuverAvis_shouldChangeStatutToPublie() {
        // given
        Avis avis = new Avis();
        avis.setId(1L);
        // statut is EN_ATTENTE by default from constructor

        Avis approved = new Avis();
        approved.setId(1L);
        approved.setStatut(Avis.Statut.PUBLIE);

        when(avisRepository.findById(1L)).thenReturn(Optional.of(avis));
        when(avisRepository.save(any())).thenReturn(approved);

        // when
        Avis result = avisService.approuverAvis(1L);

        // then
        assertThat(result.getStatut()).isEqualTo(Avis.Statut.PUBLIE);
    }

    @Test
    void approuverAvis_shouldThrow_whenAvisAlreadyPublie() {
        // given
        Avis avis = new Avis();
        avis.setId(1L);
        avis.setStatut(Avis.Statut.PUBLIE);

        when(avisRepository.findById(1L)).thenReturn(Optional.of(avis));

        // when / then
        assertThatThrownBy(() -> avisService.approuverAvis(1L))
                .isInstanceOf(IllegalStateException.class);
    }
}
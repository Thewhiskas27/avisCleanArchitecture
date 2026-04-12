package fr.clelia.avis.application.port.in;

import fr.clelia.avis.domain.Jeu;

import java.util.List;

// I from SOLID: read operations separated from write operations (see EcrireJeuUseCase)
public interface LireJeuxUseCase {
    Jeu recupererJeu(Long id);
    List<Jeu> recupererJeux();
}
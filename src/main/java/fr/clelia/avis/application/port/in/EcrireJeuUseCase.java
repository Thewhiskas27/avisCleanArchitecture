package fr.clelia.avis.application.port.in;

import fr.clelia.avis.domain.Jeu;

import java.time.LocalDate;
import java.util.List;

// I from SOLID: write operations separated from read operations (see LireJeuxUseCase)
public interface EcrireJeuUseCase {

    Jeu ajouterJeu(AjouterJeuCommand command);

    Jeu mettreAJourJeu(Long id, ModifierJeuCommand command);

    void supprimerJeu(Long id);

    record AjouterJeuCommand(
            String nom,
            LocalDate dateDeSortie,
            String description,
            Long editeurId,
            List<Long> plateformeIds
    ) {}

    record ModifierJeuCommand(
            String nom,
            LocalDate dateDeSortie,
            String description
    ) {}
}
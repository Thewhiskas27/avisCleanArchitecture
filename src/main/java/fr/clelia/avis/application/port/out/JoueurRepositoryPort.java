package fr.clelia.avis.application.port.out;

import fr.clelia.avis.domain.Joueur;

import java.util.Optional;

public interface JoueurRepositoryPort {
    Joueur save(Joueur joueur);
    Optional<Joueur> findByEmail(String email);
    long count();
}

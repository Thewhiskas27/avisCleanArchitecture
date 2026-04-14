package fr.clelia.avis.application.port.out;

import fr.clelia.avis.domain.Jeu;

import java.util.List;
import java.util.Optional;

public interface JeuRepositoryPort {
    Long count();
    Jeu save(Jeu jeu);
    Optional<Jeu> findById(Long id);
    List<Jeu> findAll();
    void delete(Jeu jeu);
}
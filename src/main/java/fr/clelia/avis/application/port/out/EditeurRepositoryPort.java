package fr.clelia.avis.application.port.out;

import fr.clelia.avis.domain.Editeur;

import java.util.List;
import java.util.Optional;

public interface EditeurRepositoryPort {
    long count();
    Editeur save(Editeur editeur);
    boolean existsByNom(String nom);
    boolean existsById(Long id);
    Optional<Editeur> findById(Long id);
    Optional<Editeur> findByNom(String nom);
    List<Editeur> findAll();
    List<Editeur> findByNomContaining(String nom);
    void deleteById(Long id);
}
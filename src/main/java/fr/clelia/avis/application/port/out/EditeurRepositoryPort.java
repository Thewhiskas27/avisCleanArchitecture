package fr.clelia.avis.application.port.out;

import fr.clelia.avis.domain.Editeur;

import java.util.List;
import java.util.Optional;

public interface EditeurRepositoryPort {
    Editeur save(Editeur editeur);
    Optional<Editeur> findById(Long id);
    Optional<Editeur> findByNom(String nom);
    List<Editeur> findAll();
    List<Editeur> findByNomContaining(String nom);
    void deleteById(Long id);
}
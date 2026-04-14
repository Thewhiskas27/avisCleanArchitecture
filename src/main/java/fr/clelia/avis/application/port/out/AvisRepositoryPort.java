package fr.clelia.avis.application.port.out;

import fr.clelia.avis.domain.Avis;

import java.util.List;
import java.util.Optional;

public interface AvisRepositoryPort {
    Long count();
    Avis save(Avis avis);
    Optional<Avis> findById(Long id);
    List<Avis> findAll();
    List<Avis> findByJeuId(Long jeuId);
}
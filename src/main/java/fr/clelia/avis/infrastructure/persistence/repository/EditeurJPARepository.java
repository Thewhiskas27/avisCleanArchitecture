package fr.clelia.avis.infrastructure.persistence.repository;

import fr.clelia.avis.infrastructure.persistence.entity.EditeurJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EditeurJPARepository extends JpaRepository<EditeurJPAEntity, Long> {
    Optional<EditeurJPAEntity> findByNom(String nom);
    List<EditeurJPAEntity> findByNomContaining(String nom);
}
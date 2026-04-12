package fr.clelia.avis.infrastructure.persistence.repository;

import fr.clelia.avis.infrastructure.persistence.entity.AvisJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvisJPARepository extends JpaRepository<AvisJPAEntity, Long> {
    List<AvisJPAEntity> findByJeuId(Long jeuId);
}
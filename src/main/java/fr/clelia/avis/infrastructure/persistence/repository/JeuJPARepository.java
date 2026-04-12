package fr.clelia.avis.infrastructure.persistence.repository;

import fr.clelia.avis.infrastructure.persistence.entity.JeuJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JeuJPARepository extends JpaRepository<JeuJPAEntity, Long> {
}
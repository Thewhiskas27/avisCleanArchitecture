package fr.clelia.avis.infrastructure.persistence.repository;

import fr.clelia.avis.infrastructure.persistence.entity.JoueurJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JoueurJPARepository extends JpaRepository<JoueurJPAEntity, Long> {

    Optional<JoueurJPAEntity> findByEmail(String email);
}

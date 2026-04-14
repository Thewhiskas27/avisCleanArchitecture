package fr.clelia.avis.infrastructure.persistence.adapter;

import fr.clelia.avis.application.port.out.JeuRepositoryPort;
import fr.clelia.avis.domain.Editeur;
import fr.clelia.avis.domain.Jeu;
import fr.clelia.avis.domain.Plateforme;
import fr.clelia.avis.infrastructure.persistence.entity.EditeurJPAEntity;
import fr.clelia.avis.infrastructure.persistence.entity.JeuJPAEntity;
import fr.clelia.avis.infrastructure.persistence.entity.PlateformeJPAEntity;
import fr.clelia.avis.infrastructure.persistence.repository.JeuJPARepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// L from SOLID: this adapter is interchangeable with any other JeuRepositoryPort implementation (e.g. a mock)
@Component
public class JeuPersistenceAdapter implements JeuRepositoryPort {

    private final JeuJPARepository jeuJpaRepository;

    public JeuPersistenceAdapter(JeuJPARepository jeuJpaRepository) {
        this.jeuJpaRepository = jeuJpaRepository;
    }

    @Override
    public Long count() {
        return 0L;
    }

    @Override
    public Jeu save(Jeu jeu) {
        JeuJPAEntity entity = toEntity(jeu);
        JeuJPAEntity saved = jeuJpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Jeu> findById(Long id) {
        return jeuJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Jeu> findAll() {
        return jeuJpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Jeu jeu) {
        jeuJpaRepository.deleteById(jeu.getId());
    }

    // ---- Mapping methods ----

    private Jeu toDomain(JeuJPAEntity entity) {
        Jeu jeu = new Jeu();
        jeu.setId(entity.getId());
        jeu.setNom(entity.getNom());
        jeu.setDateDeSortie(entity.getDateDeSortie());
        jeu.setDescription(entity.getDescription());
        jeu.setPossedeImage(entity.isPossedeImage());
        if (entity.getEditeur() != null) {
            jeu.setEditeur(editeurToDomain(entity.getEditeur()));
        }
        if (entity.getPlateformes() != null) {
            jeu.setPlateformes(entity.getPlateformes().stream()
                    .map(this::plateformeToDomain)
                    .collect(Collectors.toList()));
        }
        return jeu;
    }

    private JeuJPAEntity toEntity(Jeu jeu) {
        JeuJPAEntity entity = new JeuJPAEntity();
        entity.setId(jeu.getId());
        entity.setNom(jeu.getNom());
        entity.setDateDeSortie(jeu.getDateDeSortie());
        entity.setDescription(jeu.getDescription());
        entity.setPossedeImage(jeu.isPossedeImage());
        if (jeu.getEditeur() != null) {
            entity.setEditeur(editeurToEntity(jeu.getEditeur()));
        }
        if (jeu.getPlateformes() != null) {
            entity.setPlateformes(jeu.getPlateformes().stream()
                    .map(this::plateformeToEntity)
                    .collect(Collectors.toList()));
        }
        return entity;
    }

    private Editeur editeurToDomain(EditeurJPAEntity e) {
        Editeur editeur = new Editeur();
        editeur.setId(e.getId());
        editeur.setNom(e.getNom());
        editeur.setLogo(e.getLogo());
        return editeur;
    }

    private @NotNull EditeurJPAEntity editeurToEntity(Editeur e) {
        EditeurJPAEntity entity = new EditeurJPAEntity();
        entity.setId(e.getId());
        entity.setNom(e.getNom());
        entity.setLogo(e.getLogo());
        return entity;
    }

    private Plateforme plateformeToDomain(PlateformeJPAEntity p) {
        Plateforme plateforme = new Plateforme();
        plateforme.setId(p.getId());
        plateforme.setNom(p.getNom());
        return plateforme;
    }

    private PlateformeJPAEntity plateformeToEntity(Plateforme p) {
        PlateformeJPAEntity entity = new PlateformeJPAEntity();
        entity.setId(p.getId());
        entity.setNom(p.getNom());
        return entity;
    }
}
package fr.clelia.avis.infrastructure.persistence.adapter;

import fr.clelia.avis.application.port.out.EditeurRepositoryPort;
import fr.clelia.avis.domain.Editeur;
import fr.clelia.avis.infrastructure.persistence.entity.EditeurJPAEntity;
import fr.clelia.avis.infrastructure.persistence.repository.EditeurJPARepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EditeurPersistenceAdapter implements EditeurRepositoryPort {

    private final EditeurJPARepository editeurJpaRepository;

    public EditeurPersistenceAdapter(EditeurJPARepository editeurJpaRepository) {
        this.editeurJpaRepository = editeurJpaRepository;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public Editeur save(Editeur editeur) {
        return toDomain(editeurJpaRepository.save(toEntity(editeur)));
    }

    @Override
    public boolean existsByNom(String nom) {
        return false;
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public Optional<Editeur> findById(Long id) {
        return editeurJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<Editeur> findByNom(String nom) {
        return editeurJpaRepository.findByNom(nom).map(this::toDomain);
    }

    @Override
    public List<Editeur> findAll() {
        return editeurJpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Editeur> findByNomContaining(String nom) {
        return editeurJpaRepository.findByNomContaining(nom).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        editeurJpaRepository.deleteById(id);
    }

    private Editeur toDomain(EditeurJPAEntity entity) {
        Editeur editeur = new Editeur();
        editeur.setId(entity.getId());
        editeur.setNom(entity.getNom());
        editeur.setLogo(entity.getLogo());
        return editeur;
    }

    private EditeurJPAEntity toEntity(Editeur editeur) {
        EditeurJPAEntity entity = new EditeurJPAEntity();
        entity.setId(editeur.getId());
        entity.setNom(editeur.getNom());
        entity.setLogo(editeur.getLogo());
        return entity;
    }
}
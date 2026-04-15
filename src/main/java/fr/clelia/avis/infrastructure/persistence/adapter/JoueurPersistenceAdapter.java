package fr.clelia.avis.infrastructure.persistence.adapter;

import fr.clelia.avis.application.port.out.JoueurRepositoryPort;
import fr.clelia.avis.domain.Joueur;
import fr.clelia.avis.infrastructure.persistence.entity.JoueurJPAEntity;
import fr.clelia.avis.infrastructure.persistence.repository.JoueurJPARepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JoueurPersistenceAdapter implements JoueurRepositoryPort {

    private final JoueurJPARepository repository;

    public JoueurPersistenceAdapter(JoueurJPARepository repository) {
        this.repository = repository;
    }

    @Override
    public Joueur save(Joueur joueur) {
        JoueurJPAEntity entity = toEntity(joueur);
        JoueurJPAEntity saved = repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Joueur> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(this::toDomain);
    }

    @Override
    public long count() {
        return repository.count();
    }

    // -----------------------
    // Mapping
    // -----------------------

    private JoueurJPAEntity toEntity(Joueur joueur) {
        JoueurJPAEntity entity = new JoueurJPAEntity();
        entity.setId(joueur.getId());
        entity.setPseudo(joueur.getPseudo());
        entity.setEmail(joueur.getEmail());
        entity.setMotDePasse(joueur.getMotDePasse());
        entity.setDateDeNaissance(joueur.getDateDeNaissance());
        return entity;
    }

    private Joueur toDomain(JoueurJPAEntity entity) {
        Joueur joueur = new Joueur();
        joueur.setId(entity.getId());
        joueur.setPseudo(entity.getPseudo());
        joueur.setEmail(entity.getEmail());
        joueur.setMotDePasse(entity.getMotDePasse());
        joueur.setDateDeNaissance(entity.getDateDeNaissance());
        return joueur;
    }
}
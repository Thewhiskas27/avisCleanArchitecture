package fr.clelia.avis.infrastructure.persistence.adapter;

import fr.clelia.avis.application.port.out.AvisRepositoryPort;
import fr.clelia.avis.domain.Avis;
import fr.clelia.avis.domain.Jeu;
import fr.clelia.avis.domain.Joueur;
import fr.clelia.avis.infrastructure.persistence.entity.AvisJPAEntity;
import fr.clelia.avis.infrastructure.persistence.entity.JeuJPAEntity;
import fr.clelia.avis.infrastructure.persistence.entity.JoueurJPAEntity;
import fr.clelia.avis.infrastructure.persistence.repository.AvisJPARepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AvisPersistenceAdapter implements AvisRepositoryPort {

    private final AvisJPARepository avisJpaRepository;

    public AvisPersistenceAdapter(AvisJPARepository avisJpaRepository) {
        this.avisJpaRepository = avisJpaRepository;
    }

    @Override
    public Avis save(Avis avis) {
        return toDomain(avisJpaRepository.save(toEntity(avis)));
    }

    @Override
    public Optional<Avis> findById(Long id) {
        return avisJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Avis> findAll() {
        return avisJpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Avis> findByJeuId(Long jeuId) {
        return avisJpaRepository.findByJeuId(jeuId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private Avis toDomain(AvisJPAEntity entity) {
        Avis avis = new Avis();
        avis.setId(entity.getId());
        avis.setContenu(entity.getContenu());
        avis.setNote(entity.getNote());
        avis.setDateDeCreation(entity.getDateDeCreation());
        avis.setStatut(entity.getStatut());

        if (entity.getJeu() != null) {
            Jeu jeu = new Jeu();
            jeu.setId(entity.getJeu().getId());
            jeu.setNom(entity.getJeu().getNom());
            avis.setJeu(jeu);
        }
        if (entity.getJoueur() != null) {
            Joueur joueur = new Joueur();
            joueur.setId(entity.getJoueur().getId());
            joueur.setPseudo(entity.getJoueur().getPseudo());
            avis.setJoueur(joueur);
        }
        return avis;
    }

    private AvisJPAEntity toEntity(Avis avis) {
        AvisJPAEntity entity = new AvisJPAEntity();
        entity.setId(avis.getId());
        entity.setContenu(avis.getContenu());
        entity.setNote(avis.getNote());
        entity.setDateDeCreation(avis.getDateDeCreation());
        entity.setStatut(avis.getStatut());

        if (avis.getJeu() != null) {
            JeuJPAEntity jeuEntity = new JeuJPAEntity();
            jeuEntity.setId(avis.getJeu().getId());
            entity.setJeu(jeuEntity);
        }
        if (avis.getJoueur() != null) {
            JoueurJPAEntity joueurEntity = new JoueurJPAEntity();
            joueurEntity.setId(avis.getJoueur().getId());
            entity.setJoueur(joueurEntity);
        }
        return entity;
    }
}
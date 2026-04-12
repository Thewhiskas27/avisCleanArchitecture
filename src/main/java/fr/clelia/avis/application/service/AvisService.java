package fr.clelia.avis.application.service;

import fr.clelia.avis.application.port.in.AvisUseCase;
import fr.clelia.avis.application.port.out.AvisRepositoryPort;
import fr.clelia.avis.application.port.out.JeuRepositoryPort;
import fr.clelia.avis.domain.Avis;
import fr.clelia.avis.domain.Jeu;

import java.util.List;

public class AvisService implements AvisUseCase {

    private final AvisRepositoryPort avisRepository;
    private final JeuRepositoryPort jeuRepository;

    public AvisService(AvisRepositoryPort avisRepository, JeuRepositoryPort jeuRepository) {
        this.avisRepository = avisRepository;
        this.jeuRepository = jeuRepository;
    }

    @Override
    public Avis redigerAvis(RedigerAvisCommand command) {
        Jeu jeu = jeuRepository.findById(command.jeuId())
                .orElseThrow(() -> new RuntimeException("Jeu introuvable : " + command.jeuId()));
        Avis avis = new Avis();
        avis.setContenu(command.contenu());
        avis.setNote(command.note());
        avis.setJeu(jeu);
        return avisRepository.save(avis);
    }

    @Override
    public Avis approuverAvis(Long id) {
        Avis avis = findAvis(id);
        avis.approuver();
        return avisRepository.save(avis);
    }

    @Override
    public Avis rejeterAvis(Long id) {
        Avis avis = findAvis(id);
        avis.rejeter();
        return avisRepository.save(avis);
    }

    @Override
    public Avis signalerAvis(Long id) {
        Avis avis = findAvis(id);
        avis.signaler();
        return avisRepository.save(avis);
    }

    @Override
    public List<Avis> recupererAvis() {
        return avisRepository.findAll();
    }

    @Override
    public List<Avis> recupererAvisParJeu(Long jeuId) {
        return avisRepository.findByJeuId(jeuId);
    }

    private Avis findAvis(Long id) {
        return avisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avis introuvable : " + id));
    }
}
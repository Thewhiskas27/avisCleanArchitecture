package fr.clelia.avis.application.service;

import fr.clelia.avis.application.port.in.EcrireJeuUseCase;
import fr.clelia.avis.application.port.in.LireJeuxUseCase;
import fr.clelia.avis.application.port.out.JeuRepositoryPort;
import fr.clelia.avis.domain.Jeu;

import java.util.List;

// S from SOLID: this class only handles Jeu business logic (image upload is in a separate service)
// D from SOLID: depends on JeuRepositoryPort (interface), not on any JPA class
public class JeuService implements EcrireJeuUseCase, LireJeuxUseCase {

    private final JeuRepositoryPort jeuRepository;

    public JeuService(JeuRepositoryPort jeuRepository) {
        this.jeuRepository = jeuRepository;
    }

    @Override
    public Jeu ajouterJeu(AjouterJeuCommand command) {
        Jeu jeu = new Jeu();
        jeu.setNom(command.nom());
        jeu.setDateDeSortie(command.dateDeSortie());
        jeu.setDescription(command.description());
        return jeuRepository.save(jeu);
    }

    @Override
    public Jeu mettreAJourJeu(Long id, ModifierJeuCommand command) {
        Jeu jeu = recupererJeu(id);
        if (command.nom() != null) jeu.setNom(command.nom());
        if (command.dateDeSortie() != null) jeu.setDateDeSortie(command.dateDeSortie());
        if (command.description() != null) jeu.setDescription(command.description());
        return jeuRepository.save(jeu);
    }

    @Override
    public void supprimerJeu(Long id) {
        Jeu jeu = recupererJeu(id);
        jeuRepository.delete(jeu);
    }

    @Override
    public Jeu recupererJeu(Long id) {
        return jeuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jeu introuvable : " + id));
    }

    @Override
    public List<Jeu> recupererJeux() {
        return jeuRepository.findAll();
    }
}
package fr.clelia.avis.initialisation;

import fr.clelia.avis.application.port.out.AvisRepositoryPort;
import fr.clelia.avis.application.port.out.EditeurRepositoryPort;
import fr.clelia.avis.application.port.out.JeuRepositoryPort;
import fr.clelia.avis.application.port.out.JoueurRepositoryPort;
import fr.clelia.avis.domain.Avis;
import fr.clelia.avis.domain.Editeur;
import fr.clelia.avis.domain.Jeu;
import fr.clelia.avis.domain.Joueur;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@AllArgsConstructor
@Profile({"dev", "prod"})
@Transactional
public class AjoutDonneesInitiales {

    private final EditeurRepositoryPort editeurRepository;
    private final JeuRepositoryPort jeuRepository;
    private final JoueurRepositoryPort joueurRepository;
    private final AvisRepositoryPort avisRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        ajouterEditeurs();
        ajouterJeux();
        ajouterAvis();
    }

    // ------------------------
    // Editeurs
    // ------------------------

    private void ajouterEditeurs() {
        if (editeurRepository.count() > 0) return;

        editeurRepository.save(new Editeur("Nintendo", "nintendo.com"));
        editeurRepository.save(new Editeur("Ubisoft", "ubisoft.com"));
        editeurRepository.save(new Editeur("Psyonix", "psyonix.com"));
    }

    // ------------------------
    // Jeux
    // ------------------------

    private void ajouterJeux() {
        if (jeuRepository.count() > 0) return;

        Editeur nintendo = editeurRepository.findByNom("Nintendo").orElseThrow();
        Editeur ubisoft = editeurRepository.findByNom("Ubisoft").orElseThrow();

        Jeu zelda = new Jeu();
        zelda.setNom("The Legend of Zelda: Breath of the Wild");
        zelda.setDateDeSortie(LocalDate.of(2017, 3, 3));
        zelda.setDescription("Jeu d'action-aventure");
        zelda.setPossedeImage(true);
        zelda.setEditeur(nintendo);

        Jeu assassinsCreed = new Jeu();
        assassinsCreed.setNom("Assassin's Creed Black Flag");
        assassinsCreed.setDateDeSortie(LocalDate.of(2013, 10, 29));
        assassinsCreed.setDescription("Action-aventure en monde ouvert");
        assassinsCreed.setPossedeImage(true);
        assassinsCreed.setEditeur(ubisoft);

        jeuRepository.save(zelda);
        jeuRepository.save(assassinsCreed);
    }

    // ------------------------
    // Avis
    // ------------------------

    private void ajouterAvis() {
        if (avisRepository.count() > 0) return;

        Jeu jeu = jeuRepository.findAll().get(0);
        Joueur joueur = creerOuRecupererJoueurParDefaut();

        Avis avis = new Avis();
        avis.setContenu("Excellent jeu, immersion incroyable");
        avis.setNote(9);
        avis.setJeu(jeu);
        avis.setJoueur(joueur); // ✅ REQUIRED by UML & JPA

        avisRepository.save(avis);
    }

    // ------------------------
    // Joueur par défaut
    // ------------------------

    private Joueur creerOuRecupererJoueurParDefaut() {

        return joueurRepository.findByEmail("demo@esgi.fr")
                .orElseGet(() -> {
                    Joueur joueur = new Joueur();
                    joueur.setPseudo("joueur-demo");
                    joueur.setEmail("demo@esgi.fr");
                    joueur.setMotDePasse("password");
                    joueur.setDateDeNaissance(LocalDate.of(2000, 1, 1));
                    return joueurRepository.save(joueur);
                });
    }
}
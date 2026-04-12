package fr.clelia.avis.initialisation;

import fr.clelia.avis.business.*;
import fr.clelia.avis.repository.*;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Component
@AllArgsConstructor
@Profile({"dev", "prod"})
@Transactional
public class AjoutDonneesInitiales {

    private EditeurRepository  editeurRepository;
    private JoueurRepository joueurRepository;
    private ModerateurRepository moderateurRepository;
    private PlateformeRepository plateformeRepository;
    private JeuRepository jeuRepository;

    // Le fait de déclarer l'attribut en static va dispenser Spring de gérer l'objet
    private static Faker faker = new Faker(Locale.FRENCH);

   // @Override
   @EventListener(ApplicationReadyEvent.class)
   // public void run(String... args) throws Exception {
   public void init() {
       ajouterEditeurs();
       ajouterJoueurs(100);
       ajouterModerateur();
       ajouterPlateformes();
       ajouterJeux();
    }

    private void ajouterJeux() {
        if (jeuRepository.count() == 0) {
            jeuRepository.save(new Jeu(
                    "Rocket League",
                    LocalDate.of(2015, 7, 3),
                    "Jeu de foot avec des voitures",
                    true,
                    editeurRepository.findByNom("Psyonix"),
                    List.of(plateformeRepository.findByNom("Windows"))
            ));

            Editeur editeur = editeurRepository.findByNom("Ubisoft");
            List<Plateforme> plateformes = plateformeRepository.findAll();

            Jeu jeu = new Jeu("Ghost Recon: Wildlands", LocalDate.of(2017, 3, 17), editeur, plateformes);
            jeuRepository.save(jeu);

            jeuRepository.save(
                    new Jeu(
                            "assassin's creed black flag",
                            LocalDate.of(2013, 10, 29),
                            editeurRepository.findByNom("Ubisoft"),
                            List.of(
                                    plateformeRepository.findByNom("Windows"),
                                    plateformeRepository.findByNom("PlayStation 4"),
                                    plateformeRepository.findByNom("Xbox One"),
                                    plateformeRepository.findByNom("Nintendo Switch")
                            ),
                            "Un jeu d'action-aventure en monde ouvert développé par Ubisoft.",
                            false));

            LocalDate dateDeSortie = LocalDate.of(2019, 5, 28);
            Editeur mobius = editeurRepository.findByNom("Mobius Digital");
            List<Plateforme> plateformes2 = List.of(
                    plateformeRepository.findByNom("PC-Engine"),
                    plateformeRepository.findByNom("Xbox One"),
                    plateformeRepository.findByNom("PlayStation 4"),
                    plateformeRepository.findByNom("Nintendo Switch")
            );

            jeuRepository.save(new Jeu("Outer Wilds", dateDeSortie, mobius, plateformes2));

            List<Plateforme> plateformes3 = plateformeRepository.findAll();
            List<Editeur> editeurs = editeurRepository.findAll();
            jeuRepository.save(new Jeu("Overwatch", LocalDate.of(2016, 5, 24), "Un jeu de tir en équipe", true, editeurs.get(9), plateformes3.subList(4, 6)));
            jeuRepository.save(new Jeu("Super Mario Odyssey", LocalDate.of(2017, 10, 27), "Un jeu de plateforme en 3D", true, editeurs.get(1), plateformes3.subList(3, 4)));
            jeuRepository.save(new Jeu("The Witcher 3: Wild Hunt", LocalDate.of(2015, 5, 19), "Un RPG en monde ouvert", true, editeurs.get(2), plateformes3.subList(4, 6)));
            jeuRepository.save(new Jeu("Red Dead Redemption 2", LocalDate.of(2018, 10, 26), "Un jeu d'action-aventure en monde ouvert", true, editeurs.get(3), plateformes3.subList(4, 7)));
            jeuRepository.save(new Jeu("Minecraft", LocalDate.of(2011, 11, 18), "Un jeu de construction et d'aventure", true, editeurs.get(4), plateformes3.subList(4, 8)));
            jeuRepository.save(new Jeu("Cyberpunk 2077", LocalDate.of(2020, 12, 10), "Un RPG futuriste en monde ouvert", true, editeurs.get(5), plateformes3.subList(4, 6)));
            jeuRepository.save(new Jeu("God of War", LocalDate.of(2018, 4, 20), "Un jeu d'action-aventure mythologique", true, editeurs.get(6), plateformes3.subList(9, 10)));
            jeuRepository.save(new Jeu("Hollow Knight", LocalDate.of(2017, 2, 24), "Un jeu de plateforme et d'exploration", true, editeurs.get(7), plateformes3.subList(3, 5)));
            jeuRepository.save(new Jeu("Stardew Valley", LocalDate.of(2016, 2, 26), "Un jeu de simulation de ferme", true, editeurs.get(8), plateformes3.subList(4, 7)));

            jeuRepository.save(new Jeu("ZELDA : TOTK", LocalDate.of(2023, 5, 12), "Visiter le ciel, la terre et les souterrains", false, editeurRepository.findByNom("Nintendo"), List.of(plateformeRepository.findByNom("Nintendo Switch"))));
            jeuRepository.save(new Jeu(
                    "The Legend of Zelda: Breath of the Wild",
                    LocalDate.of(2017, 3, 3),
                    "Jeu d'action-aventure",
                    true,
                    editeurRepository.findByNom("Nintendo"),
                    List.of(plateformeRepository.findByNom("Nintendo Switch"))
            ));
        }
    }

    private void ajouterEditeurs() {
        if (editeurRepository.count() == 0) {
            editeurRepository.save(new Editeur("Mobius Digital", "mobiusdigitalgames.com"));
            editeurRepository.save(new Editeur("Activision", "activision.com"));
            editeurRepository.save(new Editeur("Amazon Games","amazongames.com"));
            editeurRepository.save(new Editeur("Ankama", "ankama.com"));
            editeurRepository.save(new Editeur("Psyonix", "psyonix.com"));
            editeurRepository.save(new Editeur("Bandai Namco", "bandai.co.jp"));
            editeurRepository.save(new Editeur("Bethesda", "bethesda.net"));
            editeurRepository.save(new Editeur("BioWare", "bioware.com"));
            editeurRepository.save(new Editeur("Blizzard", "blizzard.com"));
            editeurRepository.save(new Editeur("Capcom", "capcom.com"));
            editeurRepository.save(new Editeur("CCP", "ccpgames.com"));
            editeurRepository.save(new Editeur("CD Projekt Red", "cdprojekt.com"));
            editeurRepository.save(new Editeur("Davilex", "davilex.nl"));
            editeurRepository.save(new Editeur("Digital Extreme", "digitalextremes.com"));
            editeurRepository.save(new Editeur("Electronic Arts", "ea.com"));
            editeurRepository.save(new Editeur("Epic Games", "epicgames.com"));
            editeurRepository.save(new Editeur("FromSoftware", "fromsoftware.jp"));
            editeurRepository.save(new Editeur("Hazelight Studios", "hazelight.se"));
            editeurRepository.save(new Editeur("idSoftware", "idsoftware.com"));
            editeurRepository.save(new Editeur("Microsoft", "microsoft.com"));
            editeurRepository.save(new Editeur("MonolithSoftware", "monolithsoft.co.jp"));
            editeurRepository.save(new Editeur("Naughty Dog", "naughtydog.com"));
            editeurRepository.save(new Editeur("Nintendo", "nintendo.com"));
            editeurRepository.save(new Editeur("Riot Games", "riotgames.com"));
            editeurRepository.save(new Editeur("Rockstar", "rockstar.com"));
            editeurRepository.save(new Editeur("Sega", "sega.com"));
            editeurRepository.save(new Editeur("Square Enix", "squareenix.com"));
            editeurRepository.save(new Editeur("Tencent", "tencentgames.com"));
            editeurRepository.save(new Editeur("Ubisoft", "ubisoft.com"));
            editeurRepository.save(new Editeur("Ultra Software", null));
            editeurRepository.save(new Editeur("Valve", "valvesoftware.com"));
            editeurRepository.save(new Editeur("Wildcard", "wildcardmobile.com"));
        }
    }

    private void ajouterJoueurs(int nbJoueursAAjouter) {
        if (joueurRepository.count() == 0) {
            Random random = new Random();
            Calendar calendar = Calendar.getInstance();
            Map<String, Joueur> map = new HashMap<>();

            while (map.size() != nbJoueursAAjouter) {

                calendar.set(1940, 1, 1);
                Date dateDebut = calendar.getTime();
                calendar = Calendar.getInstance();
                calendar.set(2003, 1, 1);
                Date dateFin = calendar.getTime();
                Date dateAleatoire = faker.date().between(dateDebut, dateFin);
                calendar.setTime(dateAleatoire);
                LocalDate dateDeNaissance = dateAleatoire.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                String prenom = faker.name().firstName();
                String email = faker.internet().emailAddress().split("@")[0]+"@esgi.fr";

    Joueur joueur = Joueur.builder().pseudo(prenom + String.valueOf(random.nextInt(999) + 1000))
            .email(email).motDePasse(String.valueOf(random.nextInt(99999999) + 10000000))
            .dateDeNaissance(dateDeNaissance).build();

                map.put(joueur.getEmail(), joueur);

}
            System.out.println(map.values());
            joueurRepository.saveAll(map.values());
        joueurRepository.save(Joueur.builder().pseudo("Rayane").motDePasse("anniversaire")
                    .dateDeNaissance(LocalDate.of(2000, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth())).build());
        }

        }

    private void ajouterModerateur() {
        if (moderateurRepository.count()==0) {
            moderateurRepository.save(new Moderateur("Peppe", "azerty", "peppe@esgi.fr", "+39123456789"));
        }
    }

    private void ajouterPlateformes() {
        if (plateformeRepository.count() == 0) {
            plateformeRepository.save(new Plateforme("Amstrad CPC"));
            plateformeRepository.save(new Plateforme("Nintendo Wii"));
            plateformeRepository.save(new Plateforme("Nintendo Wii U"));
            plateformeRepository.save(new Plateforme("Nintendo Switch"));
            plateformeRepository.save(new Plateforme("Windows"));
            plateformeRepository.save(new Plateforme("MacOS"));
            plateformeRepository.save(new Plateforme("Steam"));
            plateformeRepository.save(new Plateforme("Neo-Geo"));
            plateformeRepository.save(new Plateforme("PlayStation 1"));
            plateformeRepository.save(new Plateforme("PlayStation 2"));
            plateformeRepository.save(new Plateforme("PlayStation 3"));
            plateformeRepository.save(new Plateforme("PlayStation 4"));
            plateformeRepository.save(new Plateforme("PlayStation 5"));
            plateformeRepository.save(new Plateforme("PlayStation Vita"));
            plateformeRepository.save(new Plateforme("PSP"));
            plateformeRepository.save(new Plateforme("Sega Dreamcast"));
            plateformeRepository.save(new Plateforme("Sega Mastersystem"));
            plateformeRepository.save(new Plateforme("Sega Saturn"));
            plateformeRepository.save(new Plateforme("Xbox One"));
            plateformeRepository.save(new Plateforme("Xbox One Series"));
            plateformeRepository.save(new Plateforme("Xbox 360"));
            plateformeRepository.save(new Plateforme("Amiga"));
            plateformeRepository.save(new Plateforme("Android"));
            plateformeRepository.save(new Plateforme("Atari 8-bit"));
            plateformeRepository.save(new Plateforme("Atari Jaguar"));
            plateformeRepository.save(new Plateforme("Commodore 64"));
            plateformeRepository.save(new Plateforme("Game Boy"));
            plateformeRepository.save(new Plateforme("Game Boy Color"));
            plateformeRepository.save(new Plateforme("Game Boy Advance"));
            plateformeRepository.save(new Plateforme("Game Boy Advance SP"));
            plateformeRepository.save(new Plateforme("NES"));
            plateformeRepository.save(new Plateforme("PC-Engine"));
            plateformeRepository.save(new Plateforme("SNES"));
            plateformeRepository.save(new Plateforme("Nintendo 3DS"));
            plateformeRepository.save(new Plateforme("Nintendo 64"));
            plateformeRepository.save(new Plateforme("Nintendo DS"));
            plateformeRepository.save(new Plateforme("Nintendo Gamecube"));
        }
    }

}

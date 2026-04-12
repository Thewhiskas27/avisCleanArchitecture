package fr.clelia.avis.application.port.in;

import fr.clelia.avis.domain.Editeur;

import java.util.List;

public interface EditeurUseCase {
    Editeur ajouterEditeur(AjouterEditeurCommand command);
    Editeur recupererEditeur(Long id);
    List<Editeur> recupererEditeurs();
    List<Editeur> recupererEditeursParNomContenant(String nom);
    Editeur mettreAJourEditeur(Long id, AjouterEditeurCommand command);
    void supprimerEditeur(Long id);

    record AjouterEditeurCommand(String nom, String logo) {}
}
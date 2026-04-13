package fr.clelia.avis.application.service;

import fr.clelia.avis.application.port.in.EditeurUseCase;
import fr.clelia.avis.application.port.out.EditeurRepositoryPort;
import fr.clelia.avis.domain.Editeur;
import fr.clelia.avis.domain.exception.EditeurDejaExistantException;
import fr.clelia.avis.domain.exception.EditeurNotFoundException;

import java.util.List;

public class EditeurService implements EditeurUseCase {

    private final EditeurRepositoryPort editeurRepository;

    public EditeurService(EditeurRepositoryPort editeurRepository) {
        this.editeurRepository = editeurRepository;
    }

    @Override
    public Editeur ajouterEditeur(AjouterEditeurCommand command) {
        editeurRepository.findByNom(command.nom()).ifPresent(e -> {
            throw new EditeurDejaExistantException(command.nom());
        });
        Editeur editeur = new Editeur(command.nom(), command.logo());
        return editeurRepository.save(editeur);
    }

    @Override
    public Editeur recupererEditeur(Long id) {
        return editeurRepository.findById(id)
                .orElseThrow(() -> new EditeurNotFoundException(id));
    }

    @Override
    public List<Editeur> recupererEditeurs() {
        return editeurRepository.findAll();
    }

    @Override
    public List<Editeur> recupererEditeursParNomContenant(String nom) {
        return editeurRepository.findByNomContaining(nom);
    }

    @Override
    public Editeur mettreAJourEditeur(Long id, AjouterEditeurCommand command) {
        Editeur editeur = recupererEditeur(id);
        editeur.setNom(command.nom());
        editeur.setLogo(command.logo());
        return editeurRepository.save(editeur);
    }

    @Override
    public void supprimerEditeur(Long id) {
        recupererEditeur(id); // throws if not found
        editeurRepository.deleteById(id);
    }
}
package fr.clelia.avis.infrastructure.web.controller;

import fr.clelia.avis.application.port.in.EcrireJeuUseCase;
import fr.clelia.avis.application.port.in.LireJeuxUseCase;
import fr.clelia.avis.domain.Jeu;
import fr.clelia.avis.infrastructure.web.dto.WebDTOs.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/jeux")
public class JeuRestController {

    // D from SOLID: depends on use case interfaces, not on service implementations
    private final EcrireJeuUseCase ecrireJeuUseCase;
    private final LireJeuxUseCase lireJeuxUseCase;

    public JeuRestController(EcrireJeuUseCase ecrireJeuUseCase, LireJeuxUseCase lireJeuxUseCase) {
        this.ecrireJeuUseCase = ecrireJeuUseCase;
        this.lireJeuxUseCase = lireJeuxUseCase;
    }

    @GetMapping
    @Operation(summary = "Récupère tous les jeux")
    public List<JeuResponse> getJeux() {
        return lireJeuxUseCase.recupererJeux().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupère un jeu par son id")
    public JeuResponse getJeu(@PathVariable Long id) {
        return toResponse(lireJeuxUseCase.recupererJeu(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Ajoute un nouveau jeu")
    public JeuResponse postJeu(@Valid @RequestBody AjouterJeuRequest request) {
        var command = new EcrireJeuUseCase.AjouterJeuCommand(
                request.nom(),
                request.dateDeSortie(),
                request.description(),
                request.editeurId(),
                request.plateformeIds()
        );
        return toResponse(ecrireJeuUseCase.ajouterJeu(command));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Met à jour partiellement un jeu")
    public JeuResponse patchJeu(@PathVariable Long id, @RequestBody ModifierJeuRequest request) {
        var command = new EcrireJeuUseCase.ModifierJeuCommand(
                request.nom(),
                request.dateDeSortie(),
                request.description()
        );
        return toResponse(ecrireJeuUseCase.mettreAJourJeu(id, command));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprime un jeu")
    public void deleteJeu(@PathVariable Long id) {
        ecrireJeuUseCase.supprimerJeu(id);
    }

    private JeuResponse toResponse(Jeu jeu) {
        EditeurResponse editeurResponse = null;
        if (jeu.getEditeur() != null) {
            editeurResponse = new EditeurResponse(
                    jeu.getEditeur().getId(),
                    jeu.getEditeur().getNom(),
                    jeu.getEditeur().getLogo()
            );
        }
        return new JeuResponse(
                jeu.getId(),
                jeu.getNom(),
                jeu.getDateDeSortie(),
                jeu.getDescription(),
                jeu.isPossedeImage(),
                editeurResponse
        );
    }
}
package fr.clelia.avis.infrastructure.web.controller;

import fr.clelia.avis.application.port.in.EditeurUseCase;
import fr.clelia.avis.domain.Editeur;
import fr.clelia.avis.infrastructure.web.dto.WebDTOs.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/editeurs")
public class EditeurRestController {

    private final EditeurUseCase editeurUseCase;

    public EditeurRestController(EditeurUseCase editeurUseCase) {
        this.editeurUseCase = editeurUseCase;
    }

    @GetMapping
    @Operation(summary = "Liste tous les éditeurs")
    public List<EditeurResponse> getEditeurs() {
        return editeurUseCase.recupererEditeurs().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupère un éditeur par son id")
    public EditeurResponse getEditeur(@PathVariable Long id) {
        return toResponse(editeurUseCase.recupererEditeur(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Ajoute un éditeur")
    public EditeurResponse postEditeur(@Valid @RequestBody AjouterEditeurRequest request) {
        var command = new EditeurUseCase.AjouterEditeurCommand(request.nom(), request.logo());
        return toResponse(editeurUseCase.ajouterEditeur(command));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Met à jour un éditeur")
    public void putEditeur(@PathVariable Long id, @RequestBody AjouterEditeurRequest request) {
        var command = new EditeurUseCase.AjouterEditeurCommand(request.nom(), request.logo());
        editeurUseCase.mettreAJourEditeur(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprime un éditeur")
    public void deleteEditeur(@PathVariable Long id) {
        editeurUseCase.supprimerEditeur(id);
    }

    private EditeurResponse toResponse(Editeur editeur) {
        return new EditeurResponse(editeur.getId(), editeur.getNom(), editeur.getLogo());
    }
}
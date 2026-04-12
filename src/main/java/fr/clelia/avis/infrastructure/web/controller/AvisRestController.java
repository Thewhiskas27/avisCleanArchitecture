package fr.clelia.avis.infrastructure.web.controller;

import fr.clelia.avis.application.port.in.AvisUseCase;
import fr.clelia.avis.domain.Avis;
import fr.clelia.avis.infrastructure.web.dto.WebDTOs.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/avis")
public class AvisRestController {

    private final AvisUseCase avisUseCase;

    public AvisRestController(AvisUseCase avisUseCase) {
        this.avisUseCase = avisUseCase;
    }

    @GetMapping
    @Operation(summary = "Récupère tous les avis")
    public List<AvisResponse> getAvis() {
        return avisUseCase.recupererAvis().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/jeu/{jeuId}")
    @Operation(summary = "Récupère les avis d'un jeu")
    public List<AvisResponse> getAvisParJeu(@PathVariable Long jeuId) {
        return avisUseCase.recupererAvisParJeu(jeuId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Rédige un avis")
    public AvisResponse postAvis(@RequestBody RedigerAvisRequest request) {
        var command = new AvisUseCase.RedigerAvisCommand(
                request.contenu(),
                request.note(),
                request.jeuId(),
                request.joueurId()
        );
        return toResponse(avisUseCase.redigerAvis(command));
    }

    @PatchMapping("/{id}/approuver")
    @Operation(summary = "Approuve un avis (modérateur)")
    public AvisResponse approuver(@PathVariable Long id) {
        return toResponse(avisUseCase.approuverAvis(id));
    }

    @PatchMapping("/{id}/rejeter")
    @Operation(summary = "Rejette un avis (modérateur)")
    public AvisResponse rejeter(@PathVariable Long id) {
        return toResponse(avisUseCase.rejeterAvis(id));
    }

    @PatchMapping("/{id}/signaler")
    @Operation(summary = "Signale un avis (joueur)")
    public AvisResponse signaler(@PathVariable Long id) {
        return toResponse(avisUseCase.signalerAvis(id));
    }

    private AvisResponse toResponse(Avis avis) {
        return new AvisResponse(
                avis.getId(),
                avis.getContenu(),
                avis.getNote(),
                avis.getStatut() != null ? avis.getStatut().name() : null,
                avis.getJeu() != null ? avis.getJeu().getId() : null,
                avis.getJeu() != null ? avis.getJeu().getNom() : null
        );
    }
}
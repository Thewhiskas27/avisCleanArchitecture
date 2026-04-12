package fr.clelia.avis.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

// ---- Request DTOs ----

public class WebDTOs {

    public record AjouterJeuRequest(
            @NotBlank(message = "Merci de préciser le nom du jeu")
            @Size(min = 1)
            String nom,
            LocalDate dateDeSortie,
            String description,
            Long editeurId,
            List<Long> plateformeIds
    ) {}

    public record ModifierJeuRequest(
            String nom,
            LocalDate dateDeSortie,
            String description
    ) {}

    public record AjouterEditeurRequest(
            @NotBlank(message = "Merci de préciser le nom de l'éditeur")
            @Size(min = 2)
            String nom,
            String logo
    ) {}

    public record RedigerAvisRequest(
            String contenu,
            float note,
            Long jeuId,
            Long joueurId
    ) {}

    // ---- Response DTOs ----

    public record JeuResponse(
            Long id,
            String nom,
            LocalDate dateDeSortie,
            String description,
            boolean possedeImage,
            EditeurResponse editeur
    ) {}

    public record EditeurResponse(
            Long id,
            String nom,
            String logo
    ) {}

    public record AvisResponse(
            Long id,
            String contenu,
            float note,
            String statut,
            Long jeuId,
            String jeuNom
    ) {}
}
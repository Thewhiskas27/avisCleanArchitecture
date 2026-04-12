package fr.clelia.avis.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "jeu")
@Data
@NoArgsConstructor
public class JeuJPAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank
    @Size(min = 1)
    private String nom;

    private LocalDate dateDeSortie;
    private String description;
    private boolean possedeImage;

    @ManyToOne
    @NotNull
    private EditeurJPAEntity editeur;

    @ManyToMany
    private List<PlateformeJPAEntity> plateformes;
}
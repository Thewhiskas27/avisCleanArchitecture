package fr.clelia.avis.infrastructure.persistence.entity;

import fr.clelia.avis.domain.Avis;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Entity
@Table(name = "avis")
@Data
@NoArgsConstructor
public class AvisJPAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String contenu;

    @Range(min = 0, max = 20)
    private float note;

    private LocalDateTime dateDeCreation;

    @Enumerated(EnumType.STRING)
    private Avis.Statut statut = Avis.Statut.EN_ATTENTE;

    @ManyToOne
    @NotNull
    private JeuJPAEntity jeu;

    @ManyToOne
    @NotNull
    private JoueurJPAEntity joueur;

    @ManyToOne
    private ModerateurJPAEntity moderateur;
}
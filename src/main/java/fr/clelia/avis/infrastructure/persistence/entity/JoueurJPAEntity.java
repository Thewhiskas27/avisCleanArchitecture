package fr.clelia.avis.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "joueur")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class JoueurJPAEntity extends UtilisateurJPAEntity {

    @Past
    private LocalDate dateDeNaissance;

    @OneToMany(mappedBy = "joueur")
    private List<AvisJPAEntity> avis;
}
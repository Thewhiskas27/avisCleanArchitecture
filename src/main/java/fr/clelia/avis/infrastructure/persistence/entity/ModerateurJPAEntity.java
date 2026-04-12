package fr.clelia.avis.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "moderateur")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class ModerateurJPAEntity extends UtilisateurJPAEntity {

    private String numeroDeTelephone;
}
package fr.clelia.avis.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "editeur")
@Data
@NoArgsConstructor
public class EditeurJPAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Size(min = 2)
    private String nom;

    private String logo;

    @OneToMany(mappedBy = "editeur")
    private List<JeuJPAEntity> jeux;
}
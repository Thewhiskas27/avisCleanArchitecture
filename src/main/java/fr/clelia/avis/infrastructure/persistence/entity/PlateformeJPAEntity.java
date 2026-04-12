package fr.clelia.avis.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "plateforme")
@Data
@NoArgsConstructor
public class PlateformeJPAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @NotBlank
    @Size(min = 1)
    private String nom;

    @ManyToMany(mappedBy = "plateformes")
    private List<JeuJPAEntity> jeux;
}
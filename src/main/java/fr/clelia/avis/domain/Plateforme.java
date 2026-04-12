package fr.clelia.avis.domain;

public class Plateforme {

    private Long id;
    private String nom;

    public Plateforme() {}

    public Plateforme(String nom) {
        this.nom = nom;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
}
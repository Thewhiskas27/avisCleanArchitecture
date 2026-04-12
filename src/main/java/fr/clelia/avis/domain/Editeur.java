package fr.clelia.avis.domain;

import java.util.List;

public class Editeur {

    private Long id;
    private String nom;
    private String logo;
    private List<Jeu> jeux;

    public Editeur() {}

    public Editeur(String nom, String logo) {
        this.nom = nom;
        this.logo = logo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getLogo() { return logo; }
    public void setLogo(String logo) { this.logo = logo; }
    public List<Jeu> getJeux() { return jeux; }
    public void setJeux(List<Jeu> jeux) { this.jeux = jeux; }
}
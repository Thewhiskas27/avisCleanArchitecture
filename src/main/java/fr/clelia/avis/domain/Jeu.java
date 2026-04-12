package fr.clelia.avis.domain;

import java.time.LocalDate;
import java.util.List;

public class Jeu {

    private Long id;
    private String nom;
    private LocalDate dateDeSortie;
    private String description;
    private boolean possedeImage;
    private Editeur editeur;
    private List<Plateforme> plateformes;

    public Jeu() {}

    public Jeu(String nom, LocalDate dateDeSortie, String description, boolean possedeImage,
               Editeur editeur, List<Plateforme> plateformes) {
        this.nom = nom;
        this.dateDeSortie = dateDeSortie;
        this.description = description;
        this.possedeImage = possedeImage;
        this.editeur = editeur;
        this.plateformes = plateformes;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public LocalDate getDateDeSortie() { return dateDeSortie; }
    public void setDateDeSortie(LocalDate dateDeSortie) { this.dateDeSortie = dateDeSortie; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isPossedeImage() { return possedeImage; }
    public void setPossedeImage(boolean possedeImage) { this.possedeImage = possedeImage; }
    public Editeur getEditeur() { return editeur; }
    public void setEditeur(Editeur editeur) { this.editeur = editeur; }
    public List<Plateforme> getPlateformes() { return plateformes; }
    public void setPlateformes(List<Plateforme> plateformes) { this.plateformes = plateformes; }
}
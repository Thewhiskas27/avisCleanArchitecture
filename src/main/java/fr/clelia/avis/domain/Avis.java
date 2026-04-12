package fr.clelia.avis.domain;

import fr.clelia.avis.business.Moderateur;

import java.time.LocalDateTime;

public class Avis {

    public enum Statut {
        EN_ATTENTE,
        PUBLIE,
        REJETE,
        SIGNALE,
        SUPPRIME
    }

    private Long id;
    private String contenu;
    private float note;
    private LocalDateTime dateDeCreation;
    private Statut statut;
    private Jeu jeu;
    private Joueur joueur;
    private Moderateur moderateur;

    public Avis() {
        this.statut = Statut.EN_ATTENTE;
        this.dateDeCreation = LocalDateTime.now();
    }

    public Avis(String contenu, float note, Jeu jeu, Joueur joueur) {
        this();
        this.contenu = contenu;
        this.note = note;
        this.jeu = jeu;
        this.joueur = joueur;
    }

    // State transitions
    public void approuver() {
        if (this.statut != Statut.EN_ATTENTE) {
            throw new IllegalStateException("Seul un avis en attente peut être approuvé");
        }
        this.statut = Statut.PUBLIE;
    }

    public void rejeter() {
        if (this.statut != Statut.EN_ATTENTE) {
            throw new IllegalStateException("Seul un avis en attente peut être rejeté");
        }
        this.statut = Statut.REJETE;
    }

    public void signaler() {
        if (this.statut != Statut.PUBLIE) {
            throw new IllegalStateException("Seul un avis publié peut être signalé");
        }
        this.statut = Statut.SIGNALE;
    }

    public void supprimer() {
        if (this.statut != Statut.SIGNALE) {
            throw new IllegalStateException("Seul un avis signalé peut être supprimé");
        }
        this.statut = Statut.SUPPRIME;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }
    public float getNote() { return note; }
    public void setNote(float note) { this.note = note; }
    public LocalDateTime getDateDeCreation() { return dateDeCreation; }
    public void setDateDeCreation(LocalDateTime dateDeCreation) { this.dateDeCreation = dateDeCreation; }
    public Statut getStatut() { return statut; }
    public void setStatut(Statut statut) { this.statut = statut; }
    public Jeu getJeu() { return jeu; }
    public void setJeu(Jeu jeu) { this.jeu = jeu; }
    public Joueur getJoueur() { return joueur; }
    public void setJoueur(Joueur joueur) { this.joueur = joueur; }
    public Moderateur getModerateur() { return moderateur; }
    public void setModerateur(Moderateur moderateur) { this.moderateur = moderateur; }
}
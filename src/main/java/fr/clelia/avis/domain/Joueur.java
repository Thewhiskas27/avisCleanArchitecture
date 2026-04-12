package fr.clelia.avis.domain;

import java.time.LocalDate;
import java.util.List;

public class Joueur extends Utilisateur {

    private LocalDate dateDeNaissance;
    private List<Avis> avis;

    public Joueur() {}

    public Joueur(String pseudo, String motDePasse, String email, LocalDate dateDeNaissance) {
        super(pseudo, motDePasse, email);
        this.dateDeNaissance = dateDeNaissance;
    }

    public LocalDate getDateDeNaissance() { return dateDeNaissance; }
    public void setDateDeNaissance(LocalDate dateDeNaissance) { this.dateDeNaissance = dateDeNaissance; }
    public List<Avis> getAvis() { return avis; }
    public void setAvis(List<Avis> avis) { this.avis = avis; }
}
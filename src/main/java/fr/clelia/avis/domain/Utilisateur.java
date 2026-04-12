package fr.clelia.avis.domain;

public abstract class Utilisateur {

    protected Long id;
    protected String pseudo;
    protected String email;
    protected String motDePasse;

    public Utilisateur() {}

    public Utilisateur(String pseudo, String motDePasse, String email) {
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
}
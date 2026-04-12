package fr.clelia.avis.domain;

public class Moderateur extends Utilisateur {

    private String numeroDeTelephone;

    public Moderateur() {}

    public Moderateur(String pseudo, String motDePasse, String email, String numeroDeTelephone) {
        super(pseudo, motDePasse, email);
        this.numeroDeTelephone = numeroDeTelephone;
    }

    public String getNumeroDeTelephone() { return numeroDeTelephone; }
    public void setNumeroDeTelephone(String numeroDeTelephone) { this.numeroDeTelephone = numeroDeTelephone; }
}
// src/main/java/fr/clelia/avis/domain/exception/EditeurDejaExistantException.java
package fr.clelia.avis.domain.exception;

public class EditeurDejaExistantException extends RuntimeException {
    public EditeurDejaExistantException(String nom) {
        super("Cet éditeur est déjà présent : " + nom);
    }
}
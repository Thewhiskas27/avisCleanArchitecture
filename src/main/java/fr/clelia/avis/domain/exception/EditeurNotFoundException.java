// src/main/java/fr/clelia/avis/domain/exception/EditeurNotFoundException.java
package fr.clelia.avis.domain.exception;

public class EditeurNotFoundException extends RuntimeException {
    public EditeurNotFoundException(Long id) {
        super("Éditeur introuvable : " + id);
    }
}
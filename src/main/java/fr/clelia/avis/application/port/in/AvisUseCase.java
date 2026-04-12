package fr.clelia.avis.application.port.in;

import fr.clelia.avis.domain.Avis;

import java.util.List;

public interface AvisUseCase {
    Avis redigerAvis(RedigerAvisCommand command);
    Avis approuverAvis(Long id);
    Avis rejeterAvis(Long id);
    Avis signalerAvis(Long id);
    List<Avis> recupererAvis();
    List<Avis> recupererAvisParJeu(Long jeuId);

    record RedigerAvisCommand(
            String contenu,
            float note,
            Long jeuId,
            Long joueurId
    ) {}
}
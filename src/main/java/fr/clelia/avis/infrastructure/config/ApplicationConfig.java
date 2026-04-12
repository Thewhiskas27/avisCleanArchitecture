package fr.clelia.avis.infrastructure.config;

import fr.clelia.avis.application.port.out.AvisRepositoryPort;
import fr.clelia.avis.application.port.out.EditeurRepositoryPort;
import fr.clelia.avis.application.port.out.JeuRepositoryPort;
import fr.clelia.avis.application.service.AvisService;
import fr.clelia.avis.application.service.EditeurService;
import fr.clelia.avis.application.service.JeuService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// This class is the only place where Spring enters the application layer.
// The services themselves have zero Spring annotations — they are pure Java.
@Configuration
public class ApplicationConfig {

    @Bean
    public JeuService jeuService(JeuRepositoryPort jeuRepositoryPort) {
        return new JeuService(jeuRepositoryPort);
    }

    @Bean
    public EditeurService editeurService(EditeurRepositoryPort editeurRepositoryPort) {
        return new EditeurService(editeurRepositoryPort);
    }

    @Bean
    public AvisService avisService(AvisRepositoryPort avisRepositoryPort,
                                   JeuRepositoryPort jeuRepositoryPort) {
        return new AvisService(avisRepositoryPort, jeuRepositoryPort);
    }
}
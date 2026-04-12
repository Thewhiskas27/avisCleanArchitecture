package fr.clelia.avis.configuration;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.unit.DataSize;

@Configuration
public class TeleversementConfiguration {

    @Bean(name="uploadConfig")
    @Profile("DEV")
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(8));
        factory.setMaxRequestSize(DataSize.ofMegabytes(10));
        return factory.createMultipartConfig();
    }

    @Bean(name="uploadConfig")
    @Profile("TEST")
    MultipartConfigElement multipartConfigElementTest() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(4));
        factory.setMaxRequestSize(DataSize.ofMegabytes(8));
        return factory.createMultipartConfig();
    }
}
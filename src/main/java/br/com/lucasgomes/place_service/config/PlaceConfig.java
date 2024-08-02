package br.com.lucasgomes.place_service.config;

import br.com.lucasgomes.place_service.domain.PlaceRepository;
import br.com.lucasgomes.place_service.domain.PlaceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlaceConfig {
    @Bean
    PlaceService placeService(PlaceRepository placeRepository) {
        return new PlaceService(placeRepository);
    }
}

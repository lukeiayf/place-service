package br.com.lucasgomes.place_service.config;

import br.com.lucasgomes.place_service.domain.PlaceRepository;
import br.com.lucasgomes.place_service.domain.PlaceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@Configuration
//This option is to enable the createdAt and lastUpdatedAt annotations
@EnableR2dbcAuditing
public class PlaceConfig {
    @Bean
    PlaceService placeService(PlaceRepository placeRepository) {
        return new PlaceService(placeRepository);
    }
}

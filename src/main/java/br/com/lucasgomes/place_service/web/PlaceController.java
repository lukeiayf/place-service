package br.com.lucasgomes.place_service.web;

import br.com.lucasgomes.place_service.api.PlaceRequest;
import br.com.lucasgomes.place_service.api.PlaceResponse;
import br.com.lucasgomes.place_service.domain.Place;
import br.com.lucasgomes.place_service.domain.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PostMapping
    public ResponseEntity<Mono<PlaceResponse>> create(@RequestBody PlaceRequest request) {
        Mono<Place> createdPlace = placeService.create(request);
        Mono<PlaceResponse> placeResponse = createdPlace.map(PlaceMapper::fromPlaceToResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(placeResponse);
    }
}

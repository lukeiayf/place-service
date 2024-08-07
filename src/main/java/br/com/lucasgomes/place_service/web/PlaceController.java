package br.com.lucasgomes.place_service.web;

import br.com.lucasgomes.place_service.api.PlaceRequest;
import br.com.lucasgomes.place_service.api.PlaceResponse;
import br.com.lucasgomes.place_service.domain.Place;
import br.com.lucasgomes.place_service.domain.PlaceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PostMapping
    public ResponseEntity<Mono<PlaceResponse>> create(@Valid @RequestBody PlaceRequest request) {
        Mono<Place> createdPlace = placeService.create(request);
        Mono<PlaceResponse> placeResponse = createdPlace.map(PlaceMapper::fromPlaceToResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(placeResponse);
    }

    @GetMapping
    public ResponseEntity<Flux<PlaceResponse>> getPlaces() {
        Flux<Place> places = placeService.list();
        Flux<PlaceResponse> placesResponse = places.map(PlaceMapper::fromPlaceToResponse);
        return ResponseEntity.status(HttpStatus.OK).body(placesResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mono<PlaceResponse>> updatePlace(@RequestBody PlaceRequest placeRequest, @PathVariable Long id) {
        Mono<PlaceResponse> placeResponse = placeService.update(placeRequest, id).map(PlaceMapper::fromPlaceToResponse);
        return ResponseEntity.status(HttpStatus.OK).body(placeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Flux<PlaceResponse>> deletePlace(@PathVariable Long id) {
        Flux<PlaceResponse> places = placeService.delete(id).map(PlaceMapper::fromPlaceToResponse);
        return ResponseEntity.status(HttpStatus.OK).body(places);
    }
}

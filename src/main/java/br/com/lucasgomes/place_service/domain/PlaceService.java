package br.com.lucasgomes.place_service.domain;

import br.com.lucasgomes.place_service.api.PlaceRequest;
import com.github.slugify.Slugify;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PlaceService {
    private final PlaceRepository placeRepository;
    private final Slugify slg;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
        this.slg = Slugify.builder().build();
    }

    public Mono<Place> create(PlaceRequest placeRequest) {
        String slug = slg.slugify(placeRequest.name());
        Place place = new Place(null, placeRequest.name(), slug, placeRequest.state(), null, null);
        return placeRepository.save(place);
    }

    public Flux<Place> list() {
        return placeRepository.findAll();
    }

    public Mono<Place> update(PlaceRequest placeRequest, Long id) {
        return placeRepository.findById(id)
                .flatMap(originalPlace -> {
                    originalPlace.setName(placeRequest.name());
                    originalPlace.setState(placeRequest.state());
                    return placeRepository.save(originalPlace);
                });
    }

    public Flux<Place> delete(Long id) {
        return placeRepository.deleteById(id).thenMany(list());
    }
}

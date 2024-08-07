package br.com.lucasgomes.place_service.web;

import br.com.lucasgomes.place_service.api.PlaceResponse;
import br.com.lucasgomes.place_service.domain.Place;

public class PlaceMapper {
    public static PlaceResponse fromPlaceToResponse(Place place) {
        return new PlaceResponse(place.getId(), place.getName(), place.getSlug(), place.getState(), place.getCreatedAt(), place.getUpdatedAt());
    }
}

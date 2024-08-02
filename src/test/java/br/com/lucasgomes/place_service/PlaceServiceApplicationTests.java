package br.com.lucasgomes.place_service;

import br.com.lucasgomes.place_service.api.PlaceRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaceServiceApplicationTests {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void testCreatePlaceSuccess() {
        String name = "Valid Name";
        String state = "Valid State";
        String slug = "valid-name";

        webTestClient.post()
                .uri("/places")
                .bodyValue(new PlaceRequest(name, state))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("name").isEqualTo(name)
                .jsonPath("state").isEqualTo(state)
                .jsonPath("slug").isEqualTo(slug)
                .jsonPath("createdAt").isNotEmpty()
                .jsonPath("updatedAt").isNotEmpty();
    }

    @Test
    void testCreatePlaceFailure(){
        String name = "";
        String state = "";

        webTestClient.post()
                .uri("/places")
                .bodyValue(new PlaceRequest(name, state))
                .exchange()
                .expectStatus().isBadRequest();
    }

}

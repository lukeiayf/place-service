package br.com.lucasgomes.place_service;

import br.com.lucasgomes.place_service.api.PlaceRequest;
import br.com.lucasgomes.place_service.domain.PlaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PlaceServiceApplicationTests {

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        placeRepository.deleteAll().block();
    }

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
    void testCreatePlaceFailure() {
        String name = "";
        String state = "";

        webTestClient.post()
                .uri("/places")
                .bodyValue(new PlaceRequest(name, state))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testGetPlaceSuccess() {
        String name = "Valid Name";
        String state = "Valid State";
        String slug = "valid-name";

        webTestClient.post()
                .uri("/places")
                .bodyValue(new PlaceRequest(name, state))
                .exchange()
                .expectStatus().isCreated();

        webTestClient.get()
                .uri("/places")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].name").isEqualTo("Valid Name")
                .jsonPath("$[0].state").isEqualTo("Valid State")
                .jsonPath("$[0].slug").isEqualTo("valid-name");
    }

    @Test
    void testUpdatePlaceSuccess() {
        String name = "Valid Name";
        String state = "Valid State";
        String slug = "valid-name";

        webTestClient.post()
                .uri("/places")
                .bodyValue(new PlaceRequest(name, state))
                .exchange()
                .expectStatus().isCreated();

        webTestClient.put()
                .uri("/places/1")
                .bodyValue(new PlaceRequest("Updated Name", "Updated State"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("name").isEqualTo("Updated Name")
                .jsonPath("state").isEqualTo("Updated State");
    }

    @Test
    void testDeletePlaceSuccess() {
        String name = "Valid Name";
        String state = "Valid State";
        String slug = "valid-name";

        webTestClient.post()
                .uri("/places")
                .bodyValue(new PlaceRequest(name, state))
                .exchange()
                .expectStatus().isCreated();

        webTestClient.delete()
                .uri("/places/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$").isEmpty();
    }


}

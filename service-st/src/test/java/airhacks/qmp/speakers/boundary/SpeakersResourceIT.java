package airhacks.qmp.speakers.boundary;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class SpeakersResourceIT {

    @Inject
    @RestClient
    SpeakersResourceClient client;

    @Test
    void createAndRetrieveSpeaker() {
        var speaker = Json.createObjectBuilder()
                .add("name", "Duke")
                .build();

        var response = this.client.create(speaker);
        assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());

        var speakers = this.client.all();
        assertThat(speakers).isNotEmpty();
    }
}

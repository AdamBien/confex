package airhacks.confex.sessions.boundary;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;

import airhacks.confex.speakers.boundary.SpeakersResourceClient;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.Response;

@QuarkusTest
class SessionsResourceIT {

    @Inject
    @RestClient
    SessionsResourceClient client;

    @Inject
    @RestClient
    SpeakersResourceClient speakersClient;

    @Test
    void embedPerformersInRetrievedSession() {
        var dukeId = "duke-" + UUID.randomUUID();
        var jugId = "jug-" + UUID.randomUUID();
        createSpeaker(dukeId, "Duke");
        createSpeaker(jugId, "JUG");

        var sessionId = "session-" + UUID.randomUUID();
        var session = Json.createObjectBuilder()
                .add("identifier", sessionId)
                .add("name", "The Java Mascot Story")
                .add("description", "How Duke became iconic")
                .add("about", "Java history")
                .add("startDate", "2026-06-01T09:00:00Z")
                .add("endDate", "2026-06-01T10:00:00Z")
                .add("location", "Main Stage")
                .add("performerIds", Json.createArrayBuilder().add(dukeId).add(jugId))
                .add("url", "https://confex.example.com/sessions/" + sessionId)
                .build();

        var createResponse = this.client.create(session);
        assertThat(createResponse.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());

        var sessions = this.client.all();
        assertThat(sessions).anySatisfy(entry -> {
            var json = entry.asJsonObject();
            if (!sessionId.equals(json.getString("identifier"))) {
                return;
            }
            var performer = json.getJsonArray("performer");
            assertThat(performer).hasSize(2);
            var ids = performer.getValuesAs(JsonObject.class).stream()
                    .map(speaker -> speaker.getString("identifier"))
                    .toList();
            assertThat(ids).containsExactlyInAnyOrder(dukeId, jugId);
        });
    }

    @Test
    void rejectSessionWithUnknownPerformer() {
        var session = Json.createObjectBuilder()
                .add("identifier", "session-" + UUID.randomUUID())
                .add("name", "Phantom Speaker")
                .add("description", "")
                .add("about", "")
                .add("startDate", "")
                .add("endDate", "")
                .add("location", "")
                .add("performerIds", Json.createArrayBuilder().add("unknown-" + UUID.randomUUID()))
                .add("url", "")
                .build();

        var response = this.client.create(session);
        assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
    }

    void createSpeaker(String identifier, String givenName) {
        var speaker = Json.createObjectBuilder()
                .add("identifier", identifier)
                .add("givenName", givenName)
                .add("familyName", "Test")
                .add("description", "")
                .add("jobTitle", "")
                .add("affiliation", "")
                .add("image", "")
                .add("url", "")
                .build();
        var response = this.speakersClient.create(speaker);
        assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
    }
}

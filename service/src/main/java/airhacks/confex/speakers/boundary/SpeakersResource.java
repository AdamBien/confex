package airhacks.confex.speakers.boundary;

import airhacks.confex.speakers.control.Speakers;
import airhacks.confex.speakers.entity.Speaker;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("speakers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SpeakersResource {

    @Inject
    Speakers speakers;

    @GET
    public JsonArray all() {
        var builder = Json.createArrayBuilder();
        this.speakers.all().stream()
                .map(Speaker::toJSON)
                .forEach(builder::add);
        return builder.build();
    }

    @POST
    public Response create(JsonObject json) {
        var speaker = Speaker.fromJSON(json);
        this.speakers.add(speaker);
        return Response.status(Response.Status.CREATED)
                .entity(speaker.toJSON())
                .build();
    }
}

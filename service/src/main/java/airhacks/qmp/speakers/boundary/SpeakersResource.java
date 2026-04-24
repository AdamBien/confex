package airhacks.qmp.speakers.boundary;

import airhacks.qmp.speakers.entity.Speaker;

import jakarta.enterprise.context.ApplicationScoped;
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
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Path("speakers")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SpeakersResource {

    List<Speaker> speakers = new CopyOnWriteArrayList<>();

    @GET
    public JsonArray all() {
        var builder = Json.createArrayBuilder();
        this.speakers.stream()
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

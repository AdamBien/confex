package airhacks.confex.sessions.boundary;

import airhacks.confex.sessions.control.Sessions;
import airhacks.confex.sessions.entity.Session;
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

@Path("sessions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SessionsResource {

    @Inject
    Sessions sessions;

    @GET
    public JsonArray all() {
        var builder = Json.createArrayBuilder();
        this.sessions.all().stream()
                .map(Session::toJSON)
                .forEach(builder::add);
        return builder.build();
    }

    @POST
    public Response create(JsonObject json) {
        var session = Session.fromJSON(json);
        this.sessions.add(session);
        return Response.status(Response.Status.CREATED)
                .entity(session.toJSON())
                .build();
    }
}

package airhacks.qmp.sessions.boundary;

import airhacks.qmp.sessions.entity.Session;

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

@Path("sessions")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SessionsResource {

    List<Session> sessions = new CopyOnWriteArrayList<>();

    @GET
    public JsonArray all() {
        var builder = Json.createArrayBuilder();
        this.sessions.stream()
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

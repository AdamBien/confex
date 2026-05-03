package airhacks.confex.sessions.control;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import airhacks.confex.sessions.entity.Session;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class Sessions {

    List<Session> sessions = new CopyOnWriteArrayList<>();

    public List<Session> all() {
        return List.copyOf(this.sessions);
    }

    public void add(Session session) {
        if (exists(session.identifier())) {
            throw new ClientErrorException(Response.Status.CONFLICT);
        }
        this.sessions.add(session);
    }

    boolean exists(String identifier) {
        return this.sessions.stream()
                .map(Session::identifier)
                .anyMatch(identifier::equals);
    }
}

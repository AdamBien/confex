package airhacks.confex.sessions.control;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import airhacks.confex.sessions.entity.Session;
import airhacks.confex.speakers.control.Speakers;
import airhacks.confex.speakers.entity.Speaker;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class Sessions {

    @Inject
    Speakers speakers;

    List<Session> sessions = new CopyOnWriteArrayList<>();

    public List<Session> all() {
        return List.copyOf(this.sessions);
    }

    public Session create(String identifier, String name, String description, String about,
                          String startDate, String endDate, String location,
                          List<String> performerIds, String url) {
        var performers = performerIds.stream()
                .map(this::requirePerformer)
                .toList();
        var session = new Session(identifier, name, description, about,
                startDate, endDate, location, performers, url);
        add(session);
        return session;
    }

    public void add(Session session) {
        if (exists(session.identifier())) {
            throw new ClientErrorException(Response.Status.CONFLICT);
        }
        this.sessions.add(session);
    }

    Speaker requirePerformer(String identifier) {
        return this.speakers.findByIdentifier(identifier)
                .orElseThrow(() -> new BadRequestException("unknown performer identifier: " + identifier));
    }

    boolean exists(String identifier) {
        return this.sessions.stream()
                .map(Session::identifier)
                .anyMatch(identifier::equals);
    }
}

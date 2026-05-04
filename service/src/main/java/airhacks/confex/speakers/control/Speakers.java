package airhacks.confex.speakers.control;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import airhacks.confex.speakers.entity.Speaker;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class Speakers {

    List<Speaker> speakers = new CopyOnWriteArrayList<>();

    public List<Speaker> all() {
        return List.copyOf(this.speakers);
    }

    public Optional<Speaker> findByIdentifier(String identifier) {
        return this.speakers.stream()
                .filter(speaker -> identifier.equals(speaker.identifier()))
                .findFirst();
    }

    public void add(Speaker speaker) {
        if (exists(speaker.identifier())) {
            throw new ClientErrorException(Response.Status.CONFLICT);
        }
        this.speakers.add(speaker);
    }

    boolean exists(String identifier) {
        return this.speakers.stream()
                .map(Speaker::identifier)
                .anyMatch(identifier::equals);
    }
}

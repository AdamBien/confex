package airhacks.confex.sessions.entity;

import java.util.List;

import airhacks.confex.speakers.entity.Speaker;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.BadRequestException;

/**
 * Conference session modeled after
 * <a href="https://schema.org/Event">schema.org/Event</a>. A session can have
 * multiple <a href="https://schema.org/performer">performers</a>; a speaker
 * may also appear in multiple sessions (many-to-many).
 *
 * @param identifier stable id, see <a href="https://schema.org/identifier">schema.org/identifier</a>
 * @param name session title
 * @param description abstract or summary
 * @param about topic or subject area
 * @param startDate ISO-8601 start timestamp
 * @param endDate ISO-8601 end timestamp
 * @param location room, stage or venue
 * @param performers schema.org/performer association; resolved at the boundary, JPA-friendly for a future {@code @ManyToMany}
 * @param url session page or recording URL
 */
public record Session(String identifier, String name, String description, String about,
                      String startDate, String endDate, String location,
                      List<Speaker> performers, String url) {

    public Session {
        requireNotBlank(identifier, "identifier");
        requireNotBlank(name, "name");
    }

    /**
     * Rejects null or blank input as
     * <a href="https://www.rfc-editor.org/rfc/rfc9110.html#section-15.5.1">HTTP 400 Bad Request</a>,
     * signaling a client-side error rather than a server fault.
     */
    static void requireNotBlank(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new BadRequestException(name + " is required");
        }
    }

    public JsonObject toJSON() {
        var performerArray = Json.createArrayBuilder();
        this.performers.stream()
                .map(Speaker::toJSON)
                .forEach(performerArray::add);
        return Json.createObjectBuilder()
                .add("identifier", this.identifier)
                .add("name", this.name)
                .add("description", this.description)
                .add("about", this.about)
                .add("startDate", this.startDate)
                .add("endDate", this.endDate)
                .add("location", this.location)
                .add("performer", performerArray)
                .add("url", this.url)
                .build();
    }
}

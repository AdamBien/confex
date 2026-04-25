package airhacks.qmp.sessions.entity;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import java.util.List;

/**
 * Conference session modeled after
 * <a href="https://schema.org/Event">schema.org/Event</a>. A session can have
 * multiple <a href="https://schema.org/performer">performers</a>; a speaker
 * may also appear in multiple sessions (many-to-many).
 *
 * @param name session title
 * @param description abstract or summary
 * @param about topic or subject area
 * @param startDate ISO-8601 start timestamp
 * @param endDate ISO-8601 end timestamp
 * @param location room, stage or venue
 * @param performerIds {@code Speaker.identifier} references; resolved by the speakers BC
 * @param url session page or recording URL
 */
public record Session(String name, String description, String about,
                      String startDate, String endDate, String location,
                      List<String> performerIds, String url) {

    public JsonObject toJSON() {
        var performersArray = Json.createArrayBuilder();
        this.performerIds.forEach(performersArray::add);
        return Json.createObjectBuilder()
                .add("name", this.name)
                .add("description", this.description)
                .add("about", this.about)
                .add("startDate", this.startDate)
                .add("endDate", this.endDate)
                .add("location", this.location)
                .add("performerIds", performersArray)
                .add("url", this.url)
                .build();
    }

    public static Session fromJSON(JsonObject json) {
        var performerIds = json.getJsonArray("performerIds")
                .getValuesAs(jakarta.json.JsonString.class).stream()
                .map(jakarta.json.JsonString::getString)
                .toList();
        return new Session(
                json.getString("name"),
                json.getString("description"),
                json.getString("about"),
                json.getString("startDate"),
                json.getString("endDate"),
                json.getString("location"),
                performerIds,
                json.getString("url")
        );
    }
}

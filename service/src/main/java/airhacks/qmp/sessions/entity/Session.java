package airhacks.qmp.sessions.entity;

import jakarta.json.Json;
import jakarta.json.JsonObject;

/**
 * Conference session modeled after
 * <a href="https://schema.org/Event">schema.org/Event</a>.
 *
 * @param name session title
 * @param description abstract or summary
 * @param about topic or subject area
 * @param startDate ISO-8601 start timestamp
 * @param endDate ISO-8601 end timestamp
 * @param location room, stage or venue
 * @param performer speaker name(s) or identifier
 * @param url session page or recording URL
 */
public record Session(String name, String description, String about,
                      String startDate, String endDate, String location,
                      String performer, String url) {

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("name", this.name)
                .add("description", this.description)
                .add("about", this.about)
                .add("startDate", this.startDate)
                .add("endDate", this.endDate)
                .add("location", this.location)
                .add("performer", this.performer)
                .add("url", this.url)
                .build();
    }

    public static Session fromJSON(JsonObject json) {
        return new Session(
                json.getString("name"),
                json.getString("description"),
                json.getString("about"),
                json.getString("startDate"),
                json.getString("endDate"),
                json.getString("location"),
                json.getString("performer"),
                json.getString("url")
        );
    }
}

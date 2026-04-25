package airhacks.qmp.speakers.entity;

import jakarta.json.Json;
import jakarta.json.JsonObject;

/**
 * Conference speaker modeled after
 * <a href="https://schema.org/Person">schema.org/Person</a> used as
 * <a href="https://schema.org/performer">schema.org/performer</a>.
 *
 * @param identifier stable id, see <a href="https://schema.org/identifier">schema.org/identifier</a>;
 *                   referenced by sessions to express many-to-many performer links
 * @param givenName first name
 * @param familyName last name
 * @param description biography or about text
 * @param jobTitle professional role, e.g. "Senior Engineer"
 * @param affiliation organization or company name
 * @param image profile picture URL
 * @param url speaker's website or public profile
 */
public record Speaker(String identifier, String givenName, String familyName, String description,
                      String jobTitle, String affiliation, String image, String url) {

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("identifier", this.identifier)
                .add("givenName", this.givenName)
                .add("familyName", this.familyName)
                .add("description", this.description)
                .add("jobTitle", this.jobTitle)
                .add("affiliation", this.affiliation)
                .add("image", this.image)
                .add("url", this.url)
                .build();
    }

    public static Speaker fromJSON(JsonObject json) {
        return new Speaker(
                json.getString("identifier"),
                json.getString("givenName"),
                json.getString("familyName"),
                json.getString("description"),
                json.getString("jobTitle"),
                json.getString("affiliation"),
                json.getString("image"),
                json.getString("url")
        );
    }
}

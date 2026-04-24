package airhacks.qmp.speakers.entity;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public record Speaker(String firstName, String lastName, String bio, String profilePicture, String company) {

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("firstName", this.firstName)
                .add("lastName", this.lastName)
                .add("bio", this.bio)
                .add("profilePicture", this.profilePicture)
                .add("company", this.company)
                .build();
    }

    public static Speaker fromJSON(JsonObject json) {
        return new Speaker(
                json.getString("firstName"),
                json.getString("lastName"),
                json.getString("bio"),
                json.getString("profilePicture"),
                json.getString("company")
        );
    }
}

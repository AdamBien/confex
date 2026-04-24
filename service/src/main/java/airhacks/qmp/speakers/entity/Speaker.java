package airhacks.qmp.speakers.entity;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public record Speaker(String name) {

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("name", this.name)
                .build();
    }

    public static Speaker fromJSON(JsonObject json) {
        return new Speaker(json.getString("name"));
    }
}

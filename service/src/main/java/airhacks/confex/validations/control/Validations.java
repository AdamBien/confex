package airhacks.confex.validations.control;

import jakarta.ws.rs.BadRequestException;

public interface Validations {

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
}

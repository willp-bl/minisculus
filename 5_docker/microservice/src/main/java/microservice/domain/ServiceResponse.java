package microservice.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceResponse {

    private final String message;
    private final String hostname;
    private final String version;

    @JsonCreator
    public ServiceResponse(@JsonProperty("message") String message,
                           @JsonProperty("hostname") String hostname,
                           @JsonProperty("version") String version) {
        this.message = message;
        this.hostname = hostname;
        this.version = version;
    }

    @JsonProperty
    public String getMessage() {
        return message;
    }

    @JsonProperty
    public String getHostname() {
        return hostname;
    }

    @JsonProperty
    public String getVersion() {
        return version;
    }

}

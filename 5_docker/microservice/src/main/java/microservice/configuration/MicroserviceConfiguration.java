package microservice.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;

public class MicroserviceConfiguration extends Configuration {

    @Valid
    public String clacksOverhead = "Terry Pratchett";

    @Valid
    @NotEmpty
    public String message;

    @JsonProperty
    public String getMessage() {
        return message;
    }

    @JsonProperty
    public String getClacksOverhead() {
        return clacksOverhead;
    }

}

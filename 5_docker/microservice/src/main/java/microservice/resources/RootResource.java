package microservice.resources;

import com.codahale.metrics.annotation.Timed;
import microservice.annotations.ClacksOverhead;
import microservice.domain.ServiceResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class RootResource {

    private final String message;
    private final String hostname;
    private final String version;

    public RootResource(String message, String hostname, String version) {
        this.message = message;
        this.hostname = hostname;
        this.version = version;
    }

    @GET
    @Timed
    @ClacksOverhead
    public ServiceResponse get() {
        return new ServiceResponse(message, hostname, version);
    }
}

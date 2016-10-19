package microservice.resources;

import io.dropwizard.testing.junit.ResourceTestRule;
import microservice.domain.ServiceResponse;
import org.junit.ClassRule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RootResourceTest {

    @ClassRule
    public static final ResourceTestRule rootResource = ResourceTestRule.builder()
            .addResource(new RootResource("message", "hostname", "version"))
            .build();

    @Test
    public void get() throws Exception {
        final ServiceResponse response = rootResource.client().target("/").request().get(ServiceResponse.class);
        assertThat(response).isEqualToComparingFieldByField(new ServiceResponse("message", "hostname", "version"));
    }

}
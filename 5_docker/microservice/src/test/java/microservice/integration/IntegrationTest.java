package microservice.integration;

import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import microservice.MicroserviceApplication;
import microservice.configuration.MicroserviceConfiguration;
import microservice.domain.ServiceResponse;
import microservice.helpers.HostnameHelper;
import org.eclipse.jetty.http.HttpStatus;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class IntegrationTest {

    private static final String CLACKS = "CLACKS";
    private static final String MESSAGE = "MESSAGE";

    @ClassRule
    public static final DropwizardAppRule<MicroserviceConfiguration> microserviceAppRule =
            new DropwizardAppRule<>(MicroserviceApplication.class,
                    ResourceHelpers.resourceFilePath("microservice.yml"),
                    ConfigOverride.config("server.applicationConnectors[0].port", "0"),
                    ConfigOverride.config("clacksOverhead", CLACKS),
                    ConfigOverride.config("message", MESSAGE));

    @Test
    public void testRootGet() throws UnknownHostException {
        Client client = new JerseyClientBuilder().build();
        final String resource = MessageFormat.format("http://localhost:{0}/", Integer.toString(microserviceAppRule.getLocalPort()));

        final Response response = client.target(resource).request().get();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);
        assertThat(response.getHeaderString("X-GNU-Clacks-Overhead")).isEqualTo(MessageFormat.format("GNU {0}", CLACKS));
        assertThat(response.readEntity(ServiceResponse.class).getMessage()).isEqualTo(MESSAGE);
    }
}

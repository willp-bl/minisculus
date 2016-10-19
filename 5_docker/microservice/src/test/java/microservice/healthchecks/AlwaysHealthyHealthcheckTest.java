package microservice.healthchecks;

import com.codahale.metrics.health.HealthCheck;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AlwaysHealthyHealthcheckTest {

    @Test
    public void check() throws Exception {
        assertThat(new AlwaysHealthyHealthcheck().check()).isEqualTo(HealthCheck.Result.healthy("Super Healthy!"));
    }
}
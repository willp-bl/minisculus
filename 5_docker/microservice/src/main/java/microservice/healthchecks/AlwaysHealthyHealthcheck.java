package microservice.healthchecks;

import com.codahale.metrics.health.HealthCheck;

public class AlwaysHealthyHealthcheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
        return Result.healthy("Super Healthy!");
    }
}

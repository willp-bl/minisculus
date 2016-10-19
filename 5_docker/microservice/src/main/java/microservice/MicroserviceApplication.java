package microservice;

import com.google.common.base.Optional;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import microservice.configuration.MicroserviceConfiguration;
import microservice.filters.ClacksOverheadFeature;
import microservice.healthchecks.AlwaysHealthyHealthcheck;
import microservice.helpers.HostnameHelper;
import microservice.resources.RootResource;

public class MicroserviceApplication extends Application<MicroserviceConfiguration> {

    public static void main(String[] args) throws Exception {
        new MicroserviceApplication().run(args);
    }

    @Override
    public String getName() {
        return "micro-servicey";
    }

    @Override
    public void initialize(Bootstrap<MicroserviceConfiguration> bootstrap) {
        super.initialize(bootstrap);
        final boolean strict = false;
        bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(strict)));
    }

    @Override
    public void run(MicroserviceConfiguration microserviceConfiguration, Environment environment) throws Exception {
        environment.healthChecks().register("always-healthy-healthcheck", new AlwaysHealthyHealthcheck());
        environment.jersey().register(new ClacksOverheadFeature(microserviceConfiguration.getClacksOverhead()));
        environment.jersey().register(new RootResource(microserviceConfiguration.getMessage(), HostnameHelper.getHostname(), getVersion()));
    }

    private String getVersion() {
        return Optional.fromNullable(RootResource.class.getPackage().getImplementationVersion()).or("not set");
    }
}

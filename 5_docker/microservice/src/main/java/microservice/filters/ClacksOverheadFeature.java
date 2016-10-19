package microservice.filters;

import microservice.annotations.ClacksOverhead;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

public class ClacksOverheadFeature implements DynamicFeature {

    private final String clacksOverhead;

    public ClacksOverheadFeature(String clacksOverhead) {
        this.clacksOverhead = clacksOverhead;
    }

    @Override
    public void configure(ResourceInfo resourceInfo, FeatureContext featureContext) {
        if(resourceInfo.getResourceMethod().getAnnotation(ClacksOverhead.class) != null ||
                resourceInfo.getResourceClass().getAnnotation(ClacksOverhead.class) != null) {
            featureContext.register(new ClacksOverheadFilter(clacksOverhead));
        }
    }
}

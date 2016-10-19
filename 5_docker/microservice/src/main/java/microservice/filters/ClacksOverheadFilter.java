package microservice.filters;

import com.google.common.collect.ImmutableList;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;
import java.text.MessageFormat;

public class ClacksOverheadFilter implements ContainerResponseFilter {

    private final String clacksOverhead;

    public ClacksOverheadFilter(String clacksOverhead) {
        this.clacksOverhead = clacksOverhead;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        containerResponseContext.getHeaders().put("X-GNU-Clacks-Overhead", ImmutableList.of(MessageFormat.format("GNU {0}", clacksOverhead)));
    }
}

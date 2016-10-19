package microservice.filters;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClacksOverheadFilterTest {

    @Test
    public void addsClacksOverhead() throws IOException {
        ClacksOverheadFilter clacksOverheadFilter = new ClacksOverheadFilter("CLACKS");
        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        ContainerResponseContext containerResponseContext = mock(ContainerResponseContext.class);
        final MultivaluedMap<String, Object> map = mock(MultivaluedHashMap.class);
        when(containerResponseContext.getHeaders()).thenReturn(map);

        clacksOverheadFilter.filter(containerRequestContext, containerResponseContext);

        ArgumentCaptor<String> headerName = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<List> headerValue = ArgumentCaptor.forClass(List.class);
        verify(map, times(1)).put(headerName.capture(), headerValue.capture());
        assertThat(headerName.getValue()).isEqualTo("X-GNU-Clacks-Overhead");
        assertThat(headerValue.getValue().get(0)).isEqualTo("GNU CLACKS");

    }

}
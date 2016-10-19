package microservice.filters;

import microservice.annotations.ClacksOverhead;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import java.lang.reflect.Method;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClacksOverheadFeatureTest {

    @ClacksOverhead
    private void clacksEnabled() {}

    private void clacksDisabled() {}

    @ClacksOverhead
    private class ClacksEnabled {}

    private class ClacksDisabled {}

    @Test
    public void shouldAddFilterWhenMethodAnnotated() throws NoSuchMethodException {
        test("clacksEnabled", 1, ClacksOverheadFeatureTest.class);
    }

    @Test
    public void shouldNotAddFilterWhenMethodNotAnnotated() throws NoSuchMethodException {
        test("clacksDisabled", 0, ClacksOverheadFeatureTest.class);
    }

    @Test
    public void shouldAddFilterWhenClassAnnotated() throws NoSuchMethodException {
        test("clacksDisabled", 1, ClacksEnabled.class);
    }

    @Test
    public void shouldNotAddFilterWhenClassNotAnnotated() throws NoSuchMethodException {
        test("clacksDisabled", 0, ClacksDisabled.class);
    }

    private void test(String testMethod, int times, Class clazz) throws NoSuchMethodException {
        ClacksOverheadFeature clacksOverheadFeature = new ClacksOverheadFeature("CLACKS");
        ResourceInfo resourceInfo = mock(ResourceInfo.class);
        FeatureContext featureContext = mock(FeatureContext.class);
        Method method = ClacksOverheadFeatureTest.class.getDeclaredMethod(testMethod);
        when(resourceInfo.getResourceMethod()).thenReturn(method);
        when(resourceInfo.getResourceClass()).thenReturn(clazz);

        clacksOverheadFeature.configure(resourceInfo, featureContext);

        verify(featureContext, times(times)).register(any(ClacksOverheadFilter.class));
    }
}
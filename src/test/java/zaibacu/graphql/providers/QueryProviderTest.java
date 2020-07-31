package zaibacu.graphql.providers;

import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.assertEquals;

public class QueryProviderTest {
    private class TestInnerResult implements Serializable {
        private String other;
    }
    private class TestResult implements Serializable{
        private String name;
        private int age;
        private TestInnerResult inner;
    }


    @Test
    public void testBasicQueryWoParams(){
        QueryProvider provider = new QueryProvider()
                .withName("test");

        String expected = "test{name age inner{other}}";

        assertEquals(expected, provider.toQuery(TestResult.class));
    }

    @Test
    public void testBasicQueryWParams(){
        QueryProvider provider = new QueryProvider()
                .withName("test")
                .withParameter("id", "123");

        String expected = "test(id: \"123\"){name age inner{other}}";

        assertEquals(expected, provider.toQuery(TestResult.class));
    }
}

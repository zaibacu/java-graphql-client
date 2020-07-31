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
}

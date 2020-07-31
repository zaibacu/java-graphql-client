package zaibacu.graphql.providers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueryProviderTest {

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
                .withParameter("id", "123")
                .withParameter("number", 321);

        String expected = "test(id: \"123\", number: 321){name age inner{other}}";

        assertEquals(expected, provider.toQuery(TestResult.class));
    }
}

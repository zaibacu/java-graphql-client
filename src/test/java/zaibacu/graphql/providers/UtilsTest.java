package zaibacu.graphql.providers;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class UtilsTest {
    @Test
    public void testParseResult() throws IOException {
        String json = "{\"results\": [{\"name\": \"test1\", \"age\": 1, \"inner\": {\"other\": \"test\"}}," +
                        "{\"name\": \"test2\", \"age\": 2, \"inner\": {\"other\": \"test\"}}]}";

        TestResult[] results = Utils.parseJson(json, "results", TestResult[].class);

        assertEquals(2, results.length);

        assertEquals("test1", results[0].getName());
        assertEquals("test2", results[1].getName());
    }
}

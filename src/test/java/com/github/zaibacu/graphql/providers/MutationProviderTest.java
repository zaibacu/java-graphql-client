package com.github.zaibacu.graphql.providers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MutationProviderTest {
    @Test
    public void testBasicMutation(){
        MutationProvider provider = new MutationProvider()
                .withName("doTest")
                .withParameter("input", 123);

        String expected = "mutation{doTest(input: 123){result{name age inner{other}}}}";

        assertEquals(expected, provider.toMutation("result", TestResult.class));
    }
}

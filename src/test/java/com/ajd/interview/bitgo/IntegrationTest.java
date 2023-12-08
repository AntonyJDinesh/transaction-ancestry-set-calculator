package com.ajd.interview.bitgo;

import org.junit.jupiter.api.Test;

public class IntegrationTest {
    @Test
    public void integrationTest() {
        var blockStreamClient = new BlockStreamClient("680000", HttpClientImpl.getHttpClient());
        var cal = new TransactionAncestrySetCalculatorImpl(blockStreamClient.getTxnParentData());
        var res = cal.getTopAncestrySetSize(5);
        System.out.println(res);
    }
}

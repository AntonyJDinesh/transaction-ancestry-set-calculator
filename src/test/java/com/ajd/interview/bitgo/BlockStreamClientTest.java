package com.ajd.interview.bitgo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BlockStreamClientTest {
    @Test
    public void getBlockStreamClient() {
        var blockStreamClientObj = new BlockStreamClient("680000", HttpClientImpl.getHttpClient());
        var txnParentData = blockStreamClientObj.getTxnParentData();
        System.out.println("txnParentData.size(): " + txnParentData.size());
        Assertions.assertNotEquals(0, txnParentData.size());
    }
}

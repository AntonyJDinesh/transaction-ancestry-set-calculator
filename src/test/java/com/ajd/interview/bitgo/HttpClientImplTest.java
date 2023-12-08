package com.ajd.interview.bitgo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HttpClientImplTest {

    @Test
    public void getTest() {
        String content = HttpClientImpl.getHttpClient().get("https://blockstream.info/api/blocks/680000");
        Assertions.assertNotNull(content);
    }
}

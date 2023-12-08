package com.ajd.interview.bitgo;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpClientImpl implements HttpClient {
    private static final HttpClientImpl INSTANCE = new HttpClientImpl();
    private final OkHttpClient httpClient;

    private HttpClientImpl() { httpClient = new OkHttpClient(); }

    public static HttpClient getHttpClient() { return INSTANCE; }
    @Override
    public String get(String url) {
        var request = new Request.Builder().url(url).build();
        try (var response = httpClient.newCall(request).execute()){
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

package com.lvnlx.api.change.notifier.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvnlx.api.change.notifier.enumeration.Method;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpService {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T sendRequest(Method method, String uri, Class<T> template) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().method(method.name(), HttpRequest.BodyPublishers.noBody()).uri(URI.create(uri)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), template);
    }

    public static void sendRequest(Method method, String uri, String body, String... headers) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().method(method.name(), HttpRequest.BodyPublishers.ofString(body)).headers(headers).uri(URI.create(uri)).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}

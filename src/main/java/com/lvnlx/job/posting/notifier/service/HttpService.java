package com.lvnlx.job.posting.notifier.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvnlx.job.posting.notifier.enumeration.Method;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class HttpService {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T sendRequest(Method method, String uri, Class<T> template) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().method(method.name(), HttpRequest.BodyPublishers.noBody()).uri(URI.create(uri)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), template);
    }

    public void sendRequest(Method method, String uri, String body, String... headers) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().method(method.name(), HttpRequest.BodyPublishers.ofString(body)).headers(headers).uri(URI.create(uri)).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}

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

    public <T> T sendGetRequest(String uri, Class<T> responseTemplate) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().method(Method.GET.name(), HttpRequest.BodyPublishers.noBody()).uri(URI.create(uri)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), responseTemplate);
    }

    public <T, U> U sendPostRequest(String uri, T requestBody, Class<U> responseTemplate) throws IOException, InterruptedException {
        String body = objectMapper.writeValueAsString(requestBody);
        HttpRequest request = HttpRequest.newBuilder().method(Method.POST.name(), HttpRequest.BodyPublishers.ofString(body)).uri(URI.create(uri)).setHeader("Content-Type", "application/json").build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), responseTemplate);
    }

    public void sendRequest(Method method, String uri, String body, String... headers) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().method(method.name(), HttpRequest.BodyPublishers.ofString(body)).headers(headers).uri(URI.create(uri)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 300) {
            throw new IOException(String.format("Received status code %d", response.statusCode()));
        }
    }
}

package ui;


import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;
import java.util.Map;


public class Client
{
    // Create an HttpClient for making requests
    // This should be long-lived and shared, so a static final field is good here
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public static void main(String[] args) throws Exception {
        new Client().get("localhost", 8080, "/name");
    }

    private void get(String host, int port, String path) throws Exception {
        System.out.print("Made new Client!!!");
        String urlString = String.format(Locale.getDefault(), "http://%s:%d%s", host, port, path);
        System.out.print(urlString);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(urlString))
                .timeout(java.time.Duration.ofMillis(5000))
                .GET()
                .build();

        System.out.print(request);

        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.print(httpResponse);

        if (httpResponse.statusCode() >= 200 && httpResponse.statusCode() < 300) {
            System.out.println(httpResponse.body());
        } else {
            System.out.println("Error: received status code " + httpResponse.statusCode());
        }
    }

    private static HttpResponse<String> sendRequest(String url, String method, String body)
    {
        var request = HttpRequest.newBuilder(URI.create(url))
                .method(method, requestBodyPublisher(body))
                .build();
        return httpClient.send(request, BodyHandlers.ofString());
    }

    private static HttpRequest.BodyPublisher requestBodyPublisher(String body) {
        if (body != null) {
            return HttpRequest.BodyPublishers.ofString(body);
        } else {
            return HttpRequest.BodyPublishers.noBody();
        }
    }

    private static void receiveResponse(HttpResponse<String> response) {
        var statusCode = response.statusCode();

        var responseBody = new Gson().fromJson(response.body(), Map.class);
        System.out.printf("= Response =========\n[%d]\n\n%s\n\n", statusCode, responseBody);
    }



}

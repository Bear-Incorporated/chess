package websocket;


import com.google.gson.Gson;
// import dataaccess.DataAccessException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;


public class HttpTalker
{
    // Create an HttpClient for making requests
    // This should be long-lived and shared, so a static final field is good here
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    private static final int TIMEOUT_MILLIS = 5000;

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) throws Exception {
        // new HttpTalker().get(SERVER_HOST, SERVER_PORT, "/name");
    }





    public String get(String urlPath, String authToken) throws Exception
    {
        String urlString = String.format("http://%s:%d%s", SERVER_HOST, SERVER_PORT, urlPath);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(urlString))
                .timeout(java.time.Duration.ofMillis(TIMEOUT_MILLIS))
                .header("authorization", authToken)
                .GET()
                .build();

        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if(httpResponse.statusCode() == 200) {
            HttpHeaders headers = httpResponse.headers();
            Optional<String> lengthHeader = headers.firstValue("Content-Length");

            System.out.printf("Received %s bytes%n", lengthHeader.orElse("unknown"));
            System.out.println(httpResponse.body());

            return httpResponse.body();
        } else {
            System.out.println("Error: received status code " + httpResponse.statusCode());

            System.out.println(httpResponse.body());

            throw new Exception(Integer.toString(httpResponse.statusCode()));
        }
    }

    public String put(String urlPath, String authToken, String body) throws Exception
    {
        String urlString = String.format("http://%s:%d%s", SERVER_HOST, SERVER_PORT, urlPath);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(urlString))
                .timeout(java.time.Duration.ofMillis(TIMEOUT_MILLIS))
                .header("authorization", authToken)
                .PUT(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if(httpResponse.statusCode() == 200) {
            HttpHeaders headers = httpResponse.headers();
            Optional<String> lengthHeader = headers.firstValue("Content-Length");

            System.out.printf("Received %s bytes%n", lengthHeader.orElse("unknown"));
            System.out.println(httpResponse.body());

            return httpResponse.body();
        } else {
            System.out.println("Error: received status code " + httpResponse.statusCode());

            System.out.println(httpResponse.body());

            throw new Exception(Integer.toString(httpResponse.statusCode()));
        }
    }
    public String post(String urlPath, String authToken, String body) throws Exception
    {
        String urlString = String.format("http://%s:%d%s", SERVER_HOST, SERVER_PORT, urlPath);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(urlString))
                .timeout(java.time.Duration.ofMillis(TIMEOUT_MILLIS))
                .header("authorization", authToken)
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if(httpResponse.statusCode() == 200) {
            HttpHeaders headers = httpResponse.headers();
            Optional<String> lengthHeader = headers.firstValue("Content-Length");

            System.out.printf("Received %s bytes%n", lengthHeader.orElse("unknown"));
            System.out.println(httpResponse.body());

            return httpResponse.body();
        } else {
            System.out.println("Error: received status code " + httpResponse.statusCode());

            System.out.println(httpResponse.body());

            throw new Exception(Integer.toString(httpResponse.statusCode()));
        }
    }

    public void delete(String urlPath, String authToken) throws Exception
    {
        String urlString = String.format("http://%s:%d%s", SERVER_HOST, SERVER_PORT, urlPath);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(urlString))
                .timeout(java.time.Duration.ofMillis(TIMEOUT_MILLIS))
                .header("authorization", authToken)
                .DELETE()
                .build();

        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if(httpResponse.statusCode() == 200) {
            HttpHeaders headers = httpResponse.headers();
            Optional<String> lengthHeader = headers.firstValue("Content-Length");

            System.out.printf("Received %s bytes%n", lengthHeader.orElse("unknown"));
            System.out.println(httpResponse.body());


        } else {
            System.out.println("Error: received status code " + httpResponse.statusCode());

            System.out.println(httpResponse.body());

            throw new Exception(Integer.toString(httpResponse.statusCode()));

        }
    }



//    private static HttpResponse<String> sendRequest(String url, String method, String body)
//    {
//        var request = HttpRequest.newBuilder(URI.create(url))
//                .method(method, requestBodyPublisher(body))
//                .build();
//        return httpClient.send(request, BodyHandlers.ofString());
//    }

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

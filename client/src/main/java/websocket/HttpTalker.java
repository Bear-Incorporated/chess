package websocket;


import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
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
        new HttpTalker().get(SERVER_HOST, SERVER_PORT, "/name");
    }


    public void get(String path) throws Exception {
        get(SERVER_HOST, SERVER_PORT, path);
    }

    public void get(String host, int port, String path) throws Exception {
        System.out.print("Made new Client!!!\n");
        String urlString = String.format(Locale.getDefault(), "http://%s:%d%s", host, port, path);
        System.out.print(urlString + "\n");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(urlString))
                .timeout(java.time.Duration.ofMillis(TIMEOUT_MILLIS))
                .header("authorization", "abc123")
                .GET()
                .build();

        System.out.print(request + "\n");

        HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.print(httpResponse + "\n");

        if (httpResponse.statusCode() >= 200 && httpResponse.statusCode() < 300) {
            System.out.println(httpResponse.body());
        } else {
            System.out.println("Error: received status code " + httpResponse.statusCode());
        }
    }



    public void postSession(String username, String password) throws URISyntaxException, IOException, InterruptedException
    {
        String urlString = String.format("http://%s:%d%s", SERVER_HOST, SERVER_PORT, "/session");

        System.out.println(String.format(
                """
                  {
                  "username": "%s",
                  "password": "%s"
                  }
                  """, username, password));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(urlString))
                .timeout(java.time.Duration.ofMillis(TIMEOUT_MILLIS))
                .header("authorization", "abc123")
                .POST(HttpRequest.BodyPublishers.ofString(String.format(
                    """
                      {
                      "username": "%s",
                      "password": "%s"
                      }
                      """, username, password), StandardCharsets.UTF_8))
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
        }
    }

    public void postUser(String username, String password, String email) throws URISyntaxException, IOException, InterruptedException
    {
        String urlString = String.format("http://%s:%d%s", SERVER_HOST, SERVER_PORT, "/user");

        System.out.println(String.format(
                """
                  {
                  "username": "%s",
                  "password": "%s"
                  }
                  """, username, password));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(urlString))
                .timeout(java.time.Duration.ofMillis(TIMEOUT_MILLIS))
                .header("authorization", "abc123")
                .POST(HttpRequest.BodyPublishers.ofString(String.format(
                        """
                          {
                          "username": "%s",
                          "password": "%s"
                          "email": "%s"
                          }
                          """, username, password, email), StandardCharsets.UTF_8))
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
        }
    }




    public void post(String host, int port, String urlPath, String message) throws URISyntaxException, IOException, InterruptedException
    {
        String urlString = String.format("http://%s:%d%s", host, port, urlPath);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(urlString))
                .timeout(java.time.Duration.ofMillis(TIMEOUT_MILLIS))
                .header("authorization", "abc123")
                .POST(HttpRequest.BodyPublishers.ofString(message, StandardCharsets.UTF_8))
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

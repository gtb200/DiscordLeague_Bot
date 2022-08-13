package com.example.botdemo.biz.classes;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class KanyeUtils {
    public static Optional<String> getQuote() {
        try {
            HttpRequest req = HttpRequest.newBuilder(new URI("http://api.kanye.rest/get")).GET().build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(req, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = new Gson().fromJson(response.body(), JsonObject.class);
            return Optional.of(jsonObject.get("quote").getAsString());
        } catch (NullPointerException | URISyntaxException | IOException | InterruptedException e) {
            return Optional.empty();
        }
    }
}

package com.pitufos.absolutemovies.components;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class GPTClient {

    private static final String API_KEY = System.getenv("OPENAI_API_KEY");
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String MODEL = "gpt-4o";

    private final HttpClient client;

    public GPTClient() {
        this.client = HttpClient.newHttpClient();
    }

    public Optional<String> chamarIA(String prompt) {
        String body = String.format("""
            {
                "model": "%s",
                "messages": [{"role": "user", "content": "%s"}],
                "max_tokens": 50
            }
            """, MODEL, prompt);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return Optional.of(parseRespostaGPT(response.body()));
            } else {
                System.err.println("Erro na chamada à API: " + response.statusCode());
                return Optional.empty();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private String parseRespostaGPT(String responseJson) {
        try {
            JSONObject json = new JSONObject(responseJson);
            return json.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                    .trim();
        } catch (org.json.JSONException e) {
            e.printStackTrace();
            return "Desculpe, não consegui processar sua solicitação.";
        }
    }
}

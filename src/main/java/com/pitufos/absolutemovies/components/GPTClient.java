package com.pitufos.absolutemovies.components;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GPTClient {

    @Value("${openai.api.key}")
    private String API_KEY;

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String MODEL = "gpt-4o-mini"; // Atualizado — o 3.5 foi descontinuado

    private final HttpClient client;

    public GPTClient() {
        this.client = HttpClient.newHttpClient();
    }

    public Optional<String> chamarIA(String prompt) {
        try {
            JSONObject message = new JSONObject()
                    .put("role", "user")
                    .put("content", prompt);

            JSONObject bodyJson = new JSONObject()
                    .put("model", "gpt-4o-mini")
                    .put("messages", new org.json.JSONArray().put(message))
                    .put("max_tokens", 100);

            String body = bodyJson.toString(2); // bonito para log

            System.out.println("\n🔸 Corpo JSON enviado à API:\n" + body + "\n");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Authorization", "Bearer " + API_KEY)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("🔸 Resposta da API OpenAI:");
            System.out.println("Status: " + response.statusCode());
            System.out.println("Body:\n" + response.body());
            System.out.println("============================");

            if (response.statusCode() == 200) {
                return Optional.of(parseRespostaGPT(response.body()));
            } else {
                System.err.println("❌ Erro na chamada à API. Código HTTP: " + response.statusCode());
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
            System.err.println("❌ Erro ao interpretar JSON da resposta:");
            e.printStackTrace();
            System.err.println("Resposta original recebida:");
            System.err.println(responseJson);
            return "Desculpe, não consegui processar sua solicitação.";
        }
    }
}

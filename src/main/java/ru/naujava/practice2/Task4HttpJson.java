package ru.naujava.practice2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * 4. Задание №4. HTTP клиент и JSON.
 * <p>
 * Необходимо сделать "GET" запрос на указанный адрес и обработать ответ. Запрос
 * выполняется на тестовый сервер по адресу "https://httpbin.org/". Сервер возвращает
 * ответ в формате JSON. Из ответа необходимо извлечь и вывести в консоль информацию
 * в соответствии со своим вариантом.
 * <p>
 * Вариант 2. Вывести только значение идентификационной строки приложения с которого
 * выполняется запрос (запрос выполняется по адресу "https://httpbin.org/user-agent").
 * <p>
 * Выходные данные: в консоль напечатан результат выполнения запроса, обработанный
 * в соответствии с вариантом задания.
 */
public class Task4HttpJson {

    private static final String URL = "https://httpbin.org/user-agent";
    private static final String USER_AGENT_FIELD = "user-agent";

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Выполняет синхронный GET-запрос и возвращает тело ответа.
     *
     * @return тело ответа в формате JSON
     * @throws IOException          при ошибке сети
     * @throws InterruptedException если поток был прерван во время запроса
     */
    private String fetch() throws IOException, InterruptedException {
        try (HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IOException("Неожиданный код ответа: " + response.statusCode());
            }
            return response.body();
        }
    }

    /**
     * Извлекает значение поля "user-agent" из JSON-ответа.
     *
     * @param json тело ответа
     * @return значение идентификационной строки приложения
     * @throws IOException если JSON некорректен или поле отсутствует
     */
    private String extractUserAgent(String json) throws IOException {
        JsonNode root = objectMapper.readTree(json);
        JsonNode userAgent = root.get(USER_AGENT_FIELD);
        if (userAgent == null || userAgent.isNull()) {
            throw new IOException("В ответе отсутствует поле \"" + USER_AGENT_FIELD + "\"");
        }
        return userAgent.asText();
    }

    /**
     * Точка входа задания: выполняет запрос и печатает User-Agent.
     */
    public void run() {
        System.out.println("GET " + URL);
        try {
            String body = fetch();
            System.out.println("User-Agent: " + extractUserAgent(body));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Запрос прерван: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Не удалось выполнить запрос: " + e.getMessage());
        }
    }
}

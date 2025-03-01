package com.geoloc;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class OpenWeatherApiClient {
    private static final String API_KEY = Config.API_KEY;
    private static final String BASE_URL_NAME = "http://api.openweathermap.org/geo/1.0/direct";
    private static final String BASE_URL_ZIP = "http://api.openweathermap.org/geo/1.0/zip";

    public LocationData getCoordinates(String location) {
        try {
            String jsonResponse;
            if (location.matches("\\d{5}")) {
                jsonResponse = fetchData(BASE_URL_ZIP + "?zip=" + location + ",US&appid=" + API_KEY);
            } else {
                String formattedLocation = location.replace(" ", "%20");
                jsonResponse = fetchData(BASE_URL_NAME + "?q=" + formattedLocation + ",US&limit=1&appid=" + API_KEY);
            }

            return parseJsonResponse(jsonResponse);
        } catch (Exception e) {
            System.out.println("Error fetching data: " + e.getMessage());
            return null;
        }
    }

    private String fetchData(String apiUrl) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("HTTP Error: " + responseCode);
        }

        Scanner scanner = new Scanner(conn.getInputStream());
        StringBuilder response = new StringBuilder();
        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();
        return response.toString();
    }

    private LocationData parseJsonResponse(String jsonResponse) {
        try {
            JSONArray jsonArray = new JSONArray(jsonResponse);
            if (jsonArray.isEmpty()) {
                return null;
            }

            JSONObject jsonObject = jsonArray.getJSONObject(0);
            return new LocationData(
                    jsonObject.getDouble("lat"),
                    jsonObject.getDouble("lon"),
                    jsonObject.optString("name", "Unknown"));
        } catch (Exception e) {
            try {

                JSONObject jsonObject = new JSONObject(jsonResponse);
                return new LocationData(
                        jsonObject.getDouble("lat"),
                        jsonObject.getDouble("lon"),
                        jsonObject.optString("name", "Unknown"));
            } catch (Exception ex) {

                return null;
            }
        }
    }
}

package com.geoloc;

import java.util.Arrays;
import java.util.List;

public class GeolocationUtil {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java -jar geoloc-util.jar \"City, State\" \"ZIP\"");
            return;
        }

        OpenWeatherApiClient apiClient = new OpenWeatherApiClient();
        List<String> locations = Arrays.asList(args);

        for (String location : locations) {
            LocationData data = apiClient.getCoordinates(location);
            if (data != null) {
                System.out.printf("%s -> Latitude: %f, Longitude: %f, Name: %s%n",
                        location, data.getLatitude(), data.getLongitude(), data.getName());
            } else {
                System.out.printf("Error fetching data for: %s%n", location);
            }
        }
    }
}

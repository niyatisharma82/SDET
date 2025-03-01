package com.geoloc;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class GeolocationUtilTest {
    private static WireMockServer wireMockServer;

    @BeforeAll
    public static void setup() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();
    }

    @Test
    public void testValidCityState() {
        stubFor(get(urlMatching("/geo/1.0/direct.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("[{\"lat\":40.7128, \"lon\":-74.0060, \"name\":\"New York\"}]")));

        OpenWeatherApiClient client = new OpenWeatherApiClient();
        LocationData data = client.getCoordinates("New York, NY");

        assertNotNull(data);
        assertEquals("New York", data.getName());
        assertEquals(40.7128, data.getLatitude());
        assertEquals(-74.0060, data.getLongitude());
    }

    @Test
    public void testInvalidCity() {
        stubFor(get(urlMatching("/geo/1.0/direct.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("[]")));

        OpenWeatherApiClient client = new OpenWeatherApiClient();
        LocationData data = client.getCoordinates("Invalid City");

        assertNull(data);
    }

    @Test
    public void testValidZipCode() {
        stubFor(get(urlMatching("/geo/1.0/zip.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("[{\"lat\":40.7128, \"lon\":-74.0060, \"name\":\"New York\"}]")));

        OpenWeatherApiClient client = new OpenWeatherApiClient();
        LocationData data = client.getCoordinates("10001");

        assertNotNull(data);
        assertEquals("New York", data.getName());
        assertEquals(40.7128, data.getLatitude());
        assertEquals(-74.0060, data.getLongitude());
    }

    @Test
    public void testMultipleLocations() {
        stubFor(get(urlMatching("/geo/1.0/direct.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("[{\"lat\":40.7128, \"lon\":-74.0060, \"name\":\"New York\"}]")));
        stubFor(get(urlMatching("/geo/1.0/zip.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("[{\"lat\":34.0522, \"lon\":-118.2437, \"name\":\"Los Angeles\"}]")));

        OpenWeatherApiClient client = new OpenWeatherApiClient();

        // Test multiple locations
        LocationData cityData = client.getCoordinates("New York, NY");
        LocationData zipData = client.getCoordinates("90001");

        assertNotNull(cityData);
        assertEquals("New York", cityData.getName());
        assertEquals(40.7128, cityData.getLatitude());
        assertEquals(-74.0060, cityData.getLongitude());

        assertNotNull(zipData);
        assertEquals("Los Angeles", zipData.getName());
        assertEquals(34.0522, zipData.getLatitude());
        assertEquals(-118.2437, zipData.getLongitude());

    }

    @Test
    public void testErrorHandling() {
        stubFor(get(urlMatching("/geo/1.0/direct.*"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("{\"error\":\"Server error\"}")));

        OpenWeatherApiClient client = new OpenWeatherApiClient();
        LocationData data = client.getCoordinates("New York, NY");

        assertNull(data);
    }

}

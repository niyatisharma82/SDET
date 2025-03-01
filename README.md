
# Fetch SDET Test
This project provides a command-line utility for fetching geolocation data (latitude and longitude) for cities, states, and ZIP codes using the OpenWeatherMap API. The utility can handle multiple location inputs and return relevant information, including latitude, longitude, and place name. Integration tests are provided using WireMock to mock API responses.

## Project Structure

```bash
SDET/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── geoloc/
│   │   │   │   │   ├── GeolocationUtil.java  # Main CLI Utility
│   │   │   │   │   ├── OpenWeatherApiClient.java  # API Client
│   │   │   │   │   ├── LocationData.java  # Data Model for Location
│   │   │   │   │   ├── Config.java  # API Key Handling
│   ├── test/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── geoloc/
│   │   │   │   │   ├── GeolocationUtilTest.java  # Integration Tests
├── pom.xml  # Maven Build File
├── README.md  # Documentation
├── .gitignore  # Ignore API keys, build files

```

## Prerequisites

- **JDK 11 or higher**: Ensure you have Java 11 or higher installed.
- **Maven**: The project uses Maven for build and dependency management.

## Installation

1. **Clone the Repository**:

    ```bash
    git clone https://github.com/niyatisharma82/SDET.git
    cd SDET
    ```

2. **Set Up API Key**:
    - Obtain an API key from OpenWeatherMap by signing up at [OpenWeatherMap](https://openweathermap.org/).
    - Set the `OPENWEATHER_API_KEY` environment variable with your API key:

      **For Linux/macOS:**

      ```bash
      export OPENWEATHER_API_KEY=your_api_key_here
      ```

      **For Windows (Command Prompt):**

      ```bash
      set OPENWEATHER_API_KEY=your_api_key_here
      ```

## Usage

To run the utility from the command line, use the following command:

```bash
mvn clean test

java -jar target/geoloc-util-1.0.jar "City, State" "ZIP"

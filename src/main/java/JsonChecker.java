import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.stream.Collectors;

public class JsonChecker {
    private Gson gson;

    public JsonChecker() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public boolean isValid(String json) {
        try {
            JsonParser.parseString(json);
        } catch (JsonSyntaxException e) {
            return false;
        }
        return true;
    }

    public void processJsonFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Print raw JSON content before parsing
            String rawJson = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println("Raw JSON content:");
            System.out.println(rawJson);

            try (JsonReader jsonReader = new JsonReader(new StringReader(rawJson))) {
                jsonReader.setLenient(true);

                // Attempt to parse JSON
                JsonElement jsonElement = gson.fromJson(jsonReader, JsonElement.class);
                String jsonString = gson.toJson(jsonElement);

                System.out.println("Content of the JSON file as a string:");
                System.out.println(jsonString);

                // Check if the JSON is valid
                if (isValid(jsonString)) {
                    System.out.println("The JSON is valid.");
                } else {
                    System.out.println("The JSON is not valid.");
                }
            } catch (JsonSyntaxException e) {
                System.out.println("Error while parsing JSON: " + e.getMessage());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

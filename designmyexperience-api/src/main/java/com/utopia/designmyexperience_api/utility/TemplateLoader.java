package com.utopia.designmyexperience_api.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class TemplateLoader {

    public static String load(String path, Map<String, String> variables) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/" + path)));
            for (Map.Entry<String, String> entry : variables.entrySet()) {
                content = content.replace("${" + entry.getKey() + "}", entry.getValue());
            }
            return content;
        } catch (IOException e) {
            System.err.println("❌ Failed to load template: " + e.getMessage());
            return "";
        }
    }
}
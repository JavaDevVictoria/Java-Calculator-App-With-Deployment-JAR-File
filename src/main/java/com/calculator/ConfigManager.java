package com.calculator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private Properties properties;

    public ConfigManager() throws IOException {
        loadConfiguration();
    }

    private void loadConfiguration() throws IOException {
        properties = new Properties();

        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("app.properties")) {

            if (input == null) {
                throw new IOException("Configuration file not found");
            }
            properties.load(input);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }

    public int getIntProperty(String key, int defaultValue) {
        String value = properties.getProperty(key);
        try {
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
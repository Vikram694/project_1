package com.coursera.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {
        try {
            properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties");
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load config.properties file");
        }
    }

    /**
     * Get property value by key
     * @param key Property key
     * @return Property value
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get property value by key with default value
     * @param key Property key
     * @param defaultValue Default value if key not found
     * @return Property value or default
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Get property as integer
     * @param key Property key
     * @return Property value as integer
     */
    public static int getIntProperty(String key) {
        return Integer.parseInt(properties.getProperty(key, "0"));
    }

    /**
     * Get property as boolean
     * @param key Property key
     * @return Property value as boolean
     */
    public static boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(properties.getProperty(key, "false"));
    }
}

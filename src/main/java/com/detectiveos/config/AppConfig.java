package com.detectiveos.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class AppConfig {
    private static final Properties PROPERTIES = new Properties();
    static {
        try (InputStream in = AppConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (in == null) throw new IllegalStateException("application.properties not found");
            PROPERTIES.load(in);
        } catch (IOException e) { throw new ExceptionInInitializerError(e); }
    }
    private AppConfig() {}
    public static String get(String key){ return System.getProperty(key, PROPERTIES.getProperty(key)); }
}

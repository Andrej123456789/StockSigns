package me.andrej123456789.stocksigns.config;

import java.io.File;

import com.moandjiezana.toml.Toml;

/**
 * Class handling .TOML configs
 */
public class Config {
    
    private static File file;
    private static Toml toml;

    /**
     * Generate new .TOML config
     * @param path path to the .TOML file
     */
    Config(String path) {
        file = new File(path);
        toml = new Toml().read(file);
    }

    /**
     * Get a string from .TOML file
     * @param key
     * @return
     */
    String geString(String key) {
        return toml.getString(key);
    }
}

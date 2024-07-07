package me.andrej123456789.stocksigns.config;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;
import java.io.IOException;

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
    public Config(String path) {
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

    public ArrayList<String> getTables() {
        ArrayList<String> result = new ArrayList<String>();
        Map<String, Object> map = toml.toMap();
        
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            
            // Check if the value is a nested table
            if (value instanceof Map) {
                result.add(key);
            }
        }

        return result;
    }

    public String writeEmptyTable(String name) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write("[" + name + "]\n");

            writer.close();
        } catch (IOException e) {
            return e.toString();
        }
        
        return "";
    }
}

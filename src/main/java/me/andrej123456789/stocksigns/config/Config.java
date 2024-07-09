package me.andrej123456789.stocksigns.config;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;

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
     * Reload file
     */
    public void reload() {
        toml = new Toml().read(file);
    }

    /**
     * Get a string from .TOML file
     * @param key
     * @return `String` type
     */
    public String geString(String key) {
        return toml.getString(key);
    }

    /**
     * Get all table names
     * @return array list containing table names
     */
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

    /**
     * Write table at the end of a file
     * @param tomlData class containing data
     * @param new_map_name value to replace variable name representing TOML table
     * @return empty string if ok, or exception
     */
    public String writeTable(Object tomlData, String oldName, String newName) {
        TomlWriter tomlWriter = new TomlWriter();
        String tomlString = tomlWriter.write(tomlData);

        tomlString = tomlString.replace(oldName, newName);
        tomlString = "\n" + tomlString;

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(tomlString);

            writer.close();
        } catch (IOException e) {
            return e.toString();
        }
        
        return "";
    }

    /**
     * Write new empty table at the end of a file
     * @param name name of new table
     * @return empty string if ok, or exception
     */
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

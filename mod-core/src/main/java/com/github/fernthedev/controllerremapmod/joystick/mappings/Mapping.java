package com.github.fernthedev.controllerremapmod.joystick.mappings;

import com.github.fernthedev.controllerremapmod.joystick.mappings.gson.GsonAxeMapping;
import com.github.fernthedev.controllerremapmod.joystick.mappings.gson.GsonButtonMapping;
import com.github.fernthedev.controllerremapmod.joystick.mappings.gson.GsonMapping;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;
import lombok.Getter;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

import java.io.File;
import java.io.IOException;

@Data
@Getter
public abstract class Mapping {

    protected GsonButtonMapping buttonMapping;
    protected GsonAxeMapping axesMapping;
    protected String name = "UnknownMapping";

    protected Mapping(ButtonMapping buttonMapping, AxesMapping axesMapping, String name) {
        this.buttonMapping = buttonMapping.toGson();
        this.axesMapping = axesMapping.toGson();
        this.name = name;
    }

    protected Mapping() {}

    public static GsonMapping loadFromFile(File file) {
        StringBuilder json = new StringBuilder();
        try (Source fileSource = Okio.source(file);
             BufferedSource bufferedSource = Okio.buffer(fileSource)) {

            while (true) {
                String line = bufferedSource.readUtf8Line();
                if (line == null) break;

                json.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return loadFromJSON(json.toString());
    }

    public static GsonMapping loadFromJSON(String json) {
        return new Gson().fromJson(json, GsonMapping.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public String toJsonPretty() {return new GsonBuilder().setPrettyPrinting().create().toJson(this); }
}

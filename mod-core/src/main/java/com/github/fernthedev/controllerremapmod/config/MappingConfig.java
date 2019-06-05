package com.github.fernthedev.controllerremapmod.config;

import com.github.fernthedev.controllerremapmod.mappings.Mapping;
import com.github.fernthedev.controllerremapmod.mappings.gson.GsonMapping;
import com.github.fernthedev.controllerremapmod.mappings.xbox.XboxOneMapping;
import com.google.gson.Gson;
import lombok.NonNull;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MappingConfig {

    public MappingConfig(File file,@NonNull Mapping mapping) {
        this(file);
        this.mapping = mapping;
    }

    private MappingConfig(File file) {
        this.file = file;
    }

    public static MappingConfig loadConfig(File file) {
        MappingConfig config = new MappingConfig(file);

        config.load();

        return config;
    }

    public void load()  {
        if(!file.exists()) {
            mapping = new XboxOneMapping();
            save();
        }

        try {
            mapping = new Gson().fromJson(new FileReader(file), GsonMapping.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (Sink fileSink = Okio.sink(file);
             BufferedSink bufferedSink = Okio.buffer(fileSink)) {

            bufferedSink.writeUtf8(mapping.toJsonPretty()).writeUtf8(System.lineSeparator());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sync() {
        save();
        load();
    }

    private File file;
    private Mapping mapping;

}

package com.github.fernthedev.controllerremapmod.config;

import com.github.fernthedev.controllerremapmod.mappings.Mapping;
import com.github.fernthedev.controllerremapmod.mappings.xbox.XboxOneMapping;
import com.google.gson.Gson;
import lombok.Data;
import lombok.NonNull;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

import java.io.File;
import java.io.IOException;

@Data
public class MappingConfig {


    private File file;


    private Mapping mapping;

    public MappingConfig(File file,@NonNull Mapping mapping) {
        this(file);
        this.mapping = mapping;
    }

    private MappingConfig(File file) {
        this.file = file;
        mapping = new XboxOneMapping();
    }

    public static MappingConfig loadConfig(File file) {
        MappingConfig config = new MappingConfig(file);

        config.load();

        return config;
    }

    public void load()  {
        if(!file.exists()) {
            save();
        }

        try {
            mapping = Mapping.loadFromFile(file);


        } catch (Exception e) {
            String map = "";
            if(mapping != null) {
                map = " as mapping " + mapping.getName();
            }

            new IOException("Unable to load " + file.getName() + map,e).printStackTrace();
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




}

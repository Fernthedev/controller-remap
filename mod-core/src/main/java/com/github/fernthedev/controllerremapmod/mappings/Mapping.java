package com.github.fernthedev.controllerremapmod.mappings;

import com.github.fernthedev.controllerremapmod.mappings.gson.GsonAxeMapping;
import com.github.fernthedev.controllerremapmod.mappings.gson.GsonButtonMapping;
import com.github.fernthedev.controllerremapmod.mappings.gson.GsonMapping;
import com.google.gson.Gson;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public abstract class Mapping {

    protected GsonButtonMapping buttonMapping;
    protected GsonAxeMapping axesMapping;
    protected String name = "UnknownMapping";

    protected Mapping(ButtonMapping buttonMapping,AxesMapping axesMapping,String name) {
        this.buttonMapping = buttonMapping.toGson();
        this.axesMapping = axesMapping.toGson();
        this.name = name;
    }

    protected Mapping() {}

    public static GsonMapping loadFromJSON(String json) {
        return new Gson().fromJson(json, GsonMapping.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}

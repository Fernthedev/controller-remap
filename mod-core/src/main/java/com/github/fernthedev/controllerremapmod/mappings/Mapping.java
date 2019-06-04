package com.github.fernthedev.controllerremapmod.mappings;

import com.google.gson.Gson;
import lombok.Data;

@Data
public abstract class Mapping {

    public abstract AxesMapping getAxesMapping();
    public abstract ButtonMapping getButtonMapping();

    public static Mapping loadFromJSON(String json) {
        return new Gson().fromJson(json,Mapping.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}

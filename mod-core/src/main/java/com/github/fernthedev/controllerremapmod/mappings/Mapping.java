package com.github.fernthedev.controllerremapmod.mappings;

import com.google.gson.Gson;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public abstract class Mapping {

    protected ButtonMapping buttonMapping;
    protected AxesMapping axesMapping;
    protected String name = "UnknownMapping";

    public Mapping() {}

    protected Mapping(ButtonMapping buttonMapping,AxesMapping axesMapping,String name) {
        this.buttonMapping = buttonMapping;
        this.axesMapping = axesMapping;
        this.name = name;
    }


    public static Mapping loadFromJSON(String json) {
        return new Gson().fromJson(json,Mapping.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}

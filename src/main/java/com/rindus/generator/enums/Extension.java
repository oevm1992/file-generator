package com.rindus.generator.enums;

public enum Extension {
    XML("xml"),
    JSON("json");

    Extension(String id) {
        this.id = id;
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

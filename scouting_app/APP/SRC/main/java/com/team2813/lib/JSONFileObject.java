package com.team2813.lib;

import org.json.JSONObject;

import java.io.File;

public class JSONFileObject {
    private String name;
    private JSONObject content;
    private File file;

    public JSONFileObject(String name, JSONObject content, File file){
        this.name = name;
        this.content = content;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public JSONObject getContent() {
        return content;
    }

    public File getFile() { return file; }
}

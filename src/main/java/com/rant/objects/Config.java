package com.rant.objects;

import java.util.HashMap;

public class Config {

    private String _id = "APPCONFIG";
    private String _rev;
    private HashMap<String, String> settings;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    public HashMap<String, String> getSettings() {
        return settings;
    }

    public void setSettings(HashMap<String, String> settings) {
        this.settings = settings;
    }
}

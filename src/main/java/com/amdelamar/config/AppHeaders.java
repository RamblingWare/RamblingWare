package com.amdelamar.config;

import java.util.List;

import com.amdelamar.objects.Header;

public class AppHeaders {

    private String _id = "APPHEADERS";
    private String _rev;
    private List<Header> headers;

    public String get_Id() {
        return _id;
    }

    public void set_Id(String _id) {
        this._id = _id;
    }

    public String get_Rev() {
        return _rev;
    }

    public void set_Rev(String _rev) {
        this._rev = _rev;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }
}

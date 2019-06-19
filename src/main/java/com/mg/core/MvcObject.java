package com.mg.core;

import java.util.Map;

public class MvcObject {
    private String msg;
    private String code;
    private Map map;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }


    public MvcObject() {
    }

    public MvcObject(String msg, String code, Map map) {
        this.msg = msg;
        this.code = code;
        this.map = map;
    }

    public MvcObject(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }
}

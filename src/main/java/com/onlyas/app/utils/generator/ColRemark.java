package com.onlyas.app.utils.generator;

public class ColRemark {
    private String objname;
    private String value;

    public String getObjname() {
        return objname;
    }

    public void setObjname(String objname) {
        this.objname = objname;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ColRemark{" +
                "objname='" + objname + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

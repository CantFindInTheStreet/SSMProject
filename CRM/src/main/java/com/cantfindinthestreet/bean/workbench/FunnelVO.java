package com.cantfindinthestreet.bean.workbench;

public class FunnelVO {
    private String name;
    private int value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "FunnelVO{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    public void setValue(int value) {
        this.value = value;
    }
}

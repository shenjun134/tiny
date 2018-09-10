package com.tiny.web.controller.integration.entity;

import java.util.Arrays;

public class FaxClassResult {

    private FaxClass[] classes;

    public FaxClass[] getClasses() {
        return classes;
    }

    public void setClasses(FaxClass[] classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "FaxClassResult{" +
                "classes=" + Arrays.toString(classes) +
                '}';
    }
}

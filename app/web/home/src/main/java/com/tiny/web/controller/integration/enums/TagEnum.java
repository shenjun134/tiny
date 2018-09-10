package com.tiny.web.controller.integration.enums;

public enum TagEnum {

    TH("th", "table.header"),

    TD("td", "table.body"),

    DEF("def", "normal rectangle"),;

    private String code;

    private String message;

    TagEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }


    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "TagEnum{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}

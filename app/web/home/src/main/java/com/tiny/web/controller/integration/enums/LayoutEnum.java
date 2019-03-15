package com.tiny.web.controller.integration.enums;

import org.apache.commons.lang.StringUtils;

public enum LayoutEnum {

    GRID("grid-ly", "gird layout"),

    TABLE("table-ly", "table layout"),;

    private String code;

    private String message;

    LayoutEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }


    /**
     * @param code
     * @return
     */
    public static LayoutEnum codeOf(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (LayoutEnum temp : values()) {
            if (StringUtils.equalsIgnoreCase(temp.code, code)) {
                return temp;
            }
        }
        return null;
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

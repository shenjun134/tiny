package com.tiny.web.controller.integration.factory;

import org.apache.commons.lang.StringUtils;

public abstract class BaseFactory {
    enum Mode {
        REMOTE("remote", 1),

        MOCK("mock", 2),

        DEF("def", 0),;
        private String code;

        private int val;

        Mode(String code, int val) {
            this.code = code;
            this.val = val;
        }

        public static Mode codeOf(String code) {
            if (StringUtils.isBlank(code)) {
                return null;
            }
            for (Mode temp : values()) {
                if (StringUtils.equalsIgnoreCase(code, temp.code)) {
                    return temp;
                }
            }
            return null;
        }
    }

}

package com.tiny.web.controller.integration.factory;

import com.tiny.common.enums.SystemPropertyEnum;
import com.tiny.common.util.SystemUtils;
import com.tiny.web.controller.integration.service.SignatureService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SignatureFactory {

    @Autowired
    private SignatureService remoteService;

    @Autowired
    private SignatureService mockService;

    public SignatureService getService() {
        String modeV = SystemUtils.getSystemProperty(SystemPropertyEnum.SIGNATURE_MODE);
        Mode mode = Mode.codeOf(modeV);
        mode = mode == null ? Mode.DEF : mode;
        switch (mode) {
            case DEF:
                return mockService;
            case MOCK:
                return mockService;
            case REMOTE:
                return remoteService;
        }
        throw new RuntimeException("No service available!");

    }


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

    public void setRemoteService(SignatureService remoteService) {
        this.remoteService = remoteService;
    }

    public void setMockService(SignatureService mockService) {
        this.mockService = mockService;
    }
}

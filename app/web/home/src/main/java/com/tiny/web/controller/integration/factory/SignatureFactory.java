package com.tiny.web.controller.integration.factory;

import com.tiny.common.enums.SystemPropertyEnum;
import com.tiny.common.util.SystemUtils;
import com.tiny.web.controller.integration.service.SignatureService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SignatureFactory extends BaseFactory {

    @Autowired
    private SignatureService remoteSignatureService;

    @Autowired
    private SignatureService mockSignatureService;

    public SignatureService getService() {
        String modeV = SystemUtils.getSystemProperty(SystemPropertyEnum.SIGNATURE_MODE);
        Mode mode = Mode.codeOf(modeV);
        mode = mode == null ? Mode.DEF : mode;
        switch (mode) {
            case DEF:
                return mockSignatureService;
            case MOCK:
                return mockSignatureService;
            case REMOTE:
                return remoteSignatureService;
        }
        throw new RuntimeException("No service available!");

    }

    public void setRemoteSignatureService(SignatureService remoteSignatureService) {
        this.remoteSignatureService = remoteSignatureService;
    }

    public void setMockSignatureService(SignatureService mockSignatureService) {
        this.mockSignatureService = mockSignatureService;
    }
}

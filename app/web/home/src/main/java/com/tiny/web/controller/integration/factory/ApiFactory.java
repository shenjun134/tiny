package com.tiny.web.controller.integration.factory;

import com.tiny.common.enums.SystemPropertyEnum;
import com.tiny.common.util.SystemUtils;
import com.tiny.web.controller.integration.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApiFactory extends BaseFactory {

    @Autowired
    private ApiService remoteApiService;

    @Autowired
    private ApiService mockApiService;

    public ApiService getService() {
        String modeV = SystemUtils.getSystemProperty(SystemPropertyEnum.SIGNATURE_MODE);
        Mode mode = Mode.codeOf(modeV);
        mode = mode == null ? Mode.DEF : mode;
        switch (mode) {
            case DEF:
                return mockApiService;
            case MOCK:
                return mockApiService;
            case REMOTE:
                return remoteApiService;
        }
        throw new RuntimeException("No service available!");
    }

    public void setRemoteApiService(ApiService remoteApiService) {
        this.remoteApiService = remoteApiService;
    }

    public void setMockApiService(ApiService mockApiService) {
        this.mockApiService = mockApiService;
    }
}

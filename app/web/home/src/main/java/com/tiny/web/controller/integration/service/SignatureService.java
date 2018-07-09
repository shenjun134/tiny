package com.tiny.web.controller.integration.service;

import com.tiny.web.controller.BaseJsonResult;

public interface SignatureService {

    /**
     * only support jpg
     *
     * @param imagePath
     * @param imageName
     * @return
     */
    BaseJsonResult scan(String imagePath, String imageName);
}

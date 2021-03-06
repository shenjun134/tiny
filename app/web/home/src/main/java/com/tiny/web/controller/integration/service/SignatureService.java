package com.tiny.web.controller.integration.service;

import com.tiny.web.controller.BaseJsonResult;
import com.tiny.web.controller.integration.entity.FixedFax;

public interface SignatureService {

    /**
     * only support jpg
     *
     * @param imagePath
     * @param imageName
     * @return
     */
    BaseJsonResult scan(String imagePath, String imageName);

    /**
     * only support jpg
     *
     * @param imagePath
     * @param imageName
     * @return
     */
    BaseJsonResult scan2(String imagePath, String imageName);


    /**
     * @param fixedFax
     * @return
     */
    BaseJsonResult fix(FixedFax fixedFax);

    /**
     * @param fixedFax
     * @return
     */
    BaseJsonResult fix2(FixedFax fixedFax);
}

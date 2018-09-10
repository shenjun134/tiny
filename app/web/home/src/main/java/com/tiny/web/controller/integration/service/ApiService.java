package com.tiny.web.controller.integration.service;

import com.tiny.web.controller.http.request.RecognitionReq;
import com.tiny.web.controller.integration.entity.ContentResult;
import com.tiny.web.controller.integration.entity.LayoutResult;

import java.util.List;

public interface ApiService {

    /**
     * @param request
     * @return
     */
    List<LayoutResult> recognitionLayout(RecognitionReq request);

    /**
     * @param request
     * @return
     */
    ContentResult recognitionContent(RecognitionReq request);
}

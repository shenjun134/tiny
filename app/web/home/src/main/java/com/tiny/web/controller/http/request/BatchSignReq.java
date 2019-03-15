package com.tiny.web.controller.http.request;

import com.tiny.common.base.ToString;

import java.util.ArrayList;
import java.util.List;

public class BatchSignReq extends ToString {
    private List<SignatureReq> batchReq = new ArrayList<>();


    public List<SignatureReq> getBatchReq() {
        return batchReq;
    }

    public void setBatchReq(List<SignatureReq> batchReq) {
        this.batchReq = batchReq;
    }
}

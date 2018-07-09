package com.tiny.web.controller.json;

import com.tiny.common.base.ToString;

public class OcrCover extends ToString {
    private static final long serialVersionUID = 1411608607720070825L;

    private String receivedFaxLineName;

    private String receivedDateTime;

    private String totalPage;

    private String faxSeqNumber;

    private String senderInformation;

    private String recipientUser;

    private String faxStatus;

    public String getReceivedFaxLineName() {
        return receivedFaxLineName;
    }

    public void setReceivedFaxLineName(String receivedFaxLineName) {
        this.receivedFaxLineName = receivedFaxLineName;
    }

    public String getReceivedDateTime() {
        return receivedDateTime;
    }

    public void setReceivedDateTime(String receivedDateTime) {
        this.receivedDateTime = receivedDateTime;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getFaxSeqNumber() {
        return faxSeqNumber;
    }

    public void setFaxSeqNumber(String faxSeqNumber) {
        this.faxSeqNumber = faxSeqNumber;
    }

    public String getSenderInformation() {
        return senderInformation;
    }

    public void setSenderInformation(String senderInformation) {
        this.senderInformation = senderInformation;
    }

    public String getRecipientUser() {
        return recipientUser;
    }

    public void setRecipientUser(String recipientUser) {
        this.recipientUser = recipientUser;
    }

    public String getFaxStatus() {
        return faxStatus;
    }

    public void setFaxStatus(String faxStatus) {
        this.faxStatus = faxStatus;
    }
}

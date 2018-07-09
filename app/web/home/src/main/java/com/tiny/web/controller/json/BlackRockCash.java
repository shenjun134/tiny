package com.tiny.web.controller.json;

public class BlackRockCash extends OcrTemp {
    private static final long serialVersionUID = -6689655697696492400L;

    private String transactionType;
    private String messageFunction;
    private String transactionReference;
    private String fundId;
    private String tradeDate;
    private String settlementDate;
    private String securityId;
    private String currency;
    private String quantity;
    private String price;
    private String interest;
    private String commission;
    private String netAmount;
    private String execBroBIC;
    private String execBroName;
    private String execBroAcct;
    private String clearBroBIC;
    private String clearBroName;
    private String clearBroAcct;
    private String beneficiaryBIC;
    private String beneficiaryAcct;
    private String clientName;
    private String tradeType;
    private String etdSender;
    private String senderBIC;
    private String receiverBIC;

    public String getFundId() {
        return fundId;
    }

    public void setFundId(String fundId) {
        int spaceIndex = fundId.lastIndexOf('#');
        if (spaceIndex > -1)
            fundId = fundId.substring(spaceIndex + 1, fundId.length()).trim();
        this.fundId = fundId;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(String netAmount) {
        this.netAmount = netAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getMessageFunction() {
        return messageFunction;
    }

    public void setMessageFunction(String messageFunction) {
        this.messageFunction = messageFunction;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getExecBroBIC() {
        return execBroBIC;
    }

    public void setExecBroBIC(String execBroBIC) {
        this.execBroBIC = execBroBIC;
    }

    public String getExecBroName() {
        return execBroName;
    }

    public void setExecBroName(String execBroName) {
        this.execBroName = execBroName;
    }

    public String getExecBroAcct() {
        return execBroAcct;
    }

    public void setExecBroAcct(String execBroAcct) {
        this.execBroAcct = execBroAcct;
    }

    public String getClearBroBIC() {
        return clearBroBIC;
    }

    public void setClearBroBIC(String clearBroBIC) {
        this.clearBroBIC = clearBroBIC;
    }

    public String getClearBroName() {
        return clearBroName;
    }

    public void setClearBroName(String clearBroName) {
        this.clearBroName = clearBroName;
    }

    public String getClearBroAcct() {
        return clearBroAcct;
    }

    public void setClearBroAcct(String clearBroAcct) {
        this.clearBroAcct = clearBroAcct;
    }

    public String getBeneficiaryBIC() {
        return beneficiaryBIC;
    }

    public void setBeneficiaryBIC(String beneficiaryBIC) {
        this.beneficiaryBIC = beneficiaryBIC;
    }

    public String getBeneficiaryAcct() {
        return beneficiaryAcct;
    }

    public void setBeneficiaryAcct(String beneficiaryAcct) {
        this.beneficiaryAcct = beneficiaryAcct;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getEtdSender() {
        return etdSender;
    }

    public void setEtdSender(String etdSender) {
        this.etdSender = etdSender;
    }

    public String getSenderBIC() {
        return senderBIC;
    }

    public void setSenderBIC(String senderBIC) {
        this.senderBIC = senderBIC;
    }

    public String getReceiverBIC() {
        return receiverBIC;
    }

    public void setReceiverBIC(String receiverBIC) {
        this.receiverBIC = receiverBIC;
    }


}

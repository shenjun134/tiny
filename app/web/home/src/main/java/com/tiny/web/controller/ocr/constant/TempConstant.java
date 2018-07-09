package com.tiny.web.controller.ocr.constant;

import com.tiny.web.controller.ocr.model.Field;
import com.tiny.web.controller.ocr.model.ScanTemp;

import java.util.ArrayList;
import java.util.List;

public class TempConstant {


    private interface Type {
        String date = "date";
        String number = "number";
        String string = "string";
        String phone = "phone";
        String email = "email";
        String postCode = "postCode";
        String currency = "currency";
    }

    private interface Rule {
        String no_spance = "/^[^\\s]*$/";
        String with_decimal = "/^\\d+\\.\\d+$/";
    }

    private interface Constant {
        String required = "true";
        String not_required = "false";
    }

    public static final ScanTemp cover = new ScanTemp();
    public static final ScanTemp blackRockCash = new ScanTemp();
    public static final ScanTemp blackRockSecurity = new ScanTemp();
    public static final ScanTemp pimco = new ScanTemp();

    public static final List<ScanTemp> TEMP_LIST = new ArrayList<ScanTemp>();

    static {
        initCover();
        initCash();
        //initSecurity();
        initPimco();

        TEMP_LIST.add(blackRockCash);
        //TEMP_LIST.add(blackRockSecurity);
        TEMP_LIST.add(pimco);
    }


    private static void initCover() {
        cover.setName("Cover");
        cover.setKey("cover");

        cover.addField(new Field("Received Date Time", "date", "date").setType(Type.date).setRequired(Constant.required));
        cover.addField(new Field("Total Page Count", "totalPage", "totalPage").setType(Type.number).setRequired(Constant.required));
        cover.addField(new Field("Fax Seq Number", "seqNumber", "seqNumber").setType(Type.number).setRequired(Constant.required));
        cover.addField(new Field("Sender Information", "senderInfo", "senderInfo").setRequired(Constant.required));
        cover.addField(new Field("Recipient Phone Number", "lineName", "lineName").setType(Type.phone).setRequired(Constant.required));
        cover.addField(new Field("Recipient User", "recipientUser", "recipientUser").setRequired(Constant.required));
        cover.addField(new Field("Fax Status", "faxStatus", "faxStatus").setRequired(Constant.required));
    }

    private static void initCash() {
        blackRockCash.setName("Black-Rock-Cash");
        blackRockCash.setKey("black-rock-cash");

        blackRockCash.addField(new Field("Transaction Type", "transactionType", "transactionType").setRequired(Constant.required));
        blackRockCash.addField(new Field("Message Function", "messageFunction", "messageFunction").setRequired(Constant.required));
        blackRockCash.addField(new Field("Transaction Reference", "transactionReference", "transactionReference").setRequired(Constant.required));
        blackRockCash.addField(new Field("Fund ID", "fundId", "fundId").setMaxLength("4").setMinLength("4").setRequired(Constant.required));
        blackRockCash.addField(new Field("Trade Date", "tradeDate", "tradeDate").setType(Type.date).setRequired(Constant.required));
        blackRockCash.addField(new Field("Settlement Date", "settlementDate", "settlementDate").setType(Type.date).setRequired(Constant.required));
        blackRockCash.addField(new Field("Security ID", "securityId", "securityId").setRequired(Constant.required));
        blackRockCash.addField(new Field("Currency", "currency", "currency").setType(Type.currency).setRequired(Constant.required));
        blackRockCash.addField(new Field("Quantity", "quantity", "quantity").setType(Type.number).setRequired(Constant.required));
        blackRockCash.addField(new Field("Price", "price", "price").setType(Type.number).setRequired(Constant.required));
        blackRockCash.addField(new Field("Interest", "interest", "interest").setType(Type.number).setRequired(Constant.required));
        blackRockCash.addField(new Field("Commission", "commission", "commission").setType(Type.number).setRequired(Constant.required));
        blackRockCash.addField(new Field("Net Amount", "netAmount", "netAmount").setType(Type.number).setRequired(Constant.required));
        blackRockCash.addField(new Field("Executing Broker BIC", "execBroBIC", "execBroBIC").setRequired(Constant.required));
        blackRockCash.addField(new Field("Executing Broker Name", "execBroName", "execBroName").setRequired(Constant.required));
        blackRockCash.addField(new Field("Executing Broker Acct", "execBroAcct", "execBroAcct").setRequired(Constant.required));
        blackRockCash.addField(new Field("Clearing Broker BIC", "clearBroBIC", "clearBroBIC").setRequired(Constant.required));
        blackRockCash.addField(new Field("Clearing Broker Name", "clearBroName", "clearBroName").setRequired(Constant.required));
        blackRockCash.addField(new Field("Clearing Broker Acct", "clearBroAcct", "clearBroAcct").setRequired(Constant.required));
        blackRockCash.addField(new Field("Beneficiary BIC", "beneficiaryBIC", "beneficiaryBIC"));
        blackRockCash.addField(new Field("Beneficiary Acct", "beneficiaryAcct", "beneficiaryAcct"));
        blackRockCash.addField(new Field("Client Name", "clientName", "clientName"));
        blackRockCash.addField(new Field("Trade Type", "tradeType", "tradeType"));
        blackRockCash.addField(new Field("ETD Sender", "etdSender", "etdSender"));
        blackRockCash.addField(new Field("Sender BIC", "senderBIC", "senderBIC"));
        blackRockCash.addField(new Field("Receiver BIC", "receiverBIC", "receiverBIC"));
    }

    private static void initSecurity() {
        blackRockSecurity.setName("Black-Rock-Security");
        blackRockSecurity.setKey("black-rock-security");

        blackRockSecurity.addField(new Field("Transaction Type", "transactionType", "transactionType").setRequired(Constant.required));
        blackRockSecurity.addField(new Field("Message Function", "messageFunction", "messageFunction").setRequired(Constant.required));
        blackRockSecurity.addField(new Field("Transaction Reference", "transactionReference", "transactionReference").setRequired(Constant.required));
        blackRockSecurity.addField(new Field("Fund ID", "fundId", "fundId").setMaxLength("4").setMinLength("4").setRequired(Constant.required));
        blackRockSecurity.addField(new Field("Trade Date", "tradeDate", "tradeDate").setType(Type.date).setRequired(Constant.required));
        blackRockSecurity.addField(new Field("Settlement Date", "settlementDate", "settlementDate").setType(Type.date).setRequired(Constant.required));
        blackRockSecurity.addField(new Field("Security ID", "securityId", "securityId").setRequired(Constant.required));
        blackRockSecurity.addField(new Field("Currency", "currency", "currency").setType(Type.currency).setRequired(Constant.required));
        blackRockSecurity.addField(new Field("Quantity", "quantity", "quantity").setType(Type.number).setRequired(Constant.required));
        blackRockSecurity.addField(new Field("Price", "price", "price").setType(Type.number).setRequired(Constant.required));
        blackRockSecurity.addField(new Field("Interest", "interest", "interest").setType(Type.number).setRequired(Constant.required));
        blackRockSecurity.addField(new Field("Commission", "commission", "commission").setType(Type.number).setRequired(Constant.required));
        blackRockSecurity.addField(new Field("Net Amount", "netAmount", "netAmount").setType(Type.number).setRequired(Constant.required));
        blackRockSecurity.addField(new Field("Beneficiary BIC", "beneficiaryBIC", "beneficiaryBIC").setRequired(Constant.required));
        blackRockSecurity.addField(new Field("Security Beneficiary Acct", "beneficiaryAcct", "beneficiaryAcct").setRequired(Constant.required));
        blackRockSecurity.addField(new Field("Security Client Name", "clientName", "clientName").setRequired(Constant.required));
        blackRockSecurity.addField(new Field("Security Trade Type", "tradeType", "tradeType"));
        blackRockSecurity.addField(new Field("Security ETD Sender", "etdSender", "etdSender"));
        blackRockSecurity.addField(new Field("Security Sender BIC", "senderBIC", "senderBIC"));
        blackRockSecurity.addField(new Field("Security Receiver BIC", "receiverBIC", "receiverBIC"));
    }

    private static void initPimco() {
        pimco.setName("PIMCO-Derivative");
        pimco.setKey("pimco-derivative");

        pimco.addField(new Field("Transaction Type", "transactionType", "transactionType").setRequired(Constant.required));
        pimco.addField(new Field("Message Function", "messageFunction", "messageFunction").setRequired(Constant.required));
        pimco.addField(new Field("Transaction Reference", "transactionReference", "transactionReference").setRequired(Constant.required));
        pimco.addField(new Field("Custodian Account", "custodianAccount", "custodianAccount").setRequired(Constant.required));
        pimco.addField(new Field("Account Number", "accountNumber", "accountNumber").setRequired(Constant.required));
        pimco.addField(new Field("Account Name", "accountName", "accountName").setRequired(Constant.required));
        pimco.addField(new Field("Trade Date", "tradeDate", "tradeDate").setType(Type.date).setRequired(Constant.required));
        pimco.addField(new Field("Settlement Date", "settlementDate", "settlementDate").setType(Type.date).setRequired(Constant.required));
        pimco.addField(new Field("Deal ID", "dealID", "dealID").setType(Type.string).setRequired(Constant.required));
        pimco.addField(new Field("Notional Amount", "notionalAmount", "notionalAmount").setType(Type.number).setRequired(Constant.required));
        pimco.addField(new Field("Price", "price", "price").setType(Type.number).setRequired(Constant.required));
        pimco.addField(new Field("Principal Currency", "principalCurrency", "principalCurrency").setType(Type.currency).setRequired(Constant.required));
        pimco.addField(new Field("Principal Amount", "principalAmount", "principalAmount").setType(Type.number).setRequired(Constant.required));
        pimco.addField(new Field("Accrued Interest", "accruedInterest", "accruedInterest").setType(Type.number).setRequired(Constant.required));
        pimco.addField(new Field("Settlement Currency", "settlementCurrency", "settlementCurrency").setType(Type.currency).setRequired(Constant.required));
        pimco.addField(new Field("Settlement Amount", "settlementAmount", "settlementAmount").setType(Type.number).setRequired(Constant.required));
        pimco.addField(new Field("Executing Broker BIC", "execBroBIC", "execBroBIC").setRequired(Constant.required));
        pimco.addField(new Field("Clearing Broker BIC", "clearBroBIC", "clearBroBIC").setRequired(Constant.required));
    }

}

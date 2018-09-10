package com.tiny.common.util;

import com.tiny.common.entity.CellVO;
import com.tiny.common.entity.Element;
import com.tiny.common.entity.RowVO;
import com.tiny.common.entity.TableVO;
import com.tiny.common.enums.FieldType;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TableUtil {

    interface Const {
        int seed = 50;
    }


    public static TableVO random() {
        double rd = Math.random() * 100;
        return rd > Const.seed ? randomTV1() : randomTV2();
    }


    private static TableVO randomTV2() {
        RowVO header = new RowVO();

        header.add(new CellVO("Block ID").setValueX(Element.ALIGN_LEFT).setValueType(FieldType.BLOCK_ID).setWidth(7));
        header.add(new CellVO("Account").setValueX(Element.ALIGN_LEFT).setValueType(FieldType.FUND).setWidth(4));
        header.add(new CellVO("Transaction Type").setValueX(Element.ALIGN_CENTER).setValueDef("SELL").setWidth(6));
        header.add(new CellVO("ASSET ID").setValueX(Element.ALIGN_LEFT).setValueType(FieldType.ASSET_ID).setWidth(6));
        header.add(new CellVO("Security Description").setValueX(Element.ALIGN_LEFT).setValueType(FieldType.SECURITY_DESC).setWidth(16));
        header.add(new CellVO("Settlement Location").setValueX(Element.ALIGN_CENTER).setValueDef("GBV").setWidth(4));
        header.add(new CellVO("Trading & Clearing Broker").setValueX(Element.ALIGN_CENTER).setValueType(FieldType.BROKER).setWidth(3));
        header.add(new CellVO("Trade Currency").setValueX(Element.ALIGN_CENTER).setValueType(FieldType.CURRENCY).setWidth(4));
        header.add(new CellVO("Trade Date (dd/mm/yyyy)").setValueX(Element.ALIGN_CENTER).setValueType(FieldType.DATE).setWidth(7));
        header.add(new CellVO("Settlement Date (dd/mm/yyyy)").setValueX(Element.ALIGN_CENTER).setValueType(FieldType.DATE).setWidth(7));
        header.add(new CellVO("Share Amount").setValueX(Element.ALIGN_CENTER).setValueType(FieldType.NUMBER).setInt(6, 3).setWidth(5));
        header.add(new CellVO("Price").setValueX(Element.ALIGN_CENTER).setValueType(FieldType.NUMBER).setInt(5, 3).setDecimal(2, 4).setWidth(5));
        header.add(new CellVO("Net Amount").setValueX(Element.ALIGN_CENTER).setValueType(FieldType.NUMBER).setInt(6, 5).setDecimal(2, 2).setWidth(6));

        List<RowVO> bodyList = new ArrayList<>();
        int maxRow = 15;
        int minRow = 12;
        int length = (int) (Math.random() * 100d % maxRow);
        if (length < minRow && length > 0) {
            length = minRow;
        } else if (length == 0) {
            length = maxRow;
        }
        for (int i = 0; i < length; i++) {
            RowVO body = new RowVO();
            for (int j = 0; j < header.getList().size(); j++) {
                CellVO headVO = header.getList().get(j);
                String value = headVO.getValueDef();
                if (StringUtils.isBlank(value)) {
                    value = randomStr(headVO.getValueType(), headVO);
                }
                CellVO colVo = new CellVO(value).setxAlign(headVO.getValueX()).setyAlign(headVO.getValueY());
                body.add(colVo);
            }
            bodyList.add(body);
        }
        return new TableVO(header, bodyList);
    }


    private static TableVO randomTV1() {
        RowVO header = new RowVO();

        header.add(new CellVO("Agreement ID").setValueX(Element.ALIGN_RIGHT).setValueType(FieldType.AGREEMENT).setWidth(4));
        header.add(new CellVO("CP").setValueX(Element.ALIGN_LEFT).setValueDef("ICI").setWidth(2));
        header.add(new CellVO("Fund Account").setValueX(Element.ALIGN_LEFT).setValueType(FieldType.FUND).setWidth(3));
        header.add(new CellVO("Transaction Type").setValueX(Element.ALIGN_LEFT).setValueType(FieldType.TXN_TYPE).setWidth(10));
        header.add(new CellVO("Transfer Out Fund").setValueX(Element.ALIGN_LEFT).setValueType(FieldType.W_FUND).setWidth(6));
        header.add(new CellVO("Transfer In Fund").setValueX(Element.ALIGN_LEFT).setValueType(FieldType.W_FUND).setWidth(6));
        header.add(new CellVO("Trade Date").setValueX(Element.ALIGN_RIGHT).setValueType(FieldType.DATE).setWidth(5));
        header.add(new CellVO("Value Date").setValueX(Element.ALIGN_RIGHT).setValueType(FieldType.DATE).setWidth(5));
        header.add(new CellVO("NOMINAL").setValueX(Element.ALIGN_RIGHT).setValueType(FieldType.NUMBER).setWidth(7));
        header.add(new CellVO("ISIN").setValueX(Element.ALIGN_LEFT).setValueType(FieldType.ISIN).setWidth(8));
        header.add(new CellVO("Settl Location").setValueX(Element.ALIGN_LEFT).setValueType(FieldType.LOCATION).setWidth(4));
        header.add(new CellVO("Reference").setValueX(Element.ALIGN_LEFT).setValueType(FieldType.REFERENCE).setWidth(4));
        header.add(new CellVO("Type").setValueX(Element.ALIGN_LEFT).setValueType(FieldType.TYPE).setWidth(4));
        header.add(new CellVO("Additional Info:").setValueX(Element.ALIGN_LEFT).setValueDef("FOP-Collateral movement").setWidth(6));
        header.add(new CellVO("Broker Code").setValueX(Element.ALIGN_MIDDLE).setValueType(FieldType.BROKER).setWidth(3));
        List<RowVO> bodyList = new ArrayList<>();
        int maxRow = 9;
        int minRow = 6;
        int length = (int) (Math.random() * 100d % maxRow);
        if (length < minRow && length > 0) {
            length = minRow;
        } else if (length == 0) {
            length = maxRow;
        }
        for (int i = 0; i < length; i++) {
            RowVO body = new RowVO();
            for (int j = 0; j < header.getList().size(); j++) {
                CellVO headVO = header.getList().get(j);
                String value = headVO.getValueDef();
                if (StringUtils.isBlank(value)) {
                    value = randomStr(headVO.getValueType());
                }
                CellVO colVo = new CellVO(value).setxAlign(headVO.getValueX()).setyAlign(headVO.getValueY());
                body.add(colVo);
            }
            bodyList.add(body);
        }
        return new TableVO(header, bodyList);
    }

    public static String defRandom() {
        return randomStr(8, 4);
    }

    public static String randomStr(int max, int min) {
        double random = Math.random() * 100d;
        int length = (int) (Math.random() * 100d % max);
        if (length < min && length > 0) {
            length = min;
        } else if (length == 0) {
            length = max;
        }

        if (random <= Constant.numberOnly) {
            return RandomStringUtils.random(length, false, true);
        } else if (random <= Constant.stringOnly) {
            return RandomStringUtils.random(length, true, false);
        } else if (random <= Constant.mix) {
            return RandomStringUtils.random(length, true, true);
        }
        return RandomStringUtils.random(length, true, true);
    }


    public static String randomStr(FieldType fieldType, Object... params) {
        switch (fieldType) {
            case DATE:
                return randomDate();
            case FUND:
                return RandomStringUtils.random(4, true, true).toUpperCase();
            case NUMBER: {
                if (params == null || params.length == 0) {

                    return randomNum();
                }
                if (params[0] instanceof CellVO) {
                    CellVO cellVO = (CellVO) params[0];
                    String intPart = "";
                    String decimalPart = "";

                    if (cellVO.isIntEnable() && cellVO.getIntMinCount() >= 0) {
                        int rd = randomInt(cellVO.getIntMaxCount(), cellVO.getIntMinCount());
                        intPart = RandomStringUtils.random(rd, false, true);
                    }
                    if (cellVO.isDecimalEnable() && cellVO.getDecimalMinCount() > 0) {
                        int rd = randomInt(cellVO.getDecimalMaxCount(), cellVO.getDecimalMinCount());
                        decimalPart = "." + RandomStringUtils.random(rd, false, true);
                    }
                    if (intPart.length() != 0 || decimalPart.length() != 0) {
                        intPart = intPart.length() == 0 ? "0" : intPart;
                        return intPart + decimalPart;
                    }
                }
                return randomNum();
            }
            case STRING:
                return defRandom();
            /**
             * 6 digital
             */
            case AGREEMENT:
                return RandomStringUtils.random(6, false, true);

            /**
             * CP3A-OW02
             */
            case W_FUND:
                return RandomStringUtils.random(4, true, true).toUpperCase() + "-" + RandomStringUtils.random(4, true, true).toUpperCase();
            /**
             * Sell-Payment, Securities Internal Transfer,
             */
            case TXN_TYPE:
                return randomBool() ? "Sell-Payment" : "Securities Internal Transfer";

            /**
             * 12 string begin with XS0 or GB0
             */
            case ISIN: {
                String begin = randomBool() ? "XS0" : "GB0";
                return begin + RandomStringUtils.random(9, true, true).toUpperCase();
            }

            /**
             * Euroclear, CREST,
             */
            case LOCATION:
                return randomBool() ? "Euroclear" : "CREST";

            /**
             * SW-SEC-1
             */
            case REFERENCE:
                return "SW-SEC-" + RandomStringUtils.random(2, false, true);

            /**
             * ACCOUNTING ONLY/CUSTODY ONLY
             */
            case TYPE:
                return randomBool() ? "ACCOUNTING ONLY" : "CUSTODY ONLY";

            /**
             * 5 digital
             */
            case BROKER:
                return RandomStringUtils.random(5, false, true);

            case BLOCK_ID:
                return RandomStringUtils.random(9, false, true) + "_F1";
            case ASSET_ID:
                return RandomStringUtils.random(9, true, true).toUpperCase();
            case SECURITY_DESC: {
                int rd = randomInt(6, 1);
                if (rd == 1) {
                    return "VANGUARD-JAPAN STK IND-GBP ACC";
                }
                if (rd == 2) {
                    return "VANGUARD DEV WORLD XUK EI-A";
                }
                if (rd == 3) {
                    return "VANGUARD-JAPAN STK INDX-GBPA";
                }
                if (rd == 4) {
                    return "VANGUARD-US EQUITY INDEX-A";
                }
                if (rd == 5) {
                    return " BLOCKROCK CONF EUR INC-A ACC";
                }
                if (rd == 6) {
                    return "BLOCKROCK CIF-EM NKT EQ-D";
                }
                return "VANGUARD-JAPAN STK IND-GBP ACC";
            }

            case CURRENCY: {
//                GBP, USD, RMB, EUR
                int rd = randomInt(4, 1);
                if (rd == 1) {
                    return "GBP";
                }
                if (rd == 2) {
                    return "USD";
                }
                if (rd == 3) {
                    return "RMB";
                }
                if (rd == 4) {
                    return "EUR";
                }
                return "GBP";
            }

        }
        return defRandom();
    }


    /**
     * @return
     */
    public static String randomDate() {
        String day = randomDay();
        String month = randomMonth();
        String year = randomYear();
        return new StringBuilder().append(day).append("/").append(month).append("/").append(year).toString();
    }

    /**
     * 01 ~ 28
     *
     * @return
     */
    public static String randomDay() {
        return StringUtils.leftPad("" + randomInt(28, 1), 2, "0");
    }

    /**
     * 01 ~ 12
     *
     * @return
     */
    public static String randomMonth() {
        return StringUtils.leftPad("" + randomInt(12, 1), 2, "0");
    }

    /**
     * 2017 ~ 2018
     *
     * @return
     */
    public static String randomYear() {
        return StringUtils.leftPad("20" + randomInt(18, 17), 2, "0");
    }


    public static boolean randomBool() {
        return Math.random() > 0.5;
    }

    /**
     * 100,000.00 ~ 9,999,999,999.00
     *
     * @return
     */
    public static String randomNum() {
        DecimalFormat decimalFormat = new DecimalFormat("##,###.00");
        int count = randomInt(10, 6);
        String strNum = RandomStringUtils.random(count, false, true);
        try {
            int num = Integer.valueOf(strNum);
            return decimalFormat.format(num);
        } catch (NumberFormatException e) {
            return "100,000.00";
        }
    }


    public static int randomInt(int maxRow, int minRow) {
        int length = (int) (Math.random() * 1000000d % maxRow);
        if (length < minRow && length > 0) {
            length = minRow;
        } else if (length == 0) {
            length = maxRow;
        }
        return length;
    }

    interface Constant {

        int numberOnly = 50;
        int stringOnly = 80;
        int mix = 100;
    }


}

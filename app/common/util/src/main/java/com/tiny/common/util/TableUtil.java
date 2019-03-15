package com.tiny.common.util;

import com.tiny.common.entity.*;
import com.tiny.common.enums.FieldType;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.*;

public class TableUtil {

    private static final Logger logger = LoggerFactory.getLogger(TableUtil.class);

    class FontConstants {
        public static final String COURIER = "Courier";
        public static final String COURIER_BOLD = "Courier-Bold";
        public static final String COURIER_OBLIQUE = "Courier-Oblique";
        public static final String COURIER_BOLDOBLIQUE = "Courier-BoldOblique";
        public static final String HELVETICA = "Helvetica";
        public static final String HELVETICA_BOLD = "Helvetica-Bold";
        public static final String HELVETICA_OBLIQUE = "Helvetica-Oblique";
        public static final String HELVETICA_BOLDOBLIQUE = "Helvetica-BoldOblique";
        public static final String SYMBOL = "Symbol";
        public static final String TIMES_ROMAN = "Times-Roman";
        public static final String TIMES_BOLD = "Times-Bold";
        public static final String TIMES_ITALIC = "Times-Italic";
        public static final String TIMES_BOLDITALIC = "Times-BoldItalic";
        public static final String ZAPFDINGBATS = "ZapfDingbats";
    }

    public static List<String> defFontStrList;

    static {
        String[] fontArr = new String[]{
                FontConstants.COURIER,
                FontConstants.COURIER_BOLD,
                FontConstants.COURIER_OBLIQUE,
                FontConstants.COURIER_BOLDOBLIQUE,
                FontConstants.HELVETICA,
                FontConstants.HELVETICA_BOLD,
                FontConstants.HELVETICA_OBLIQUE,
                FontConstants.HELVETICA_BOLDOBLIQUE,
                FontConstants.SYMBOL,
                FontConstants.TIMES_ROMAN,
                FontConstants.TIMES_BOLD,
                FontConstants.TIMES_ITALIC,
                FontConstants.TIMES_BOLDITALIC,
                FontConstants.ZAPFDINGBATS,
        };
        defFontStrList = Arrays.asList(fontArr);
    }

    interface Const {
        int seed = 50;
        int baseFont = 16;
        int unitCharWidth = 10;
        int minCol = 6;
        float lineHeightPer = 1.2f;
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

    /******************************random normal table begin***********************/
    /**
     * @param config
     * @param mockData
     * @return
     */
    public static TableStructure getNormalStructure(TableLayoutConfig config, MockData mockData) {
        TableStructure structure = randomNormalStructure(config, mockData);
        int colSize = structure.getCellWidthList().size();
        int rowSize = structure.getCellHeightList().size();
        if (colSize >= config.getCellColumnLimit() && rowSize >= config.getCellRowLimit()) {
            return structure;
        }
        config.retryCountCut();
        if (config.getRetryCount() <= 0) {
            return null;
        }
        return getNormalStructure(config, mockData);
    }

    public static TableStructure randomNormalStructure(TableLayoutConfig config, MockData mockData) {
        TableStructure structure = new TableStructure();
        boolean noiseTop = randomTF(config.getNoiseTopProbability());
        boolean noiseBottom = randomTF(config.getNoiseBottomProbability());
        boolean noiseLeft = randomTF(config.getNoiseLeftProbability());
        boolean noiseRight = randomTF(config.getNoiseRightProbability());

        int borderWidth = randomRange(config.getBorderMaxWidth(), config.getBorderMinWidth());
        int innerBorderWidth = randomRange(config.getBorderMaxWidth(), config.getBorderMinWidth());
        if (borderWidth - innerBorderWidth != 0) {
            logger.warn("border diff - borderWidth:" + borderWidth + ", innerBorderWidth:" + innerBorderWidth);
        }
        structure.setInnerBorderWidth(innerBorderWidth);

//        boolean isMerge = randomTF(config.getMergeProbability());
        boolean isMerge = false;
        int fontSize = fontSize();
        float lintHeight = fontSize * Const.lineHeightPer;
        String fontFamily = fontProgram();

        Point startPoint = randomPoint(config);

        int cellMinWidth = config.getCellMinWidth() + config.getCellPaddingLeft() + config.getCellPaddingRight();
        int cellMinHeight = config.getCellMinHeight() + config.getCellPaddingBottom() + config.getCellPaddingTop();

//        List<Integer> widthList = randomInt(widthRange, config.getCellPaddingLeft(), config.getCellPaddingRight(), widthBase, (int) (fontMutl * cellMinWidth));
//        List<Integer> heightList = randomInt(heightRange, config.getCellPaddingTop(), config.getCellPaddingBottom(), heightBase, (int) (fontMutl * cellMinHeight));

//        int tableWidth = sum(widthList);
//        int tableHeight = sum(heightList);
        /*******************************setter*********************************/
        structure.setStartPoint(startPoint);

        structure.setBorderWidth(borderWidth);

        structure.setCellMinWidth(cellMinWidth);
        structure.setCellMinHeight(cellMinHeight);

        structure.setFontSize(fontSize);
        structure.setLineHeight(lintHeight);
        structure.setFontFamily(fontFamily);

//        structure.setTableWidth(tableWidth);
//        structure.setTableHeight(tableHeight);

        structure.setCellLeftPadding(config.getCellPaddingLeft());
        structure.setCellRightPadding(config.getCellPaddingRight());
        structure.setCellTopPadding(config.getCellPaddingTop());
        structure.setCellBottomPadding(config.getCellPaddingBottom());

        structure.setMergeCell(isMerge);

//        structure.setCellWidthList(widthList);
//        structure.setCellHeightList(heightList);

        structure.setNoiseTop(noiseTop);
        structure.setNoiseBottom(noiseBottom);
        structure.setNoiseLeft(noiseLeft);
        structure.setNoiseRight(noiseRight);

        /**
         * random cell array and line
         */
        randomCellArray(config, structure, mockData);

        /**
         * generate cell
         */
        generateCell(structure);

        /**
         * generate line
         */
        generateLine(structure);
        return structure;
    }

    /**
     * random true or false
     *
     * @param probability
     * @return
     */
    public static boolean randomTF(int probability) {
        double rd = Math.random() * 100;
        return rd <= probability;
    }

    /**
     * @param max
     * @param min
     * @return >=min and < max
     */
    public static int randomRange(int max, int min) {
        if (max == 0) {
            return min;
        }
        double rd = Math.random() * 10000000;
        int temp = (int) rd % max;
        if (temp < min) {
            temp = min;
        }
        return temp;
    }

    public static int fontSize() {
        double rd = Math.random() * 1000;
        return Const.baseFont + (int) (rd % 20);
    }

    public static String fontProgram() {
        return defFontStrList.get(randomInt(defFontStrList.size() - 1, 0));
    }

    public static Point randomPoint(int width, int height) {
        int x = randomRange(width, 0);
        int y = randomRange(height, 0);

        return new Point(x, y);
    }

    public static Point randomPoint(TableLayoutConfig config) {
        int left = config.getPaddingLeft();
        int top = config.getPaddingTop();

        int restWidth = config.getTableStartXMaxOff();
        int restHeight = config.getTableStartYMaxOff();

        int rdPer = randomRange(50, 0);

        int x = left + restWidth * rdPer / 100;
        int y = top + restHeight * rdPer / 100;

        return new Point(x, y);
    }

    public static void generateLine(TableStructure structure) {
        Set<Line> lineSet = new HashSet<>();
        for (TableCell tableCell : structure.getCellList()) {
            List<Line> lines = getLines(tableCell, structure);
            lineSet.addAll(lines);
        }
        structure.setLineList(new ArrayList<>(lineSet));
    }

    public static List<Line> getLines(TableCell tableCell, TableStructure structure) {
        Point a = tableCell.getPoint1();
        Point c = tableCell.getPoint2();
        Point b = new Point(c.getX(), a.getY());
        Point d = new Point(a.getX(), c.getY());

        int outBorder = structure.getBorderWidth();
        int innerBorder = structure.getInnerBorderWidth();
        if (structure.isMergeOnBorder()) {
            outBorder = structure.getInnerBorderWidth();
        }

        Line top = new Line(a, b);
        top.setWidth(tableCell.isTop() ? outBorder : innerBorder);
        Line right = new Line(b, c);
        right.setWidth(tableCell.isRight() ? outBorder : innerBorder);
        Line bottom = new Line(c, d);
        bottom.setWidth(tableCell.isBottom() ? outBorder : innerBorder);
        Line left = new Line(d, a);
        left.setWidth(tableCell.isLeft() ? outBorder : innerBorder);

        List<Line> lineList = new ArrayList<>();
        lineList.add(top);
        lineList.add(right);
        lineList.add(bottom);
        lineList.add(left);
        return lineList;
    }

    /**
     * random cell array , like 4*5
     * width array, height array, head column position list,
     *
     * @param structure
     * @param mockData
     */
    public static void randomCellArray(TableLayoutConfig config, TableStructure structure, MockData mockData) {

        Point startPoint = structure.getStartPoint();
        int fontSize = structure.getFontSize();
        double lintHeight = structure.getLineHeight();
        int widthRange = config.getTotalWidth() - startPoint.getX() - config.getPaddingRight();
        int heightRange = config.getTotalHeight() - startPoint.getY() - config.getPaddingBottom();

//        int widthBase = fontSize > config.getCellMinWidth() ? fontSize : config.getCellMinWidth();
        int heightBase = lintHeight > config.getCellMinHeight() ? (int) lintHeight : config.getCellMinHeight();
        List<MockHead> rdHead = getRandomHead(widthRange, structure, mockData, config.isMockDataHeadShuffle());
        List<Integer> widthList = new ArrayList<>(rdHead.size());
        float fontBaseWidth = (1.0f * fontSize) / Const.baseFont * Const.unitCharWidth;
        float blankWidth = structure.getCellLeftPadding() + structure.getCellRightPadding() + cellBlankOff();
        int borderWidth = structure.getBorderWidth();
        int innerBorderWidth = structure.getInnerBorderWidth();
        int ii = 0;
        for (MockHead mockHead : rdHead) {
            float cellW = mockHead.getMaxLenght() * fontBaseWidth + blankWidth;
            if (ii == 0 || ii == (rdHead.size() - 1)) {
                cellW = cellW + borderWidth + innerBorderWidth;
            } else {
                cellW = cellW + 2 * innerBorderWidth;
            }
            widthList.add((int) cellW + 1);
            ii++;
        }
        int rowHeight = (int) (structure.getCellTopPadding() + structure.getCellBottomPadding() + cellBlankHOff() + heightBase);
        int totalR = (int) (heightRange / rowHeight);
        totalR = totalR > 0 ? totalR : 0;
        List<Integer> heightList = new ArrayList<>(totalR);
        for (int i = 0; i < totalR; i++) {
            heightList.add(rowHeight);
        }
        int tableWidth = sum(widthList);
        int tableHeight = sum(heightList);
        structure.setTableHeight(tableHeight);
        structure.setTableWidth(tableWidth);
        structure.setCellWidthList(widthList);
        structure.setCellHeightList(heightList);
        structure.setMockHeadList(rdHead);
    }

    public static List<MockHead> getRandomHead(int widthRange, TableStructure structure, MockData mockData, boolean shuffle) {
        int fontSize = structure.getFontSize();
        float fontBaseWidth = (1.0f * fontSize) / Const.baseFont * Const.unitCharWidth;
        List<MockHead> copyHead = shuffle ? MockDataUtil.copyShuffle(mockData.getHeadList()) : mockData.getHeadList();
        int minCol = Const.minCol;
        int maxCol = minCol;
        float blankWidth = structure.getCellLeftPadding() + structure.getCellRightPadding() + cellBlankOff();

        float temp = 0;
        int borderWidth = structure.getBorderWidth();
        int innerBorderWidth = structure.getInnerBorderWidth();
        for (int i = 0, len = copyHead.size(); i < len; i++) {
            MockHead head = copyHead.get(i);
            float tmpWidth = fontBaseWidth * (head.getMaxLenght()) + blankWidth;
            if (i == 0) {
                tmpWidth = tmpWidth + borderWidth + innerBorderWidth;
            } else {
                tmpWidth = tmpWidth + 2 * innerBorderWidth;
            }

            temp += tmpWidth;
            maxCol = i + 1;
            if (temp > widthRange) {
                maxCol = maxCol - 1;
                break;
            }
        }
        if (minCol > maxCol) {
            minCol = maxCol;
        }
        //TODO need be more reasonable
//        int rd = randomRange(maxCol, minCol);
        int rd = randomRange(maxCol, maxCol);
        logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ max Col:" + maxCol + " rd:" + rd);
        return copyHead.subList(0, rd);
    }

    public static int cellBlankOff() {
        return 5;
    }

    public static int cellBlankHOff() {
        return 5;
    }


    public static void generateCell(TableStructure structure) {
        int colSize = structure.getCellWidthList().size();
        int rowSize = structure.getCellHeightList().size();

        Map<String, TableCell> map = new HashMap<>();
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                TableCell cell = createCell(structure, i, j);
                map.put(cell.getId(), cell);
            }
        }
        CellMergeConfig cellMergeConfig = randomMerge(structure);
        if (cellMergeConfig == null) {
            structure.setCellList(new ArrayList<>(map.values()));
            return;
        }
        String startMergeCellId = getId(cellMergeConfig.getStartRow(), cellMergeConfig.getStartCol());
        String endMergeCellId = getId(cellMergeConfig.getEndRow(), cellMergeConfig.getEndCol());

        TableCell startCell = map.get(startMergeCellId);
        TableCell endCell = map.get(endMergeCellId);
        if (startCell == null || endCell == null) {
            logger.error(MessageFormat.format("Merge error structure: {0}, cellMergeConfig: {1}, map:{2}", structure, cellMergeConfig, map));
            throw new RuntimeException("Merge error");
        }
        int width = endCell.getPoint2().getX() - startCell.getPoint1().getX();
        int height = endCell.getPoint2().getY() - startCell.getPoint1().getY();
        int innerWidth = width - structure.getCellLeftPadding() - structure.getCellRightPadding();
        int innerHeight = height - structure.getCellTopPadding() - structure.getCellBottomPadding();
        startCell.setMergeBegin(true);
        startCell.setPoint2(endCell.getPoint2());
        startCell.setWidth(width);
        startCell.setHeight(height);
        startCell.setContentWidth(innerWidth);
        startCell.setContentHeight(innerHeight);

        List<TableCell> cellList = new ArrayList<>();
        cellList.add(startCell);
        for (int i = cellMergeConfig.getStartRow(); i <= cellMergeConfig.getEndRow(); i++) {
            for (int j = cellMergeConfig.getStartCol(); j <= cellMergeConfig.getEndCol(); j++) {
                String id = getId(i, j);
                Position pos = getPos(i, j);
                structure.getMergePosition().add(pos);
                TableCell mergedCell = map.get(id);
                mergedCell.setMerge(true);
                startCell.getCellList().add(mergedCell);
            }
        }
        for (TableCell tableCell : map.values()) {
            if (!tableCell.isMerge()) {
                cellList.add(tableCell);
            } else {
                structure.getMergedCell().add(tableCell);
            }
        }
        structure.setCellList(cellList);
    }

    public static TableCell createCell(TableStructure structure, int row, int col) {
        TableCell cell = new TableCell();
        String id = getId(row, col);
        cell.setCol(col);
        cell.setRow(row);
        Point startPoint = structure.getStartPoint();

        int width1 = sum(structure.getCellWidthList(), 0, col);
        int width2 = sum(structure.getCellWidthList(), 0, col + 1);
        int height1 = sum(structure.getCellHeightList(), 0, row);
        int height2 = sum(structure.getCellHeightList(), 0, row + 1);

        int x1 = startPoint.getX() + width1;
        int y1 = startPoint.getY() + height1;

        int x2 = startPoint.getX() + width2;
        int y2 = startPoint.getY() + height2;

        Point point1 = new Point(x1, y1);
        Point point2 = new Point(x2, y2);


        boolean isTop = row == 0;
        boolean isBottom = row == structure.getCellHeightList().size() - 1;

        boolean isLeft = col == 0;
        boolean isRight = col == structure.getCellWidthList().size() - 1;


        int height = height2 - height1;
        int width = width2 - width1;

        int contentHeight = height - structure.getCellTopPadding() - structure.getCellBottomPadding();
        int contentWidth = width - structure.getCellLeftPadding() - structure.getCellRightPadding();

        cell.setId(id);
        cell.setRow(row);
        cell.setCol(col);
        cell.setHeight(height);
        cell.setWidth(width);
        cell.setContentHeight(contentHeight);
        cell.setContentWidth(contentWidth);
        cell.setPoint1(point1);
        cell.setPoint2(point2);

        cell.setTop(isTop);
        cell.setBottom(isBottom);
        cell.setLeft(isLeft);
        cell.setRight(isRight);

        return cell;
    }

    public static String getId(int row, int col) {
        return "" + row + "-" + col;
    }

    public static int sum(List<Integer> list, int start, int end) {
        int sum = 0;
        for (int i = 0; i < end; i++) {
            sum += list.get(i);
        }
        return sum;
    }

    public static int sum(List<Integer> list) {
        int sum = 0;
        for (Integer i : list) {
            sum += i;
        }
        return sum;
    }

    private static Position getPos(int x, int y) {
        return new Position(x, y);
    }

    public static CellMergeConfig randomMerge(TableStructure structure) {
        if (!structure.isMergeCell()) {
            return null;
        }
        CellMergeConfig config = new CellMergeConfig();
        int colSize = structure.getCellWidthList().size();
        int rowSize = structure.getCellHeightList().size();

        int offSite = 1;

        int startCol = randomRange(colSize - offSite, 0);

        int startRow = randomRange(rowSize - offSite, 0);

        int endCol = randomRange(colSize - offSite, startCol);

        int endRow = randomRange(rowSize - offSite, startRow);

        config.setStartCol(startCol);
        config.setStartRow(startRow);
        config.setEndCol(endCol);
        config.setEndRow(endRow);

        if (startCol == endCol && startRow == endRow) {
            return null;
        }

        return config;
    }

    /******************************random normal table end***********************/

    interface Constant {

        int numberOnly = 50;
        int stringOnly = 80;
        int mix = 100;
    }


}

package com.tiny.web.controller.integration.util;

import com.tiny.app.model.entity.NameValue;
import com.tiny.common.util.CommonUtil;
import com.tiny.web.controller.http.response.SignatureResp;
import com.tiny.web.controller.ocr.model.Box;
import com.tiny.web.controller.ocr.model.NameVO;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.*;

public class RandomUtil {

    private static final Logger logger = Logger.getLogger(RandomUtil.class);

    private static Map<Long, NameVO> nameVOMap;

    private static Properties layoutProps;

    private static List<String> bizTag;

    private static List<NameValue> layoutList;

    static {
        String filename = "/config/signature-list.properties";
        String layoutListFN = "/config/layout-list.properties";
        String bizTagFN = "/config/biz-tag.txt";
        nameVOMap = new HashMap<>();

        InputStream inputStream = null;
        InputStream inputStream4Layout = null;
        InputStream inputStream4BizTag = null;
        try {
            inputStream = CommonUtil.getInputStream(filename);
            Properties properties = CommonUtil.retrieveFileProperties(inputStream);
            Enumeration enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                Object enumer = enumeration.nextElement();
                String src = properties.getProperty(enumer.toString());
                Long id = NumberUtils.toLong(enumer.toString(), 0);
                NameVO nameVO = new NameVO(id, src);
                nameVO.setEmail(src.replace(" ", "").replace(",", "") + "@xxx.com");
                nameVOMap.put(id, nameVO);
            }

            inputStream4Layout = CommonUtil.getInputStream(layoutListFN);
            layoutProps = CommonUtil.retrieveFileProperties(inputStream4Layout);
            layoutList = new ArrayList<>();
            Enumeration enumeration4Layout = layoutProps.propertyNames();
            while (enumeration4Layout.hasMoreElements()) {
                Object enumer = enumeration4Layout.nextElement();
                String src = layoutProps.getProperty(enumer.toString());
                layoutList.add(new NameValue(enumer.toString(), src));
            }
            inputStream4BizTag = CommonUtil.getInputStream(bizTagFN);
            List<String> tempBizTag = IOUtils.readLines(inputStream4BizTag);
            bizTag = new ArrayList<>();
            for (String bt : tempBizTag) {
                if (StringUtils.isBlank(bt)) {
                    continue;
                }
                bt = StringUtils.trim(bt);
                if (bizTag.contains(bt)) {
                    continue;
                }
                bizTag.add(bt);
            }
            Collections.sort(bizTag);
            Collections.sort(layoutList, new Comparator<NameValue>() {
                @Override
                public int compare(NameValue o1, NameValue o2) {
                    if (o1 == null && o2 == null) {
                        return 0;
                    }
                    if (o1 == null) {
                        return -1;
                    }
                    if (o2 == null) {
                        return 1;
                    }
                    if (o1 == o2) {
                        return 0;
                    }
                    Long o1Long = NumberUtils.toLong(o1.getName());
                    Long o2Long = NumberUtils.toLong(o2.getName());
                    return o1Long.compareTo(o2Long);
                }
            });

        } catch (Exception e) {
            logger.error("read file error - " + filename, e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("input stream close error", e);
                }
            }
            if (inputStream4Layout != null) {
                try {
                    inputStream4Layout.close();
                } catch (IOException e) {
                    logger.error("input stream close error", e);
                }
            }
            if (inputStream4BizTag != null) {
                try {
                    inputStream4BizTag.close();
                } catch (IOException e) {
                    logger.error("input stream close error", e);
                }
            }
        }
    }

    /**
     * @param classId
     * @return
     */
    public static String fetchType(String classId) {
        if (StringUtils.isBlank(classId)) {
            return null;
        }
        Object obj = layoutProps.getProperty(StringUtils.trim(classId));
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    /**
     * @return
     */
    public static List<String> loadBizTag() {
        return bizTag;
    }

    /**
     * @return
     */
    public static List<NameValue> loadLayout() {
        return layoutList;
    }

    /**
     * @return
     */
    public static List<NameVO> loadNameList() {
        return new ArrayList<>(nameVOMap.values());
    }

    /**
     * @param name
     * @return
     */
    public static List<NameVO> queryName(String name) {
        List<NameVO> list = new ArrayList<>();
        if (StringUtils.isBlank(name)) {
            return list;
        }
        String temp = name.toLowerCase().trim();
        for (NameVO nameVO : nameVOMap.values()) {
            String fullName = StringUtils.isBlank(nameVO.getFull()) ? "" : nameVO.getFull().toLowerCase();
            if (StringUtils.contains(fullName, temp)) {
                list.add(nameVO);
            }
        }
        return list;
    }

    /**
     * @param fileName
     * @param page
     * @return
     */
    public static SignatureResp matchSignature(String fileName, String page) {
        boolean matched = random() <= Constant.succ;
        if (!matched) {
            throw new RuntimeException("signature not matched!!!");
        }
        SignatureResp signatureResp = new SignatureResp();
        signatureResp.setFaxId("" + randomInt(999999, 100000) + ".jpg");
        signatureResp.setMatchArea(randomMatchList());
        return signatureResp;
    }

    /**
     * @return
     */
    public static List<Box> randomMatchList() {
        int sLen = (int) (Math.round(random() * 10) % 10 + 1);
        List<Box> list = new ArrayList<>();
        for (int i = 0; i < sLen; i++) {
            list.add(randomBox());
        }
        return list;
    }


    /**
     * @return
     */
    public static Box randomBox2() {
        double w = Constant.W + Constant.W * random();
        double h = Constant.H + Constant.H * random();
        double x = Constant.X + Constant.X * random();
        double y = Constant.Y + Constant.Y * random();
        double rate = 100 * random() + 20;
        List<NameVO> nameList = new ArrayList<>();
        int sLen = (int) (Math.round(random() * 10) % Constant.nameSize + 1);
        if (sLen == 0) {
            sLen = 1;
        }
        if (sLen > Constant.nameSize) {
            sLen = Constant.nameSize;
        }
        for (int i = sLen; i > 0; i--) {
            nameList.add(randomName());
        }
        return new Box("" + w, "" + h, "" + x, "" + y).setRate("" + rate).setNameList(nameList);
    }

    public static Box randomBox() {
        double w = Constant.W + Constant.W * random();
        double h = Constant.H + Constant.H * random();
        double x = Constant.X + Constant.X * random();
        double y = Constant.Y + Constant.Y * random();
        double rate = 100 * random() + 20;
        int sLen = (int) (Math.round(random() * 10) % Constant.nameSize + 1);
        if (sLen == 0) {
            sLen = 1;
        }
        if (sLen > Constant.nameSize) {
            sLen = Constant.nameSize;
        }
        List<NameVO> nameList = randomNameList(sLen);
        return new Box("" + w, "" + h, "" + x, "" + y).setRate("" + rate).setNameList(nameList);
    }

    public static double random() {
        double rd = Math.random();
        return Double.valueOf(Constant.twoDeciaml.format(rd));
    }

    public static String randomPerc() {
        double rd = Math.random() * 100;
        return Constant.twoDeciaml.format(rd);
    }

    /**
     * @return
     */
    public static NameVO randomName1() {
        int fLen = (int) (Math.round(random() * 10) % 10 + 4);
        int sLen = (int) (Math.round(random() * 10) % 10 + 4);
        String firstName = RandomStringUtils.random(fLen, true, false).toLowerCase();
        String secondName = RandomStringUtils.random(sLen, true, false).toLowerCase();
        StringBuilder temp = new StringBuilder();
        temp.append(firstName.substring(0, 1).toUpperCase());
        temp.append(firstName.substring(1));
        temp.append(", ");
        temp.append(secondName.substring(0, 1).toUpperCase());
        temp.append(secondName.substring(1));
        NameVO name = new NameVO();
        name.setFull(temp.toString());
        name.setFirst(firstName);
        name.setSecond(secondName);
        StringBuilder email = new StringBuilder();
        email.append(firstName.substring(0, 1).toUpperCase());
        email.append(secondName.substring(0, 1).toUpperCase());
        email.append(secondName.substring(1));
        email.append(Constant.emailEndfix);
        name.setEmail(email.toString());
        name.setComments("mocked");
        name.setRate(randomPerc());
        name.setId(Long.valueOf(randomInt(480, 0)));
        return name;
    }

    /**
     * @return
     */
    public static NameVO randomName() {
        List<NameVO> list = loadNameList();
        int size = list.size();
        int index = randomInt(size - 1, 0);
        NameVO name = list.get(index);
        name.setComments("mocked");
        name.setRate(randomPerc());
        return name;
    }

    public static List<NameVO> randomNameList(int count) {
        List<NameVO> list = loadNameList();
        Collections.shuffle(list);
        List<NameVO> retList = list.subList(0, count);
        for (NameVO nameVO : retList) {
            nameVO.setRate(randomPerc());
        }
        return retList;
    }

    /**
     * @param writerId
     * @return
     */
    public static NameVO fetch(Long writerId) {
        return nameVOMap.get(writerId);
    }

    interface Constant {
        /**
         * <= 0.6 will be success
         */
        double succ = 0.6;

        String emailEndfix = "@xxx.com";

        double W = 300;
        double H = 100;
        double X = 200;
        double Y = 200;

        Box box = new Box("" + W, "" + H, "" + X, "" + Y);


        DecimalFormat twoDeciaml = new DecimalFormat("#0.00");

        int nameSize = 5;
    }

    public static int randomInt(int maxRow, int minRow) {
        int length = (int) (Math.random() * Integer.MAX_VALUE % maxRow);
        if (length < minRow && length > 0) {
            length = minRow;
        } else if (length == 0) {
            length = maxRow;
        }
        return length;
    }

    public static void main(String[] args) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= 480; i++) {
            NameVO nameVO = randomName();
            builder.append(i).append("=");
            builder.append(nameVO.getFull()).append("|");
            builder.append(nameVO.getEmail()).append("|");
            builder.append(nameVO.getFirst()).append("|");
            builder.append(nameVO.getSecond()).append("\n");
        }
        FileUtils.writeStringToFile(new File("D:\\data\\code\\github-workspace\\tiny\\app\\deploy\\src\\main\\resources\\config\\signature-list.properties"), builder.toString());
    }
}

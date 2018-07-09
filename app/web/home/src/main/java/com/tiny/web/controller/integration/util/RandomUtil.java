package com.tiny.web.controller.integration.util;

import com.tiny.web.controller.http.response.SignatureResp;
import com.tiny.web.controller.ocr.model.Box;
import com.tiny.web.controller.ocr.model.NameVO;
import org.apache.commons.lang.RandomStringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RandomUtil {

    /**
     * @param fileName
     * @param page
     * @return
     */
    public static SignatureResp matchSignature(String fileName, String page) {
        boolean matched = random() <= Constant.succ;
        if (!matched) {
            throw new RuntimeException("Oops, signature not matched!!!");
        }
        SignatureResp signatureResp = new SignatureResp();
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
    public static Box randomBox() {
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
    public static NameVO randomName() {
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
        return name;
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
}

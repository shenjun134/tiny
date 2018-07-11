package com.tiny.web.controller.integration.convert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tiny.web.controller.http.response.SignatureResp;
import com.tiny.web.controller.integration.entity.Author;
import com.tiny.web.controller.integration.entity.FaxResult;
import com.tiny.web.controller.integration.entity.MatchResult;
import com.tiny.web.controller.integration.entity.SignatureResult;
import com.tiny.web.controller.integration.util.RandomUtil;
import com.tiny.web.controller.ocr.model.Box;
import com.tiny.web.controller.ocr.model.NameVO;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import java.text.DecimalFormat;

public class FaxResultConverter {
    /**
     * logger
     */
    private static Logger logger = Logger.getLogger(FaxResultConverter.class);

    public static FaxResult json2Fax(String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, FaxResult.class);
    }

    public static SignatureResp json2Signature(String json) {
        try {
            Assert.hasText(json, "json is blank");
            return fax2Resp(json2Fax(json));
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            logger.error("json2Signature convert error", e);
            throw new RuntimeException("Json covert Signature error");
        }
    }


    public static SignatureResp fax2Resp(FaxResult faxResult) {
        SignatureResp signatureResp = new SignatureResp();

        if (faxResult.getFaxes() != null && faxResult.getFaxes().length > 0) {
//            for(SignatureResult signatureResult : faxResult.getFaxes()){
//                Box box = match2Box(signatureResult);
//            }
            SignatureResult signatureResult = faxResult.getFaxes()[0];
            if (signatureResult.getSignatures() != null) {
                for (MatchResult matchResult : signatureResult.getSignatures()) {
                    Box box = match2Box(matchResult);
                    signatureResp.getMatchArea().add(box);
                }
            }
        }

        return signatureResp;
    }


    public static Box match2Box(MatchResult matchResult) {
        Box box = new Box();

        box.setW("" + matchResult.getWidth());
        box.setH("" + matchResult.getHeight());
        box.setX("" + matchResult.getXmin());
        box.setY("" + matchResult.getYmin());
        box.setRate("" + (matchResult.getScore() != null ? matchResult.getScore() * 100 : 0));

        if (matchResult.getWriters() != null) {
            for (Author author : matchResult.getWriters()) {
                NameVO nameVO = auth2Name(author);
                box.getNameList().add(nameVO);
            }
        }
        return box;
    }

    public static NameVO auth2Name(Author author) {
        NameVO nameVO = RandomUtil.fetch(author.getWriter_id());
        if (nameVO == null) {
            nameVO = new NameVO();
        }
        double rate = author.getProbability() == null ? 0 : author.getProbability() * 100;
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        String rateStr = decimalFormat.format(rate);
        nameVO.setRate(rateStr);
        nameVO.setId(author.getWriter_id());
        nameVO.setComments("Writer Id:" + author.getWriter_id() + " | Probability:" + rateStr);
        return nameVO;
    }
}

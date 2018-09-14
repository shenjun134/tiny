package com.tiny.web.controller.integration.service.impl;

import com.tiny.common.entity.GridLayoutConfig;
import com.tiny.common.entity.TableVO;
import com.tiny.common.util.GridLayoutUtil;
import com.tiny.common.util.LogUtil;
import com.tiny.common.util.TableUtil;
import com.tiny.web.controller.http.request.RecognitionReq;
import com.tiny.web.controller.integration.convert.CommonConverter;
import com.tiny.web.controller.integration.entity.ContentResult;
import com.tiny.web.controller.integration.entity.GridLayoutResult;
import com.tiny.web.controller.integration.entity.TableLayoutResult;
import com.tiny.web.controller.integration.entity.LayoutResult;
import com.tiny.web.controller.integration.enums.LayoutEnum;
import com.tiny.web.controller.integration.util.RandomUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component("mockApiService")
public class MockApiService extends AbstractApiService {

    interface Constant {
        int seed = 50;
        int successRate = 80;
        String typeBegin = "TYPE-";
    }

    /**
     * logger
     */
    private static final Logger logger = Logger.getLogger(MockApiService.class);

    @Override
    protected List<LayoutResult> doLayoutRecon(RecognitionReq request) {
        return randomLayoutList();
    }

    @Override
    protected void beforeLayout(RecognitionReq request) {
        commonAssert(request);
    }


    @Override
    protected void beforeContent(RecognitionReq request) {
        commonAssert(request);
    }


    @Override
    protected ContentResult doContentRecon(RecognitionReq request) {
        try {
            boolean isSucc = randomBool(Constant.successRate);
            if (!isSucc) {
                throw new RuntimeException("Recognition content fail!");
            }
            LayoutResult layoutResult = randomLayout();
            LayoutEnum layoutEnum = LayoutEnum.codeOf(layoutResult.getType());
            if (layoutEnum == null) {
                throw new RuntimeException("Recognition content fail!");
            }
            switch (layoutEnum) {
                case TABLE:
                    return randomTable(layoutResult);
                case GRID:
//                    return randomGrid(layoutResult);
                    return CommonConverter.mockContentResult("");
            }
            throw new RuntimeException("Recognition content fail!");
        } catch (Exception e) {
            LogUtil.error(logger, e, "doContentRecon error with - {0}", request);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * @param layoutResult
     * @return
     */
    private ContentResult randomTable(LayoutResult layoutResult) {
        TableLayoutResult tableLayoutResult = TableLayoutResult.newInstance(layoutResult);
        TableVO tableVO = TableUtil.random();
        tableLayoutResult.setAllList(CommonConverter.convert2ListList(tableVO));
        tableLayoutResult.setWidth(CommonConverter.Constant.mockedWidth);
        tableLayoutResult.setHeight(CommonConverter.Constant.mockedHeight);

        ContentResult contentResult = new ContentResult();
        contentResult.setResult(tableLayoutResult);
        return contentResult;
    }

    private ContentResult randomGrid(LayoutResult layoutResult) {
        GridLayoutResult gridLayoutResult = GridLayoutResult.newInstance(layoutResult);

        GridLayoutConfig config = new GridLayoutConfig();
        com.tiny.common.entity.GridLayoutResult result = GridLayoutUtil.randomGridWithText(config);
        gridLayoutResult.setAllList(CommonConverter.convert2List(result));
        gridLayoutResult.setBeginX(config.getPaddingLeft());
        gridLayoutResult.setBeginY(config.getPaddingTop());

        ContentResult contentResult = new ContentResult();
        contentResult.setResult(gridLayoutResult);
        return contentResult;
    }

    private List<LayoutResult> randomLayoutList() {
        List<LayoutResult> list = new ArrayList<>();
        int count = RandomUtil.randomInt(5, 1);
        for (int i = 0; i < count; i++) {
            list.add(randomLayout());
        }
        return list;
    }

    private LayoutResult randomLayout() {
        LayoutResult layoutResult = new LayoutResult();
        layoutResult.setComments("It is mock API");
        layoutResult.setId(RandomStringUtils.random(3, false, true));
        layoutResult.setTag(StringUtils.join(new String[]{
                Constant.typeBegin, RandomStringUtils.random(2, false, true)
        }));
        layoutResult.setType(random().getCode());
        layoutResult.setProbability(RandomUtil.randomPerc());
        return layoutResult;
    }


    private LayoutEnum random() {
        return randomBool(Constant.seed) ? LayoutEnum.TABLE : LayoutEnum.GRID;
    }


    private boolean randomBool(int percentage) {
        double rd = Math.random() * 100;
        return rd <= percentage;
    }


}

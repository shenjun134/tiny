package com.tiny.web.controller.integration.service.impl;

import com.tiny.common.entity.*;
import com.tiny.common.entity.GridLayoutResult;
import com.tiny.common.util.GridLayoutUtil;
import com.tiny.common.util.LogUtil;
import com.tiny.common.util.MockDataUtil;
import com.tiny.common.util.TableUtil;
import com.tiny.web.controller.http.request.RecognitionReq;
import com.tiny.web.controller.integration.convert.CommonConverter;
import com.tiny.web.controller.integration.entity.*;
import com.tiny.web.controller.integration.enums.LayoutEnum;
import com.tiny.web.controller.integration.util.RandomUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.TreeMap;

@Component("mockApiService")
public class MockApiService extends AbstractApiService {

    interface Constant {
        int seed = 50;
        int successRate = 80;
        String typeBegin = "type-";
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
    protected LayoutWrapper doLayoutRecon2(RecognitionReq request) {
        try {
            LayoutResult layoutMain = randomLayout();
            //get the image real width & height
            setImageWH(request, layoutMain);
            GridLayoutConfig config = new GridLayoutConfig();
            config.setTotalWidth((int) layoutMain.getWidth());
            config.setTotalHeight((int) layoutMain.getHeight());
            com.tiny.common.entity.GridLayoutResult result = GridLayoutUtil.randomGrid(config);
            List<LayoutResult> layoutList = new ArrayList<>();
            for (Rectangle rectangle : result.getRectList()) {
                if (rectangle.isSplit()) {
                    continue;
                }
                LayoutResult temp = randomLayout();
                LayoutEnum layoutEnum = random();
                temp.setType(layoutEnum.getCode());
                temp.setTag(layoutEnum.getMessage());
                temp.setHeight(rectangle.height());
                temp.setWidth(rectangle.width());
                temp.setX(rectangle.getPoint1().getX());
                temp.setY(rectangle.getPoint1().getY());
                temp.setId(rectangle.getName());
                layoutList.add(temp);
            }
            LayoutWrapper layoutWrapper = new LayoutWrapper();
            layoutWrapper.setWidth(layoutMain.getWidth());
            layoutWrapper.setHeight(layoutMain.getHeight());
            layoutWrapper.setId(layoutMain.getId());
            layoutWrapper.setLayoutList(layoutList);
            return layoutWrapper;
        } catch (Exception e) {
            LogUtil.error(logger, e, "do layout recon 2 error - {0}", request);
            throw new RuntimeException("do layout recon 2 error", e);
        }
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
            //get the image real width & height
            setImageWH(request, layoutResult);

            if (layoutEnum == null) {
                throw new RuntimeException("Recognition content fail!");
            }
            switch (layoutEnum) {
                case TABLE:
                    return randomTable(layoutResult, false);
                case GRID:
                    return randomGrid(layoutResult, false);
//                    return CommonConverter.mockContentResult("");
            }
            throw new RuntimeException("Recognition content fail!");
        } catch (Exception e) {
            LogUtil.error(logger, e, "doContentRecon error with - {0}", request);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    protected List<ContentResult> doContentRecon2(RecognitionReq request) {
        List<ContentResult> contentResults = new ArrayList<>();
        for (LayoutResult layoutResult : request.getSelectedLayout()) {
            LayoutEnum layoutEnum = LayoutEnum.codeOf(layoutResult.getType());
            layoutEnum = layoutEnum == null ? LayoutEnum.GRID : layoutEnum;
            switch (layoutEnum) {
                case TABLE: {
                    ContentResult temp = randomTable(layoutResult, true);
                    if (temp.getResult() != null) {
                        TableLayoutResult tableLayoutResult = (TableLayoutResult) temp.getResult();
                        for (List<RectangleVO> rowList : tableLayoutResult.getAllList()) {
                            for (RectangleVO cell : rowList) {
                                cell.setXmin(layoutResult.getX() + cell.getXmin());
                                cell.setYmin(layoutResult.getY() + cell.getYmin());
                            }
                        }
                    }
                    contentResults.add(temp);
                    break;
                }
                case GRID: {
                    ContentResult temp = randomGrid(layoutResult, true);
                    if (temp.getResult() != null) {
                        com.tiny.web.controller.integration.entity.GridLayoutResult tableLayoutResult =
                                (com.tiny.web.controller.integration.entity.GridLayoutResult) temp.getResult();
                        for (RectangleVO cell : tableLayoutResult.getAllList()) {
                            cell.setXmin(layoutResult.getX() + cell.getXmin());
                            cell.setYmin(layoutResult.getY() + cell.getYmin());
                        }
                    }
                    contentResults.add(temp);
                    break;
                }
            }
        }
        return contentResults;
    }

    @Override
    protected void beforeContent2(RecognitionReq request) {
        super.beforeContent2(request);
        Assert.state(CollectionUtils.isNotEmpty(request.getSelectedLayout()), "selected layout is blank");
    }

    private void setImageWH(RecognitionReq request, LayoutResult layoutResult) {
        String imagePath = request.getImageAbsPath();
        String imageName = request.getReconImage();
        String join = "/";
        if (StringUtils.endsWith(imagePath, "/") || StringUtils.endsWith(imagePath, "\\")) {
            join = "";
        }
        try {
            BufferedImage bimg = ImageIO.read(new File(imagePath + join + imageName));
            int width = bimg.getWidth();
            int height = bimg.getHeight();
            layoutResult.setWidth(width);
            layoutResult.setHeight(height);
        } catch (Exception e) {
            logger.error("setImageWH error", e);
        } finally {

        }

    }

    /**
     * @param layoutResult
     * @return
     */
    private ContentResult randomTable2(LayoutResult layoutResult) {
        TableLayoutResult tableLayoutResult = TableLayoutResult.newInstance(layoutResult);
        TableVO tableVO = TableUtil.random();
        tableLayoutResult.setAllList(CommonConverter.convert2ListList(tableVO));
//        tableLayoutResult.setWidth(CommonConverter.Constant.mockedWidth);
//        tableLayoutResult.setHeight(CommonConverter.Constant.mockedHeight);

        ContentResult contentResult = new ContentResult();
        contentResult.setResult(tableLayoutResult);
        return contentResult;
    }

    private TableLayoutConfig mockConfig() {
        Properties properties = new Properties();
        String filePath = "metadata/normal-table-layout.properties";
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
            properties.load(inputStream);
        } catch (Exception e) {
            logger.error("load properties fail", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        TableLayoutConfig tableLayoutConfig = new TableLayoutConfig(properties);
        return tableLayoutConfig;
    }

    /**
     * @param layoutResult
     * @return
     */
    private ContentResult randomTable(LayoutResult layoutResult, boolean withoutPadding) {
        String mockDataFile = "metadata/mock-data-basic.txt";
        TableLayoutResult tableLayoutResult = TableLayoutResult.newInstance(layoutResult);
        TableLayoutConfig config = mockConfig();
        if (withoutPadding) {
            int padding = 0;
            config.setPaddingLeft(padding);
            config.setPaddingTop(padding);
            config.setPaddingRight(padding);
            config.setPaddingBottom(padding);
        }
        //TODO if cannot split to cell ....
        config.setTotalHeight((int) layoutResult.getHeight());
        config.setTotalWidth((int) layoutResult.getWidth());
        MockData mockData = MockDataUtil.parseMockData(mockDataFile);
        TableStructure structure = TableUtil.getNormalStructure(config, mockData);
        TreeMap<Integer, TreeMap<Integer, TableCell>> rowColCell = new TreeMap<>();
        if (structure != null) {
            for (TableCell tableCell : structure.getCellList()) {
                Integer row = tableCell.getRow();
                Integer col = tableCell.getCol();
                setText(structure, mockData, tableCell, config);
                TreeMap<Integer, TableCell> rowTree = rowColCell.get(row);
                if (rowTree == null) {
                    rowTree = new TreeMap<Integer, TableCell>();
                    rowColCell.put(row, rowTree);
                }
                rowTree.put(col, tableCell);
            }
        }
        tableLayoutResult.setAllList(CommonConverter.convert2ListList(rowColCell));

        ContentResult contentResult = new ContentResult();
        contentResult.setResult(tableLayoutResult);
        return contentResult;
    }

    private void setText(TableStructure structure, MockData mockData, TableCell rectangle, TableLayoutConfig config) {
        List<String> list = new ArrayList<>();
        int col = rectangle.getCol();
        int row = rectangle.getRow();
        int size = structure.getMockHeadList().size();
        if (size < col + 1) {
            logger.warn("unknown col: " + col + ", head size:" + size);
            list.add(config.getBlankFillString());
            rectangle.setTextList(list);
            return;
        }
        MockHead head = structure.getMockHeadList().get(col);
        if (row == 0) {
            list.add(head.getName());
            rectangle.setTextList(list);
            return;
        }
        int rowSize = mockData.getValueList().size();
        int rd = TableUtil.randomRange(rowSize, 1);
        String txt = mockData.getValueList().get(rd).get(head.getIndex());
        if (StringUtils.isNotBlank(txt)) {
            list.add(txt);
        }
        if (StringUtils.isBlank(txt) && !config.isBlankFillDisable()) {
            list.add(config.getBlankFillString());
        }
        rectangle.setTextList(list);
    }

    private ContentResult randomGrid(LayoutResult layoutResult, boolean withoutPadding) {
        com.tiny.web.controller.integration.entity.GridLayoutResult gridLayoutResult = com.tiny.web.controller.integration.entity.GridLayoutResult.newInstance(layoutResult);

        GridLayoutConfig config = new GridLayoutConfig();
        if (withoutPadding) {
            int padding = 5;
            config.setPaddingLeft(padding);
            config.setPaddingTop(padding);
            config.setPaddingRight(padding);
            config.setPaddingBottom(padding);
        }
        config.setTotalWidth((int) layoutResult.getWidth());
        config.setTotalHeight((int) layoutResult.getHeight());
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
        int count = RandomUtil.randomInt(5, 5);
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
                "", RandomStringUtils.random(1, false, true)
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

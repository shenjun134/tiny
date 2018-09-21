package com.tiny.web.controller.integration.convert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tiny.common.entity.*;
import com.tiny.common.entity.GridLayoutResult;
import com.tiny.common.util.LogUtil;
import com.tiny.web.controller.integration.entity.*;
import com.tiny.web.controller.integration.util.RandomUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CommonConverter {

    /**
     * logger
     */
    private static final Logger logger = Logger.getLogger(CommonConverter.class);


    public interface Constant {
        String split = "\n";
        Map<String, String> replaceMap = new HashMap<String, String>() {
            {
                put("\"\"\",", "\"\\\"\",");
                put("\"\\\",", "\"\\\\\",");
                put("\\\"\"\\\\\",", "\" \",");
            }
        };

        double mockedWidth = 800;
        double mockedHeight = 2000;

    }

    /**
     * @param cellVO
     * @return
     */
    public static RectangleVO convert2Rect(CellVO cellVO) {
        RectangleVO rectangleVO = new RectangleVO();
        rectangleVO.setText(cellVO.getValue() == null ? "" : cellVO.getValue().toString());
        rectangleVO.setXmin(RandomUtil.randomInt((int) Constant.mockedWidth, 0));
        rectangleVO.setYmin(RandomUtil.randomInt((int) Constant.mockedHeight, 0));
        rectangleVO.setWidth(RandomUtil.randomInt((int) Constant.mockedWidth, 0));
        rectangleVO.setHeight(RandomUtil.randomInt((int) Constant.mockedHeight, 0));
        return rectangleVO;
    }


    /**
     * @param rowVO
     * @return
     */
    public static List<RectangleVO> convert2RectList(RowVO rowVO) {
        List<RectangleVO> list = new ArrayList<>(rowVO.getList().size());
        for (CellVO cellVO : rowVO.getList()) {
            list.add(convert2Rect(cellVO));
        }
        return list;
    }

    /**
     * @param tableVO
     * @return
     */
    public static List<List<RectangleVO>> convert2ListList(TableVO tableVO) {
        List<List<RectangleVO>> listList = new ArrayList<>();
        listList.add(convert2RectList(tableVO.getHeader()));
        for (RowVO rowVO : tableVO.getBodyList()) {
            listList.add(convert2RectList(rowVO));
        }
        return listList;
    }

    /**
     * @param rectangle
     * @return
     */
    public static RectangleVO convert2Rect(Rectangle rectangle) {
        RectangleVO rectangleVO = new RectangleVO();
        String[] textArr = new String[rectangle.getText().size()];
        rectangleVO.setText(StringUtils.join(rectangle.getText().toArray(textArr), Constant.split));
        rectangleVO.setHeight(rectangle.height());
        rectangleVO.setWidth(rectangle.width());

        rectangleVO.setXmin(rectangle.getPoint1().getX());
        rectangleVO.setYmin(rectangle.getPoint1().getY());
        rectangleVO.setXmax(rectangle.getPoint2().getX());
        rectangleVO.setYmax(rectangle.getPoint2().getY());

        return rectangleVO;
    }


    /**
     * @param gridLayoutResult
     * @return
     */
    public static List<RectangleVO> convert2List(GridLayoutResult gridLayoutResult) {
        List<RectangleVO> list = new ArrayList<>();
        for (Rectangle rectangle : gridLayoutResult.getRectList()) {
            if (rectangle.isSplit()) {
                continue;
            }
            list.add(convert2Rect(rectangle));
        }
        return list;
    }

    public static List<LayoutResult> parseLayoutResult(String json) {
        List<LayoutResult> list = new ArrayList<>();
        if (StringUtils.isBlank(json)) {
            return list;
        }
        Gson gson = new GsonBuilder().create();
        FaxClassResult faxClassResult = gson.fromJson(json, FaxClassResult.class);
        if (faxClassResult == null || faxClassResult.getClasses() == null) {
            return list;
        }
        for (FaxClass faxClass : faxClassResult.getClasses()) {
            LayoutResult layoutResult = new LayoutResult();
            layoutResult.setProbability("" + 100 * faxClass.getProbability());
            layoutResult.setType(RandomUtil.fetchType(faxClass.getClass_id()));
            layoutResult.setTag(faxClass.getClass_id());
            layoutResult.setId(faxClass.getClass_id());
            list.add(layoutResult);
        }
        return list;
    }

    public static com.tiny.web.controller.integration.entity.ContentResult parseContentResult(String srcJson) {
        if (StringUtils.isBlank(srcJson)) {
            return null;
        }
        String json = srcJson;
        for (Map.Entry<String, String> entry : Constant.replaceMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            json = StringUtils.replace(json, key, value);
        }

        JSONObject jsonObject = JSONObject.fromObject(json);
        Object faxesObj = jsonObject.get("faxes");
        if (faxesObj == null) {
            return null;
        }
        JSONArray faxesArr = (JSONArray) faxesObj;
        if (faxesArr.size() == 0) {
            return null;
        }
        com.tiny.web.controller.integration.entity.GridLayoutResult result = new com.tiny.web.controller.integration.entity.GridLayoutResult();
        JSONObject faxObj = (JSONObject) faxesArr.get(0);
        result.setWidth(faxObj.getDouble("width"));
        result.setHeight(faxObj.getDouble("height"));
        result.setId(faxObj.getString("fax_id"));

        Object isTableObj = faxObj.get("is_table");
        boolean isTable = isTableObj == null ? false : Boolean.parseBoolean(isTableObj.toString());
        Object rowObj = faxObj.get("row_num");
        int row = rowObj == null ? 0 : (int) NumberUtils.toDouble(rowObj.toString(), 0);
        Object columnObj = faxObj.get("col_num");
        int column = columnObj == null ? 0 : (int) NumberUtils.toDouble(columnObj.toString(), 0);

        JSONArray cellsArr = (JSONArray) faxObj.get("cells");
        if (cellsArr == null || cellsArr.size() == 0) {
            return null;
        }
        for (Object cellObj : cellsArr) {
            JSONObject cell = (JSONObject) cellObj;
            RectangleVO rectangleVO = parseRect(cell);
            result.getAllList().add(rectangleVO);
        }
        //SORT

//        Collections.sort(result.getAllList(), new Comparator<RectangleVO>() {
//            @Override
//            public int compare(RectangleVO o1, RectangleVO o2) {
//                int result = compareRect(o1, o2);
//                return result;
//            }
//
//            public int compareRect(RectangleVO o1, RectangleVO o2) {
//                if(o1 == null && o2 == null ){
//                    return 0;
//                }
//                if(o1 == null){
//                    return 1;
//                }
//                if(o2 == null){
//                    return -1;
//                }
//                if(o1 == o2){
//                    return 0;
//                }
//                int yResult = Double.valueOf(o1.getYmin()).compareTo(Double.valueOf(o2.getYmin()));
//                if(yResult == 0){
//                    return Double.valueOf(o1.getXmin()).compareTo(o2.getXmin());
//                }
//                return yResult;
//            }
//        });

        if (isTable) {
            TableLayoutResult layoutResult = grid2Table(result, row, column);
            if (layoutResult != null) {
                ContentResult contentResult = new ContentResult();
                contentResult.setResult(layoutResult);
                return contentResult;
            }
        }

        ContentResult contentResult = new ContentResult();
        contentResult.setResult(result);
        return contentResult;
    }


    /**
     * @param gridResult
     * @param row
     * @param column
     * @return if size!=row*column will be null
     */
    private static com.tiny.web.controller.integration.entity.TableLayoutResult grid2Table(com.tiny.web.controller.integration.entity.GridLayoutResult gridResult, int row, int column) {
        if (gridResult.getAllList().size() != row * column) {
            LogUtil.error(logger, "convert grid2Table fail, size not match, size:{0}, row: {1}, column: {2}", gridResult.getAllList().size(), row, column);
            return null;
        }
        com.tiny.web.controller.integration.entity.TableLayoutResult tableResult = com.tiny.web.controller.integration.entity.TableLayoutResult.newInstance(gridResult);
        for (int i = 0; i < row; i++) {
            List<RectangleVO> rowData = new ArrayList<>();
            int startIndex = i * column;
            for (int j = 0; j < column; j++) {
                RectangleVO rectangleVO = gridResult.getAllList().get(startIndex + j);
                rowData.add(rectangleVO);
            }
            tableResult.getAllList().add(rowData);
        }
        return tableResult;
    }

    private static RectangleVO parseRect(JSONObject cell) {
        RectangleVO rectangleVO = new RectangleVO();
        JSONArray ocrArr = (JSONArray) cell.get("ocr");
        double width = cell.getDouble("width");
        double height = cell.getDouble("height");
        double score = cell.getDouble("score");
        double xmin = cell.getDouble("xmin");
        double ymin = cell.getDouble("ymin");

        rectangleVO.setWidth(width);
        rectangleVO.setHeight(height);
        rectangleVO.setScore("" + score * 100);
        rectangleVO.setXmax(xmin + width);
        rectangleVO.setYmax(ymin + height);
        rectangleVO.setXmin(xmin);
        rectangleVO.setYmin(ymin);
        List<CharVO> list = new ArrayList<>(ocrArr.size());
        StringBuilder stringBuilder = new StringBuilder();
        for (Object obj : ocrArr) {
            JSONObject ocrObj = (JSONObject) obj;
            CharVO charVO = parseChar(ocrObj);
            stringBuilder.append(charVO.getStr());
            list.add(charVO);
        }
        rectangleVO.setText(stringBuilder.toString());
        rectangleVO.setCharList(list);
        return rectangleVO;
    }

    private static CharVO parseChar2(JSONObject ocrObj) {
        CharVO charVO = new CharVO();
        Set<String> set = ocrObj.keySet();
        for (String key : set) {
            charVO.setStr(key);
            JSONObject json = (JSONObject) ocrObj.get(key);

            double width = json.getDouble("width");
            double height = json.getDouble("height");
            double xmin = json.getDouble("xmin");
            double ymin = json.getDouble("ymin");

            charVO.setHeight(height);
            charVO.setWidth(width);
            charVO.setXmin(xmin);
            charVO.setYmin(ymin);
            charVO.setXmax(xmin + width);
            charVO.setYmax(ymin + height);

            break;
        }
        return charVO;
    }

    private static CharVO parseChar(JSONObject ocrObj) {
        CharVO charVO = new CharVO();
        double width = ocrObj.getDouble("width");
        double height = ocrObj.getDouble("height");
        double xmin = ocrObj.getDouble("xmin");
        double ymin = ocrObj.getDouble("ymin");
        String str = ocrObj.getString("char");

        charVO.setHeight(height);
        charVO.setWidth(width);
        charVO.setXmin(xmin);
        charVO.setYmin(ymin);
        charVO.setXmax(xmin + width);
        charVO.setYmax(ymin + height);
        charVO.setStr(str);
        return charVO;
    }

    public static com.tiny.web.controller.integration.entity.ContentResult mockContentResult(String srcJson) {
        String path = "D:\\data\\code\\github-workspace\\tiny\\app\\web\\home\\src\\test\\resources\\table-result-2.json";
        String jsonStr = null;
        try {
            jsonStr = FileUtils.readFileToString(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return parseContentResult(jsonStr);
    }

    public static void main(String[] args) throws IOException {
//        String path = "D:\\data\\code\\github-workspace\\tiny\\app\\web\\home\\src\\test\\resources\\table-result-2.json";
        String path = "D:\\data\\code\\github-workspace\\tiny\\app\\web\\home\\src\\test\\resources\\022979.json";
        String jsonStr = FileUtils.readFileToString(new File(path));

        Object obj = parseContentResult(jsonStr);
        System.out.println(obj);

    }
}

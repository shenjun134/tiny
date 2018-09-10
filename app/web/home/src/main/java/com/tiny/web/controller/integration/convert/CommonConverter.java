package com.tiny.web.controller.integration.convert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tiny.common.entity.*;
import com.tiny.common.entity.GridLayoutResult;
import com.tiny.web.controller.integration.entity.*;
import com.tiny.web.controller.integration.util.RandomUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CommonConverter {


    interface Constant {
        String split = "\n";
        Map<String, String> replaceMap = new HashMap<String, String>() {
            {
                put("{\"\"\"", "{\"\\\"\"");
                put("{\"\\\"", "{\"\\\\\"");
            }
        };

    }

    /**
     * @param cellVO
     * @return
     */
    public static RectangleVO convert2Rect(CellVO cellVO) {
        RectangleVO rectangleVO = new RectangleVO();
        rectangleVO.setText(cellVO.getValue() == null ? "" : cellVO.getValue().toString());
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

        JSONArray cellsArr = (JSONArray) faxObj.get("cells");
        if (cellsArr == null || cellsArr.size() == 0) {
            return null;
        }
        for (Object cellObj : cellsArr) {
            JSONObject cell = (JSONObject) cellObj;
            RectangleVO rectangleVO = parseRect(cell);
            result.getAllList().add(rectangleVO);
        }
        ContentResult contentResult = new ContentResult();
        contentResult.setResult(result);
        return contentResult;
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

    private static CharVO parseChar(JSONObject ocrObj) {
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
        String path = "D:\\data\\code\\github-workspace\\tiny\\app\\web\\home\\src\\test\\resources\\table-result-2.json";
        String jsonStr = FileUtils.readFileToString(new File(path));


        Object obj = parseContentResult(jsonStr);
        System.out.println(obj);

    }
}

package com.tiny.web.controller.integration.convert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.tiny.common.entity.*;
import com.tiny.common.entity.GridLayoutResult;
import com.tiny.common.util.LogUtil;
import com.tiny.web.controller.integration.entity.*;
import com.tiny.web.controller.integration.enums.LayoutEnum;
import com.tiny.web.controller.integration.util.RandomUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
     * @param rowColCell
     * @return
     */
    public static List<List<RectangleVO>> convert2ListList(TreeMap<Integer, TreeMap<Integer, TableCell>> rowColCell) {
        List<List<RectangleVO>> listList = new ArrayList<>();
        for (TreeMap<Integer, TableCell> rowTree : rowColCell.values()) {
            List<RectangleVO> row = new ArrayList<>();
            for (TableCell tableCell : rowTree.values()) {
                row.add(convert2Rect(tableCell));
            }
            listList.add(row);
        }
        return listList;
    }

    /**
     * @param tableCell
     * @return
     */
    public static RectangleVO convert2Rect(TableCell tableCell) {
        RectangleVO rectangleVO = new RectangleVO();
        rectangleVO.setText(StringUtils.join(tableCell.getTextList(), Constant.split));
        rectangleVO.setHeight(tableCell.getHeight());
        rectangleVO.setWidth(tableCell.getWidth());
        rectangleVO.setXmin(tableCell.getPoint1().getX());
        rectangleVO.setYmin(tableCell.getPoint1().getY());
        rectangleVO.setXmax(tableCell.getPoint2().getX());
        rectangleVO.setYmax(tableCell.getPoint2().getY());

        return rectangleVO;
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

    public static LayoutWrapper parseLayoutResult2(String inputJson) {
        String json = StringUtils.replace(inputJson, "\\\"", "\"");
        json = StringUtils.removeStart(json, "\"");
        json = StringUtils.removeEnd(json, "\"");
        System.out.println(inputJson);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(json);

        LayoutWrapper wrapper = new LayoutWrapper();
        if (StringUtils.isBlank(json)) {
            return wrapper;
        }
        JSONObject jsonObject = JSONObject.fromObject(json);
        Object faxesObj = jsonObject.get("fax_id");
        if (faxesObj == null) {
            return wrapper;
        }
        wrapper.setId(faxesObj.toString());
        JSONObject areas = jsonObject.getJSONObject("areas");
        Iterator<String> keys = areas.keys();
        if(keys == null ){
            return wrapper;
        }
        while(keys.hasNext()){
            String key = keys.next();
            Object obj = areas.get(key);
            JSONObject jsonO = (JSONObject) obj;
            LayoutResult layoutResult = new LayoutResult();
            layoutResult.setProbability("" + 100 * NumberUtils.toDouble(jsonO.get("score").toString()));
            LayoutEnum layoutEnum = LayoutEnum.codeOf(jsonO.get("tag").toString() + "-ly");
            layoutEnum = layoutEnum == null ? LayoutEnum.GRID : layoutEnum;
            layoutResult.setType(layoutEnum.getCode());
            layoutResult.setTag(layoutEnum.getMessage());
            layoutResult.setId(key);
            double xmin = NumberUtils.toDouble(jsonO.get("xmin").toString());
            double ymin = NumberUtils.toDouble(jsonO.get("ymin").toString());
            double xmax = NumberUtils.toDouble(jsonO.get("xmax").toString());
            double ymax = NumberUtils.toDouble(jsonO.get("ymax").toString());
            layoutResult.setWidth(xmax - xmin);
            layoutResult.setHeight(ymax -ymin);
            layoutResult.setX(xmin);
            layoutResult.setY(ymin);
            layoutResult.setComments("from remote api");
            wrapper.getLayoutList().add(layoutResult);
        }
        return wrapper;
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

    public static List<com.tiny.web.controller.integration.entity.ContentResult> parseContentResult2(String inputJson) {
        String srcJson = StringUtils.replace(inputJson, "\\\"", "\"");
        srcJson = StringUtils.removeStart(srcJson, "\"");
        srcJson = StringUtils.removeEnd(srcJson, "\"");
        System.out.println(inputJson);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println(srcJson);

        List<com.tiny.web.controller.integration.entity.ContentResult> list = new ArrayList<>();
        if (StringUtils.isBlank(srcJson)) {
            return list;
        }
        String json = srcJson;
        for (Map.Entry<String, String> entry : Constant.replaceMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            json = StringUtils.replace(json, key, value);
        }

        JSONObject jsonObject = JSONObject.fromObject(json);
        Iterator<String> keyIt = jsonObject.keys();
        if(keyIt == null){
            return list;
        }
        while(keyIt.hasNext()){
            String id = keyIt.next();
            JSONObject jsonO = jsonObject.getJSONObject(id);
            String tag = getString(jsonO, "tag");
            double xmin = NumberUtils.toDouble(jsonO.get("xmin").toString());
            double ymin = NumberUtils.toDouble(jsonO.get("ymin").toString());
            double xmax = NumberUtils.toDouble(jsonO.get("xmax").toString());
            double ymax = NumberUtils.toDouble(jsonO.get("ymax").toString());
            double width = xmax - xmin;
            double height = ymax - ymin;

            if(StringUtils.equalsIgnoreCase(tag, "table")){
                com.tiny.web.controller.integration.entity.TableLayoutResult tableLayoutResult = new com.tiny.web.controller.integration.entity.TableLayoutResult();
                tableLayoutResult.setId(id);
                tableLayoutResult.setX(xmin);
                tableLayoutResult.setY(ymin);
                tableLayoutResult.setWidth(width);
                tableLayoutResult.setHeight(height);

                JSONArray cellsArr = (JSONArray) jsonO.get("cells");
                if (cellsArr == null || cellsArr.size() == 0) {
                    continue;
                }
                List<RectangleVO> rectangleVOS = new ArrayList<>();
                for (Object cellObj : cellsArr) {
                    JSONObject cell = (JSONObject) cellObj;
                    RectangleVO rectangleVO = parseRect2(cell);
                    rectangleVOS.add(rectangleVO);
                }
                //TODO
                int fixedCol = 4;
                int fixedRow = rectangleVOS.size() % fixedCol == 0 ? rectangleVOS.size() / fixedCol : rectangleVOS.size() / fixedCol + 1;
                for(int i = 0; i < fixedRow ;i++){
                    int begin = 0;
                    int end = 0;
                    begin = i * fixedCol;
                    end = begin + fixedCol;
                    if(i == fixedRow -1){
                        end = rectangleVOS.size();
                    }
                    List<RectangleVO> rows = new ArrayList<>(rectangleVOS.subList(begin, end));
                    if(i == fixedRow -1 && end - begin != fixedCol ){
                        //TODO to fix miss some col...
                        int miss = fixedCol - (end - begin);
                        for(int j = 0 ;j < miss; j++){
                            RectangleVO rectangleVO = new RectangleVO();
                            rectangleVO.setText("-Miss-");
                            rows.add(rectangleVO);
                        }
                    }
                    tableLayoutResult.getAllList().add(rows);
                }
                ContentResult contentResult = new ContentResult();
                contentResult.setResult(tableLayoutResult);
                list.add(contentResult);
            }else {
                com.tiny.web.controller.integration.entity.GridLayoutResult gridLayoutResult = new com.tiny.web.controller.integration.entity.GridLayoutResult();
                gridLayoutResult.setId(id);
                RectangleVO rectangleVO = parseRect2(jsonO);
                gridLayoutResult.getAllList().add(rectangleVO);

                ContentResult contentResult = new ContentResult();
                contentResult.setResult(gridLayoutResult);
                list.add(contentResult);
            }
        }
        return list;
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

    private static RectangleVO parseRect2(JSONObject cell) {
        RectangleVO rectangleVO = new RectangleVO();
        JSONArray ocrArr = (JSONArray) cell.get("ocr");
        double score = getDouble(cell,"score");
        double xmin = getDouble(cell,"xmin");
        double ymin = getDouble(cell,"ymin");
        double xmax = getDouble(cell,"xmax");
        double ymax = getDouble(cell,"ymax");
        double width = xmax - xmin;
        double height = ymax - ymin;
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

    public static double getDouble(JSONObject jsonObject, String key){
        Object object = jsonObject.get(key);
        if(object == null){
            return 0;
        }
        if(NumberUtils.isNumber(object.toString())){
            return NumberUtils.toDouble(object.toString(), 0);
        }
        return 0;
    }

    public static String getString(JSONObject jsonObject, String key){
        Object object = jsonObject.get(key);
        if(object == null){
            return null;
        }
        return object.toString();
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
        double width = getDouble(ocrObj, "width");
        double height = getDouble(ocrObj,"height");
        double xmin = getDouble(ocrObj,"xmin");
        double ymin = getDouble(ocrObj,"ymin");
        String str = getString(ocrObj,"char");

        charVO.setHeight(height);
        charVO.setWidth(width);
        charVO.setXmin(xmin);
        charVO.setYmin(ymin);
        charVO.setXmax(xmin + width);
        charVO.setYmax(ymin + height);
        charVO.setStr(str == null ? "" : str);
        return charVO;
    }

    public static com.tiny.web.controller.integration.entity.ContentResult mockContentResult(String srcJson) {
        String filePath = "metadata/table-result-2.json";
        String jsonStr = null;
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
            List<String> lines = IOUtils.readLines(inputStream);
            StringBuilder stringBuilder = new StringBuilder();
            for (String line : lines) {
                stringBuilder.append(line);
            }
            jsonStr = stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return parseContentResult(jsonStr);
    }

    public static void main(String[] args) throws IOException {
//        String path = "D:\\data\\code\\github-workspace\\tiny\\app\\web\\home\\src\\test\\resources\\table-result-2.json";
        String path = "D:\\data\\code\\github-workspace\\tiny\\app\\web\\home\\src\\test\\resources\\table-result-2.json";
        String jsonStr = FileUtils.readFileToString(new File(path));
//        Object obj = parseLayoutResult2(jsonStr);
        Object obj = parseContentResult2(jsonStr);
        System.out.println(obj);

    }
}

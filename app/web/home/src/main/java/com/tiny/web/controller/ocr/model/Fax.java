package com.tiny.web.controller.ocr.model;

//import static com.tiny.web.controller.ocr.util.Constants.FAX_SEQ_NUM;
//import static com.tiny.web.controller.ocr.util.Constants.FILE_SEPARATOR;
//import static com.tiny.web.controller.ocr.util.Constants.SENDER_INFO;
//import static com.tiny.web.controller.ocr.util.Constants.TMP_DIR;
//import static org.bytedeco.javacpp.opencv_imgcodecs.imreadmulti;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.bytedeco.javacpp.opencv_core.Mat;
//import org.bytedeco.javacpp.opencv_core.MatVector;
//
//import com.tiny.web.controller.ocr.service.OCRService;
//import com.tiny.web.controller.ocr.util.OCRUtil;
//import com.tiny.web.controller.ocr.util.StrUtil;
//
//import static com.tiny.web.controller.ocr.util.Constants.*;

public class Fax {

//    private String path;
//    private String category;
//    // private List<String> pageList;
//    // private List<Mat> matList;
//    private Map<String, Field> coverFields;
//    private MatVector matVector;
//
//    public MatVector getMatVector() {
//        return matVector;
//    }
//
////    public String getPageFile(int i) {
////        return pageList.get(i);
////    }
//
//    public Mat getPageMat(int i) {
//        if (isMatVectorNull())
//            return null;
//        return matVector.get(i);
//    }
//
//    public String getPath() {
//        return path;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public boolean isMatVectorNull()
//    {
//        return (matVector == null || matVector.isNull() || matVector.size() == 0) ? true : false;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public String toString() {
//        return "Path=" + getPath() + "\r\nCategory=" + getCategory() + StrUtil.mapToString(coverFields);
//    }
//
//    public void split() {
//        matVector = new MatVector();
//        imreadmulti(path, matVector);
//        matVector.deallocate(false);
//    }
//
//    public void destory() {
//        if (!isMatVectorNull())
//            matVector.deallocate();
//    }

//    public void storeToMat()
//    {
//	matList = new ArrayList<Mat>();
//	for (int i = 0; i < matVector.size(); i++)
//	{
//	    matList.add(matVector.get(i));
//	}
//    }

    /*

    public String storeToFile(String dirStr, String suffix) {
        pageList = new ArrayList<String>();

        if (StrUtil.isNullOrEmtry(dirStr))
            dirStr = getDestDir();
        else {
            File dir = new File(dirStr);
            if (!dir.exists())
                dir.mkdirs();
        }

        for (int i = 0; i < matVector.size(); i++) {
            String page = dirStr + i + "." + suffix;
            pageList.add(page);
            OCRUtil.saveMatAsFile(matVector.get(i), page);
        }

        return dirStr;
    }

    public String getDestDir() {
        if (coverFields == null || coverFields.size() == 0)
            getCoverFields();

        String dirStr = TMP_DIR + FILE_SEPARATOR + getCategory() + FILE_SEPARATOR + coverFields.get(SENDER_INFO).getValue()
                + FILE_SEPARATOR + coverFields.get(FAX_SEQ_NUM).getValue() + FILE_SEPARATOR;
        File dir = new File(dirStr);
        if (dir.isDirectory() && !dir.exists())
            dir.mkdirs();
        return dirStr;
    }

    */
}

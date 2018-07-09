package com.tiny.web.controller.ocr.util;

public class Constants
{
    public static final String TIFF = "tiff";
    public static final String TIFF_SUF = ".tiff";

    public static final String TMP_DIR = "tmp";
    public static final String FILE_SEPARATOR = "/";

    public static final int GREY_THRESHHOLD = 10;
    public static final int HORIZONTAL_THRESHHOLD = 10;
    public static final int VERTICAL_THRESHHOLD = 3;

    public enum RegionType
    {
        Null, Page, Template, Signature, Detect, Field;
    }

    public enum PageType
    {
        Null, Cover, Ingore, Single, Multi;
    }

    public enum Catogory
    {
        Cash, Securities, Derivatives;
    }

    public enum DetectPolicy
    {
        One, Save, H, V, H_V, V_H;
    }

    public static final String SENDER_INFO = "Sender";
    public static final String FAX_SEQ_NUM = "Fax sequence number";

    public static final String[] OCR_MAP_SEP = {"[+]", "[%]"};

    public static final String OCR_TEMPLATE = "ocr-template";

}

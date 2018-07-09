package com.tiny.web.controller.ocr.util;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
//import org.bytedeco.javacpp.opencv_core.Mat;

public class FileUtil
{
    private final static Logger logger = Logger.getLogger(FileUtil.class);

    public static String concatPath(String fileName, String dirToAdd, String nameToAdd)
    {
	if (StringUtils.isEmpty(fileName))
	    return "null";

	String dir = "";
	String suffix = "";

	int index1 = fileName.lastIndexOf('.');
	if (index1 > -1)
	{
	    suffix = fileName.substring(index1, fileName.length());
	    fileName = fileName.substring(0, index1);
	}

	fileName = fileName + nameToAdd + suffix;

	int index2 = fileName.lastIndexOf('/');
	if (index2 > -1)
	{
	    dir = fileName.substring(0, index2+1);
	    fileName = fileName.substring(index2+1, fileName.length());
	}

	return dir + dirToAdd + fileName;
    }

    public void testConcatName()
    {
	System.out.println(concatPath("C:/Users/e521901/85_Study/OCR/123.tiff", "output/", "_target"));
    }

//    public static void writeToFile(Mat mat, String text, String pagePath)
//    {
//	try
//	{
//	    String output = concatPath(pagePath, "output/", ("_" + System.currentTimeMillis()));
//	    FileUtils.write(new File(output.replace(".tiff", ".txt")), text);
//	    OCRUtil.saveMat(mat, output);
//	}
//	catch (Exception e)
//	{
//	    logger.error(e);
//	}
//    }

    public static String getFileName(String fileName)
	{
		int index = fileName.lastIndexOf('/');
		if (index > -1)
		{
			return fileName = fileName.substring(index+1, fileName.length());
		}
		return fileName;
	}

	public static void main(String[] args)
	{
		System.out.println(getFileName("123/aaa.tiff"));
	}
}

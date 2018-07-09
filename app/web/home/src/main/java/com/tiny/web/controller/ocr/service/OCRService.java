//package com.tiny.web.controller.ocr.service;
//
//import static com.tiny.web.controller.ocr.util.Constants.GREY_THRESHHOLD;
////import static org.bytedeco.javacpp.opencv_core.BORDER_DEFAULT;
////import static org.bytedeco.javacpp.opencv_core.CV_16S;
////import static org.bytedeco.javacpp.opencv_core.CV_8UC1;
////import static org.bytedeco.javacpp.opencv_core.addWeighted;
////import static org.bytedeco.javacpp.opencv_core.convertScaleAbs;
////import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
////import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;
////import static org.bytedeco.javacpp.opencv_imgproc.CV_THRESH_BINARY;
////import static org.bytedeco.javacpp.opencv_imgproc.CV_THRESH_OTSU;
////import static org.bytedeco.javacpp.opencv_imgproc.Sobel;
////import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
////import static org.bytedeco.javacpp.opencv_imgproc.erode;
////import static org.bytedeco.javacpp.opencv_imgproc.rectangle;
////import static org.bytedeco.javacpp.opencv_imgproc.threshold;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.tiny.web.controller.ocr.model.Detect;
//import com.tiny.web.controller.ocr.model.Field;
//import com.tiny.web.controller.ocr.util.Constants;
//import com.tiny.web.controller.ocr.util.OCRUtil;
//import com.tiny.web.controller.ocr.util.StrUtil;
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//import org.bytedeco.javacpp.lept.PIX;
//import org.bytedeco.javacpp.opencv_core.Mat;
//import org.bytedeco.javacpp.opencv_core.Rect;
//import org.bytedeco.javacpp.opencv_core.Scalar;
//import org.bytedeco.javacpp.indexer.UByteIndexer;
//
//
//public class OCRService
//{
//	private final static Logger logger = Logger.getLogger(OCRService.class);
//
//	private Mat page;
//
//	private Mat grad;
//
//	private TesseractService tesseractService;
//
//	public TesseractService getTesseractService() {
//		return tesseractService;
//	}
//
//	public void setTesseractService(TesseractService tesseractService) {
//		this.tesseractService = tesseractService;
//	}
//
//	public Map<String, Field> recognizeRegion(Detect detect)
//	{
//		Map<String, Field> fieldMap = new LinkedHashMap<String, Field>(10);
//		String text = tesseractService.recognize(page, detect.toRect());
//		String[] strs = StringUtils.split(text, '\n');
//
//		int index = 1;
//		for (String s : strs) {
//			Field field = null;
//			if (index == 1)
//				field = new Field(detect.getName(), s);
//			else
//				field = new Field(detect.getName() + "_" + index, s);
//			index++;
//			fieldMap.put(field.getLabel(), field);
//		}
//
//		System.out.println(StrUtil.mapToString(fieldMap));
//
//		//rectangle(page, rect, Scalar.GREEN);
//		return fieldMap;
//	}
//
//	public Map<String, Field> recognizeRegion2(Rect rect, Detect detect)
//	{
//		Map<String, Field> fieldMap = new LinkedHashMap<String, Field>(10);
//		String text = tesseractService.recognize(page, rect);
//		String[] strs = StringUtils.split(text, '\n');
//
//		int index = 1;
//
//		for (String s : strs)
//		{
//			Field f = StrUtil.getField(s);
//			if (f == null)
//				continue;
//			if (StrUtil.isNullOrEmtry(f.getLabel())) {
//				if (index == 1)
//					fieldMap.put(detect.getName(), f);
//				else
//					fieldMap.put(detect.getName() + "_" + index, f);
//				index++;
//			} else
//				fieldMap.put(f.getLabel(), f);
//		}
//
//		System.out.println(StrUtil.mapToString(fieldMap));
//
//		//ts.destory();
//		//rectangle(page, rect, Scalar.GREEN);
//		return fieldMap;
//	}
//
////    public void saveRegion(String pagePath, String detectName, Rect detectRegion)
////    {
////	Mat mat = new Mat(page, detectRegion);
////	String dir = null;
////	int index = pagePath.lastIndexOf('/');
////	if (index > -1)
////	    dir = pagePath.substring(0, index+1);
////	String dest = dir + detectName.replace('_', '/') + Constants.TIFF_SUF;
////	OCRUtil.saveMatAsFile(mat, dest);
////    }
//
//	public Map<String, Field> recognizeCover(Detect detect)
//	{
//		Map<String, Field> fieldMap = new LinkedHashMap<String, Field>();
//		List<Rect> areas = detectV(grad, detect.toRect(), detect.getVerticalThreshold());
//		int index = 1;
//
//		for (Rect r : areas) {
//			String text = tesseractService.recognize(page, r);
//			text = StringUtils.remove(text, '\n');
//			Field f = StrUtil.getField(text);
//			if (f == null)
//				continue;
//			if (StrUtil.isNullOrEmtry(f.getLabel())) {
//				fieldMap.put("Cover_" + index, f);
//				index++;
//			}
//			else
//				fieldMap.put(f.getLabel(), f);
//			// rectangle(mat, r, Scalar.BLUE);
//		}
//		logger.info(StrUtil.mapToString(fieldMap));
//		return fieldMap;
//	}
//
//	public Map<String, Field> recognizeRegion_Once(Detect detect)
//	{
//		Map<String, Field> fieldMap = new LinkedHashMap<String, Field>();
//
//		List<Rect> areas = null;
//		if (detect.getPolicy() == Constants.DetectPolicy.H)
//			areas = detectH(grad, detect.toRect(), detect.getHorizontalThreshold());
//		else if (detect.getPolicy() == Constants.DetectPolicy.V)
//			areas = detectV(grad, detect.toRect(), detect.getVerticalThreshold());
//
//		int index = 1;
//
//		for (Rect r : areas)
//		{
//			//Mat mat = new Mat(page, r);
//			String text = tesseractService.recognize(page, r);
//			String[] strs = StringUtils.split(text, '\n');
//			for (String s : strs)
//			{
//				Field f = StrUtil.getField(s);
//				if (f == null)
//					continue;
//				f.setLocation(r.x(), r.y(), r.width(), r.height());
//				if (StrUtil.isNullOrEmtry(f.getLabel()))
//				{
//					if (index == 1)
//						fieldMap.put(detect.getName(), f);
//					else
//						fieldMap.put(detect.getName() + "_" + index, f);
//					index++;
//				}
//				else
//					fieldMap.put(f.getLabel(), f);
//			}
//			//OCRUtil.rectangleRegion(page, r, Scalar.BLUE);
//		}
//		System.out.println(StrUtil.mapToString(fieldMap));
//		return fieldMap;
//	}
//
//	public Map<String, Field> recognizeRegion_Twice(Detect detect)
//	{
//		Map<String, Field> fieldMap = new LinkedHashMap<String, Field>();
//		//rectangle(page, detectRegion, Scalar.GREEN);
//
//		List<Rect> second = new ArrayList<Rect>();
//
//		if (detect.getPolicy() == Constants.DetectPolicy.H_V)
//		{
//			List<Rect> areas = detectH(grad, detect.toRect(), detect.getHorizontalThreshold());
//			for (Rect r : areas)
//			{
//				//rectangle(page, r, Scalar.RED);
//				second.addAll(detectV(grad, r, detect.getVerticalThreshold()));
//			}
//		}
//		else if (detect.getPolicy() == Constants.DetectPolicy.V_H)
//		{
//			List<Rect> areas = detectV(grad, detect.toRect(), detect.getVerticalThreshold());
//			for (Rect r : areas)
//			{
//				//rectangle(page, r, Scalar.RED);
//				second.addAll(detectH(grad, r, detect.getHorizontalThreshold()));
//			}
//		}
//
////		TesseractService ts = new TesseractService();
//		//OCRUtil.show(page);
//		for (Rect r : second)
//		{
//			//logger.info(String.format("Rect r = %d, %d, %d, %d", r.x(), r.y(), r.width(), r.height()));
//			String text = tesseractService.recognize(page, r);
//			text = StringUtils.remove(text, '\n');
//			Field f = StrUtil.splitField(text, r);
//			if (f != null)
//				fieldMap.put(f.getLabel(), f);
//			//rectangle(page, r, Scalar.BLUE);
//		}
//
//		//OCRUtil.show(page);
//
//		logger.info(StrUtil.mapToString(fieldMap));
//		//ts.destory();
//
//		return fieldMap;
//	}
//
//	private static List<Rect> detectH(Mat mat, Rect rect, int horizontalThreshold)
//	{
//
//		//OCRUtil.show(mat);
//
//		int threshhold = GREY_THRESHHOLD;
//
//		List<Rect> areas = new ArrayList<Rect>();
//
//		if (rect.width() < horizontalThreshold || rect.height() < horizontalThreshold)
//		{
//			// areas.add(rect);
//			return areas;
//		}
//
//		UByteIndexer idx = mat.createIndexer();
//		int hh[] = new int[rect.width()];
//		for (int i = rect.x(); i < rect.x() + rect.width(); i++)
//		{
//			for (int j = rect.y(); j < rect.y() + rect.height(); j++)
//			{
//				try
//				{
//					hh[i-rect.x()] += (idx.get(j, i)&0x1f);
//				}
//				catch (Exception e)
//				{
//					System.out.println(String.format("j=%d,i=%d", j, i));
//					// throw e;
//				}
//			}
//		}
//
//		int left = -1;
//		int right = -1;
//		for (int i = 0; i < hh.length;)
//		{
//			if (hh[i] >= threshhold)
//			{
//				if (left == -1)
//				{
//					left = i;
//					right = i;
//				}
//				else
//				{
//					right = i;
//				}
//			}
//			else
//			{
//				if (left != -1 && right >= left)
//				{
//					Rect r = new Rect(rect.x() + left, rect.y(), right - left + 1, rect.height());
//					//logger.info(String.format("Rect r = %d, %d, %d, %d", r.x(), r.y(), r.width(), r.height()));
//					areas.add(r);
//					left = -1;
//					right = -1;
//				}
//			}
//			i++;
//		}
//		if (left != -1 && right >= left)
//		{
//			areas.add(new Rect(rect.x() + left, rect.y(), right - left + 1, rect.height()));
//		}
//
//		if (areas.size() <= 1)
//		{
//			return areas;
//		}
//
//		List<Rect> result = new ArrayList<Rect>();
//		Rect temp = areas.get(0);
//
//
//		for (int i = 1; i < areas.size(); i++)
//		{
//			Rect r = areas.get(i);
//			//logger.info(String.format("Temp r = %d, %d, %d, %d", temp.x(), temp.y(), temp.width(), temp.height()));
//			//logger.info(String.format("Rect r = %d, %d, %d, %d", r.x(), r.y(), r.width(), r.height()));
//
//			if ((temp.x() + temp.width() + horizontalThreshold) >= r.x()
//				// || ( r.width() <= horizontalThreshold && r.x() <= 1100)
//					)
//			{
//				temp.width(r.x() - temp.x() + r.width());
//			}
//			else
//			{
//				if (temp.width() > horizontalThreshold)
//				{
//					temp.x(temp.x() - 1);
//					temp.width(temp.width() + 2);
//					result.add(temp);
//					//logger.info(String.format("AAA r = %d, %d, %d, %d", temp.x(), temp.y(), temp.width(), temp.height()));
//				}
//				temp = r;
//			}
//
//		}
//		temp.x(temp.x() - 1);
//		temp.width(temp.width() + 2);
//		result.add(temp);
//		return result;
//	}
//
//	private static List<Rect> detectV(Mat mat, Rect rect, int verticalThreshold)
//	{
//		int threshhold = GREY_THRESHHOLD;
//
//		List<Rect> areas = new ArrayList<Rect>();
//		//logger.info("detectV: " + rect.toString());
//		if (rect.width() < verticalThreshold || rect.height() < verticalThreshold)
//		{
//			areas.add(rect);
//			return areas;
//		}
//		UByteIndexer idx = mat.createIndexer();
//		int vv[] = new int[rect.height()];
//		for (int j = rect.y(); j < rect.y() + rect.height(); j++)
//		{
//			for (int i = rect.x(); i < rect.x() + rect.width(); i++)
//			{
//				vv[j - rect.y()] += (idx.get(j, i) & 0x1f);
//			}
//		}
//		int top = -1;
//		int bottom = -1;
//		for (int i = 0; i < vv.length;)
//		{
//			if (vv[i] >= threshhold)
//			{
//				if (top == -1)
//				{
//					top = i;
//					bottom = i;
//				}
//				else
//				{
//					bottom = i;
//				}
//			}
//			else
//			{
//				if (top != -1 && bottom >= top)
//				{
//					areas.add(new Rect(rect.x(), rect.y() + top, rect.width(), bottom - top + 1));
//					top = -1;
//					bottom = -1;
//				}
//			}
//			i++;
//		}
//		if (top != -1 && bottom >= top)
//		{
//			areas.add(new Rect(rect.x(), rect.y() + top, rect.width(), bottom - top + 1));
//		}
//
//		if (areas.size() <= 1)
//		{
//			return areas;
//		}
//
//		List<Rect> result = new ArrayList<Rect>();
//		Rect temp = areas.get(0);
//		for (int i = 1; i < areas.size(); i++)
//		{
//			Rect r = areas.get(i);
//			if ((temp.y() + temp.height() + verticalThreshold) >= r.y() || r.height() <= verticalThreshold)
//			{
//				temp.height(r.y() - temp.y() + r.height());
//			}
//			else
//			{
//				temp.y(temp.y() - 1);
//				temp.height(temp.height() + 2);
//				result.add(temp);
//				temp = r;
//			}
//		}
//
//		temp.y(temp.y() - 1);
//		temp.height(temp.height() + 2);
//		result.add(temp);
//		return result;
//	}
//
//	public void setGrad(Mat grad) {
//		this.grad = grad;
//	}
//
//	public Mat getPage()
//	{
//		return page;
//	}
//
//	public void setPage(Mat page)
//	{
//		this.page = page;
//	}
//
//	public void destory()
//	{
//		tesseractService.destory();
//	}
//}

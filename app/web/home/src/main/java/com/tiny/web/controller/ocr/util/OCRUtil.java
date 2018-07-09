//package com.tiny.web.controller.ocr.util;
//
//import com.google.common.io.Files;
//import org.apache.commons.io.IOUtils;
//import org.bytedeco.javacpp.opencv_core;
//import org.bytedeco.javacpp.opencv_core.*;
//import org.bytedeco.javacv.Java2DFrameConverter;
//import org.bytedeco.javacv.OpenCVFrameConverter;
//import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;
//import org.bytedeco.javacv.OpenCVFrameConverter.ToMat;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.awt.image.RenderedImage;
//import java.io.*;
//
//import static org.bytedeco.javacpp.opencv_core.*;
//import static org.bytedeco.javacpp.opencv_highgui.cvWaitKey;
//import static org.bytedeco.javacpp.opencv_highgui.imshow;
//import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
//import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
//import static org.bytedeco.javacpp.opencv_imgproc.*;
//
//
//public class OCRUtil
//{
//    public static final String IMAGE_FORMAT = "tiff";
//
//
////    public static IplImage bytesToImage(byte[] bytes, int iscolor) throws Exception
////    {
////	try (BytePointer bp = new BytePointer();)
////	{
////	    bp.put(bytes, 0, bytes.length);
////	    return cvLoadImage(bp, iscolor);
////	}
////    }
////
////    public static Mat bytesToMat(byte[] bytes) throws Exception
////    {
////	try (BytePointer bp = new BytePointer();)
////	{
////	    bp.put(bytes, 0, bytes.length);
////	    return imread(bp);
////	}
////    }
//
//    public static String matToString(Mat mat)
//    {
//		return mat.size().width() + ", " + mat.size().height() + ", " + mat.channels() + ", " + mat.depth();
//    }
//
//    public static byte[] fileToBytes(String path) throws Exception
//    {
//	File file = new File(path);
//	try (InputStream is = new FileInputStream(file);)
//	{
//	    return IOUtils.toByteArray(is);
//	}
//    }
//
//    public static byte[] imageToBytes(IplImage iplimage) throws Exception
//    {
//	return iplimage.asByteBuffer().array();
//    }
//
////    public static IplImage bytesToImage(byte[] bytes) throws Exception
////    {
////
////
////	cvLoadImage()
////    }
//
//    public static IplImage bufToIpl(BufferedImage bufImage)
//    {
//	ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
//	Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
//	return converter.convertToIplImage(java2dConverter.convert(bufImage));
//    }
//
//    public static Mat bufToMat(BufferedImage bufImage)
//    {
//	ToMat converter = new OpenCVFrameConverter.ToMat();
//	Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
//	return converter.convertToMat(java2dConverter.convert(bufImage));
//    }
//
//    public static IplImage matToIpl(Mat mat)
//    {
//	OpenCVFrameConverter.ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();
//	OpenCVFrameConverter.ToMat matConverter = new OpenCVFrameConverter.ToMat();
//	IplImage img = iplConverter.convert(matConverter.convert(mat));
//	// IplImage result = img.clone();
//	// img.release();
//	// return result;
//	return img;
//    }
//
//    public static BufferedImage matToBuf(Mat mat)
//    {
//	ToMat converter = new OpenCVFrameConverter.ToMat();
//	Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
//	return java2dConverter.getBufferedImage(converter.convert(mat));
//    }
//
//    public static BufferedImage iplToBuf(IplImage iplImage)
//    {
//	ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
//	Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
//	return java2dConverter.getBufferedImage(converter.convert(iplImage));
//    }
//
////    public static BufferedImage renderedToBuffered(RenderedImage ri)
////    {
//////	ImageDecoder dec = ImageCodec.createImageDecoder("PNM", new File(input), null);
////	return new RenderedImageAdapter(ri).getAsBufferedImage();
////    }
////
//    public static byte[] renderedToBytes(RenderedImage img) throws IOException
//    {
//	try (ByteArrayOutputStream bytes = new ByteArrayOutputStream();)
//	{
//	    ImageIO.write(img, IMAGE_FORMAT, bytes);
//	    return bytes.toByteArray();
//	}
//    }
//
//
//
//    public static byte[] RenderedToBytes(RenderedImage img) throws IOException
//    {
//	try (ByteArrayOutputStream bytes = new ByteArrayOutputStream();)
//	{
//	    ImageIO.write(img, IMAGE_FORMAT, bytes);
//	    return bytes.toByteArray();
//	}
//    }
//
//    public static BufferedImage fileToBuffered(String path) throws IOException
//    {
//	BufferedImage bi = ImageIO.read(new File(path));
//	//ImageIO.write(bi, "tiff", new File("C:\\Users\\e521901\\85_Study\\OCR\\Fax\\match\\test.tiff"));
//	return bi;
//    }
//
//
//    public static byte[] bufferedToBytes(BufferedImage img) throws IOException
//    {
//	try (ByteArrayOutputStream baos = new ByteArrayOutputStream();)
//	{
//	    ImageIO.write(img, IMAGE_FORMAT, baos);
//	    return baos.toByteArray();
//	}
//    }
//
//    public static BufferedImage bytesToBuffered(byte[] bytes) throws IOException
//    {
//	try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);)
//	{
//	    return ImageIO.read(bais);
//	}
//    }
//
//    public static void saveMat(Mat mat, String path) throws IOException {
//    	new File(path).getParentFile().mkdirs();
//    	imwrite(path, mat);
//    }
//
//    public static void show(Mat mat)
//    {
//	imshow("test", mat);
//	cvWaitKey(0);
//    }
//
//	public static Mat convert(Mat src)
//	{
////	Mat target = new Mat();
////	GaussianBlur(page, target, new Size(3, 3), 0d); // gaussian blur
//
//		Mat grey = new Mat();
//		cvtColor(src, grey, CV_BGR2GRAY);
//		threshold(grey, grey, 40, 255, CV_THRESH_BINARY | CV_THRESH_OTSU);
//		Mat sobelX = new Mat();
//		Sobel(grey, sobelX, CV_16S, 1, 0, 3, 1, 0, BORDER_DEFAULT);
//		Mat abs_grad_x = new Mat();
//		convertScaleAbs(sobelX, abs_grad_x);
//		Mat sobely = new Mat();
//		Sobel(grey, sobely, CV_16S, 0, 1, 3, 1, 0, BORDER_DEFAULT);
//		Mat abs_grad_y = new Mat();
//		convertScaleAbs(sobely, abs_grad_y);
//		Mat grad = new Mat();
//		addWeighted(abs_grad_x, 0.5, abs_grad_y, 0.5, 0, grad);
//		return grad;
//	}
//
//	public static void rectangle_Mat(Mat mat, Rect rect, Scalar color)
//	{
//		rectangle(mat, rect, color);
//	}
//
//
//	public static Mat erode_Mat(Mat src)
//	{
//		Mat mat = new Mat();
//		erode(src, mat, new Mat(2, 2, CV_8UC1));
//		return mat;
//	}
//
//	// 0 = CV_LOAD_IMAGE_GRAYSCALE || 1 = CV_LOAD_IMAGE_COLOR
//	public static IplImage toIplImage(String path)
//	{
//		return cvLoadImage(path, 0);
//	}
//
//	public static IplImage toIplImage(opencv_core.Mat mat)
//	{
//		Mat grey = new Mat();
//		cvtColor(mat, grey, CV_BGR2GRAY);
//		return OCRUtil.matToIpl(grey);
//	}
//}

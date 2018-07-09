package com.tiny.web.controller.ocr;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.TIFFEncodeParam;
import com.tiny.common.exception.InitializationException;
import com.tiny.core.model.FileInfo;
import com.tiny.core.model.SubFile;
import com.tiny.web.controller.BaseJsonResult;
import com.tiny.web.controller.http.response.TempScanResp;
import com.tiny.web.controller.json.OcrCover;
import com.tiny.web.controller.json.OcrTemp;
import com.tiny.web.controller.ocr.constant.TempConstant;
import com.tiny.web.controller.ocr.model.Detect;
import com.tiny.web.controller.ocr.model.Fax;
import com.tiny.web.controller.ocr.model.Field;
import com.tiny.web.controller.ocr.model.Template;
import com.tiny.web.controller.ocr.service.DetectService;
//import com.tiny.web.controller.ocr.service.OCRService;
//import com.tiny.web.controller.ocr.service.TesseractService;
import com.tiny.web.controller.ocr.util.Constants;
import com.tiny.web.controller.ocr.util.FieldUtil;
import com.tiny.web.controller.ocr.util.FileUtil;
//import com.tiny.web.controller.ocr.util.OCRUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
//import org.bytedeco.javacpp.opencv_core;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Scope("session")
@Controller
public class DemoController extends OCRController {

    /**
     *
     */
    private static final Logger logger = Logger.getLogger(DemoController.class);

    @Autowired
    private ServletContext context;
    /**
     * save the upload file
     */
    private String storePath;

    private String storeTempPath;

    private String resourceBasePath;

    private String tempPath;

    private Fax fax;

//    private TesseractService tesseractService;

    interface Constant {
        String imageType = "jpg";
    }

    @PostConstruct
    public void init() {

        logger.info("init: " + this + " Thread: " + Thread.currentThread().getId());
        storePath = context.getRealPath("/WEB-INF/resources/uploaded/ocr/");
        storeTempPath = context.getRealPath("/WEB-INF/resources/temp/ocr/");
        if (StringUtils.isBlank(storePath)) {
            throw new InitializationException("OCR_UPLOAD_SERVER not config...");
        }
        String contextPath = context.getContextPath();
        resourceBasePath = contextPath + "/resources/uploaded/ocr/";
        tempPath = contextPath + "/resources/temp/ocr/";
        logger.info("DemoController init end... storePath:" + storePath);
        logger.info("DemoController init end... resourceBasePath:" + resourceBasePath);

        fax = new Fax();
//        tesseractService = new TesseractService();


    }

    @PreDestroy
    public void destory() {
//        if (fax != null)
//            fax.destory();
//        if (tesseractService != null)
//            tesseractService.destory();
    }

    @RequestMapping(path = {UrlCenter.OCR.HOME, UrlCenter.OCR.DEMO})
    public String index(ModelMap model) {
        model.addAttribute("view_path", UrlCenter.OCR.DEMO);
        model.addAttribute("scan_temp_list", TempConstant.TEMP_LIST);
        model.addAttribute("scan_cover", TempConstant.cover);
        return View.DEMO;
    }


    @RequestMapping(path = {UrlCenter.OCR.FIND_IMAGE}, method = RequestMethod.GET)
    public void findImage(@RequestParam String fileName, HttpServletRequest request, HttpServletResponse response) {
    }


    @RequestMapping(path = {UrlCenter.OCR.MULTI_UPLOAD}, headers = ("content-type=multipart/*"), method = RequestMethod.POST)
    @ResponseBody
    public Object multiFileUpload(ModelMap model, @RequestParam("file") MultipartFile file, @RequestParam("checkType") String checkType) {
        BaseJsonResult baseJsonResult = new BaseJsonResult();
        if (file == null) {
            return baseJsonResult.marketFail("no file upload...");
        }
        try {
            baseJsonResult.markeSuccess("upload succ", saveFile(file, checkType));
//            if (fax != null)
//                fax.destory();
        } catch (Exception e) {
            logger.error("multiFileUpload error -- " + e.getMessage(), e);
            baseJsonResult.marketFail("file upload fail--" + e.getMessage());
        }
        return baseJsonResult;
    }

    @RequestMapping(path = {UrlCenter.OCR.COVER_SCAN}, method = RequestMethod.POST)
    @ResponseBody
    public Object coverScan(@RequestParam("fileName") String fileName) {
        BaseJsonResult baseJsonResult = new BaseJsonResult();
        if (StringUtils.isBlank(fileName)) {
            return baseJsonResult.marketFail("file name is blank.");
        }
        try {
            baseJsonResult.markeSuccess("scan succ", ocrCoverScan(fileName));
        } catch (Exception e) {
            logger.error("coverScan error -- " + e.getMessage(), e);
            baseJsonResult.marketFail("cover scan fail--" + e.getMessage());
        }
        return baseJsonResult;
    }


    @RequestMapping(path = {UrlCenter.OCR.TEMP_SCAN}, method = RequestMethod.POST)
    @ResponseBody
    public Object tempScan(@RequestParam("fileName") String fileName, @RequestParam("page") String page, @RequestParam("tempName") String tempName) {
        BaseJsonResult baseJsonResult = new BaseJsonResult();
        if (StringUtils.isBlank(fileName)) {
            return baseJsonResult.marketFail("file name is blank.");
        }
        try {
            baseJsonResult.markeSuccess("scan succ", ocrTempScan(fileName, page, tempName));
        } catch (Exception e) {
            logger.error("tempScan error -- " + e.getMessage(), e);
            baseJsonResult.marketFail("template scan fail--" + e.getMessage());
        }
        return baseJsonResult;
    }

    @RequestMapping(path = {UrlCenter.OCR.TEMP_SUB}, method = RequestMethod.POST)
    @ResponseBody
    public Object tempSub(@RequestParam("fileName") String fileName, @RequestBody OcrTemp ocrTemp) {
        BaseJsonResult baseJsonResult = new BaseJsonResult();
        if (StringUtils.isBlank(fileName)) {
            return baseJsonResult.marketFail("file name is blank.");
        }
        if (StringUtils.isBlank(ocrTemp.getTempKey())) {
            return baseJsonResult.marketFail("template name is blank.");
        }
        try {
            baseJsonResult.markeSuccess("submit succ", true);
        } catch (Exception e) {
            logger.error("tempSub error -- " + e.getMessage(), e);
            baseJsonResult.marketFail("template submit fail--" + e.getMessage());
        }
        return baseJsonResult;
    }


    /**
     * @param file
     * @param checkType
     * @return
     */
    private FileInfo saveFile(MultipartFile file, String checkType) {
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isNotBlank(checkType) && !StringUtils.endsWith(originalFilename, checkType)) {
            Assert.isTrue(false, "File type no validated...");
        }

        File dir = new File(storePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String serverFilePath = dir.getAbsolutePath() + File.separator + file.getOriginalFilename();
        File serverFile = new File(serverFilePath);
        InputStream is = null;
        BufferedOutputStream bufferedOutputStream = null;
        byte[] buffer = new byte[1024];
        try {
            is = file.getInputStream();
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(serverFile));
            int readBytes = 0;
            while ((readBytes = is.read(buffer, 0, 1024)) != -1) {
                bufferedOutputStream.write(buffer, 0, readBytes);
            }
            bufferedOutputStream.flush();
        } catch (IOException e) {
            logger.error("saveFile error", e);
            throw new RuntimeException("save file error-" + e.getLocalizedMessage());
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    logger.error("saveFile error -- close bufferedOutputStream", e);
                }
            }

            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("saveFile error -- close is", e);
                }
            }
        }
        FileInfo fileInfo = new FileInfo(resourceBasePath + originalFilename, file.getSize());
        fileInfo.setType(file.getContentType());
        List<SubFile> subFiles = splitTiff2Img2(serverFilePath, file);
        if (CollectionUtils.isNotEmpty(subFiles)) {
            fileInfo.setSubFileList(subFiles);
        }
        return fileInfo;
    }

    /**
     * e
     *
     * @param serverFile
     * @param file
     * @return
     * @throws IOException
     */
    private List<SubFile> splitTiff2Img(String serverFile, MultipartFile file) {
        String originalFile = file.getOriginalFilename();
        if (!StringUtils.endsWith(originalFile, ".tiff") && !StringUtils.endsWith(originalFile, ".tif")) {
            return null;
        }
        String tiffSrc = originalFile.substring(0, originalFile.indexOf("."));
        List<SubFile> subFiles = new ArrayList<SubFile>();
        ImageInputStream imageInputStream = null;
        try {
            imageInputStream = ImageIO.createImageInputStream(new FileInputStream(serverFile));
//            Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("TIFF");
            if (!readers.hasNext()) {
                imageInputStream = ImageIO.createImageInputStream(file.getInputStream());
                readers = ImageIO.getImageReaders(imageInputStream);
                if (!readers.hasNext()) {
                    logger.warn("no readers here...");
                    return subFiles;
                }
            }
            ImageReader imageReader = readers.next();
            imageReader.setInput(imageInputStream);
            int frameCount = imageReader.getNumImages(true);
            File dir = new File(storeTempPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            for (int i = 0; i < frameCount; ++i) {
                // save the individual TIFF frame as separate image with JPEG compression
                String tempName = dir.getAbsolutePath() + File.separator + tiffSrc + "-" + i + "." + Constant.imageType;
                String retTempName = tempPath + tiffSrc + "-" + i + "." + Constant.imageType;
                ImageIO.write(imageReader.read(i), Constant.imageType, new File(tempName));
                SubFile subFile = new SubFile();
                subFile.setName(retTempName);
                subFiles.add(subFile);
            }
        } catch (IOException e) {
            logger.error("split error", e);
            throw new RuntimeException("split error -- " + e.getLocalizedMessage());
        } finally {
            if (imageInputStream != null) {
                try {
                    imageInputStream.close();
                } catch (IOException e) {
                    logger.error("split error--close", e);
                }
            }
        }

        return subFiles;
    }


    /**
     * via JAI
     *
     * @param serverFile
     * @param file
     * @return
     */
    private List<SubFile> splitTiff2Img2(String serverFile, MultipartFile file) {
        String originalFile = file.getOriginalFilename();
        if (!StringUtils.endsWith(originalFile, ".tiff") && !StringUtils.endsWith(originalFile, ".tif")) {
            return null;
        }
        String tiffSrc = originalFile.substring(0, originalFile.indexOf("."));
        List<SubFile> subFiles = new ArrayList<SubFile>();
        FileSeekableStream fileSeekableStream = null;
        try {
            fileSeekableStream = new FileSeekableStream(serverFile);

            ImageDecoder imageDecoder = ImageCodec.createImageDecoder("tiff", fileSeekableStream, null);
            int count = imageDecoder.getNumPages();
            if (count == 0) {
                logger.warn("no page need to convert ");
                return subFiles;
            }

            File dir = new File(storeTempPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            TIFFEncodeParam param = new TIFFEncodeParam();
            param.setCompression(TIFFEncodeParam.COMPRESSION_GROUP4);
            param.setLittleEndian(false); // Intel
            for (int i = 0; i < count; ++i) {
                // save the individual TIFF frame as separate image with JPEG compression

                String tempName = dir.getAbsolutePath() + File.separator + tiffSrc + "-" + i + "." + Constant.imageType;
                String retTempName = tempPath + tiffSrc + "-" + i + "." + Constant.imageType;
                RenderedImage page = imageDecoder.decodeAsRenderedImage(i);
                ParameterBlock parameterBlock = new ParameterBlock();
                /* add source of page */
                parameterBlock.addSource(page);
                /* add o/p file path */
                parameterBlock.add(tempName);
                /* add o/p file type */
                parameterBlock.add(Constant.imageType);
                 /* create output image using JAI filestore */
                RenderedOp renderedOp = JAI.create("filestore",
                        parameterBlock);
                renderedOp.dispose();
                SubFile subFile = new SubFile();
                subFile.setName(retTempName);
                subFiles.add(subFile);
            }
        } catch (IOException e) {
            logger.error("split error", e);
            throw new RuntimeException("split error -- " + e.getLocalizedMessage());
        } finally {
            if (fileSeekableStream != null) {
                try {
                    fileSeekableStream.close();
                } catch (IOException e) {
                    logger.error("split error--close", e);
                }
            }
        }
        return subFiles;
    }


    /**
     * @param fileName
     * @return
     */
    private OcrCover ocrCoverScan(String fileName) {
        logger.info("ocrCoverScan: " + this + " Thread: " + Thread.currentThread().getId());
//        fax = getFax(fileName);
//        opencv_core.Mat coverMat = fax.getPageMat(0);
//        if (coverMat == null)
//            throw new RuntimeException("Unable to load cover page");
//
//        opencv_core.Mat erodeMat = OCRUtil.erode_Mat(coverMat);
//
//        Detect detect = new Detect("Cover");
//        detect.setPolicy(Constants.DetectPolicy.V);
//        detect.setLocation(0, 0, coverMat.size().width(), coverMat.size().height());
//        detect.setVerticalThreshold(5);
//
//        OCRService ocrService = new OCRService();
//        ocrService.setTesseractService(tesseractService);
//        ocrService.setPage(erodeMat);
//        ocrService.setGrad(OCRUtil.convert(erodeMat));

        OcrCover cover = new OcrCover();
//        FieldUtil.setMappingField(cover, ocrService.recognizeCover(detect));

        // cover.setReceivedDateTime(FieldUtil.get(coverFields, "Date received") + " " + FieldUtil.get(coverFields, "Last page received at"));
        return cover;
    }

    /**
     * @param fileName
     * @return
     */
//    private OcrTemp scanTemp(String fileName, opencv_core.Mat pageMat, String tempName) {
//        logger.info("scanTemp: " + this + " Thread: " + Thread.currentThread().getId());
//        if (StringUtils.isEmpty(tempName))
//            return null;
//
//        tempName = tempName.toLowerCase();
//
//        Template template = new Template();
//        template.setName(tempName);
//        template.setPath(context.getRealPath("/WEB-INF/resources/") + "/template/" + tempName + Constants.TIFF_SUF);
//        template.loadProperty();
//
//        DetectService detectService = new DetectService();
//        detectService.setTemplate(template);
//        detectService.setDetectList();
//
////        OCRService ocrService = new OCRService();
////        ocrService.setTesseractService(tesseractService);
////        ocrService.setPage(pageMat);
////        ocrService.setGrad(OCRUtil.convert(pageMat));
//
////        detectService.setOcrService(ocrService);
//
////        Map<String, Field> fieldMap = detectService.detectAll(pageMat);
////
////        if (fieldMap == null || fieldMap.size() == 0)
////            throw new RuntimeException("Unable to retrieve fields on current page");
////
//        OcrTemp result = new OcrTemp();
////        FieldUtil.pickField(result.getFieldList(), fieldMap, "ocr-" + tempName);
////        result.addField(new Field().setId("senderBIC").setValue("SBOSUS3UIMS"));
////        result.addField(new Field().setId("receiverBIC").setValue("SBOSUS3UIMS"));
////        result.addField(new Field().setId("clientName").setValue("BLACKROCK"));
////        result.addField(new Field().setId("tradeType").setValue("Cash"));
////        result.addField(new Field().setId("etdSender").setValue("IMAGE"));
//
//        return result;
//    }

    private TempScanResp ocrTempScan(String fileName, String page, String tempName) {

//        int pageIndex = Integer.parseInt(StringUtils.remove(page, "Page-"));
//        fax = getFax(fileName);
//        opencv_core.Mat pageMat = fax.getPageMat(pageIndex - 1);
//        TempScanResp resp = new TempScanResp();
//        OcrTemp result = scanTemp(fileName, pageMat, tempName);
//        resp.setTemp(result);
//        resp.setPageIndex(Integer.valueOf(pageIndex));
//        resp.setParentHeight(Long.valueOf(pageMat.size().height()));
//        resp.setParentWidth(Long.valueOf(pageMat.size().width()));
//
//        return resp;
        return null;
    }

//    private Fax getFax(String fileName) {
//        if (fax == null)
//            fax = new Fax();
//        if (fax.isMatVectorNull()) {
//            fax.setPath(storePath + "/" + FileUtil.getFileName(fileName));
//            fax.split();
//        }
//        return fax;
//    }
}

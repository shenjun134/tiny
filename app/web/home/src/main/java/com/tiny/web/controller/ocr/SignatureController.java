package com.tiny.web.controller.ocr;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.TIFFEncodeParam;
import com.tiny.common.exception.InitializationException;
import com.tiny.core.model.FileInfo;
import com.tiny.core.model.SubFile;
import com.tiny.web.controller.BaseJsonResult;
import com.tiny.web.controller.http.request.BatchSignReq;
import com.tiny.web.controller.http.request.SignatureReq;
import com.tiny.web.controller.integration.entity.FixedFax;
import com.tiny.web.controller.integration.factory.SignatureFactory;
import com.tiny.web.controller.integration.util.RandomUtil;
import com.tiny.web.controller.ocr.model.Box;
import com.tiny.web.controller.ocr.util.FileUtil;
import com.tiny.web.controller.ocr.util.ImageUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

@Controller
public class SignatureController extends OCRController {

    /**
     *
     */
    private static final Logger logger = Logger.getLogger(SignatureController.class);

    @Autowired
    private ServletContext context;

    @Autowired
    private SignatureFactory signatureFactory;

    private String webInfAbsolutePath;
    /**
     * save the upload file
     */
    private String storePath;

    private String storeTempPath;

    private String resourceBasePath;

    private String tempPath;


    interface Constant {
        //        String imgType = "jpg";
        String imgType = "png";
        String filePrefix = "file:";
    }

    @PostConstruct
    public void init() {
        String classPath = Thread.currentThread().getContextClassLoader().getResource("/").toString();
        logger.info("SignatureController classPath:" + classPath);
        webInfAbsolutePath = StringUtils.substring(classPath, 0, classPath.indexOf("/classes"));
        if (StringUtils.startsWith(webInfAbsolutePath, Constant.filePrefix)) {
            webInfAbsolutePath = webInfAbsolutePath.substring(Constant.filePrefix.length());
        }
        storePath = context.getRealPath("/WEB-INF/resources/uploaded/ocr/signature/");
        storeTempPath = context.getRealPath("/WEB-INF/resources/temp/ocr/signature/");
        if (StringUtils.isBlank(storePath)) {
            throw new InitializationException("OCR_UPLOAD_SERVER not config...");
        }
        String contextPath = context.getContextPath();
        resourceBasePath = contextPath + "/resources/uploaded/ocr/signature/";
        tempPath = contextPath + "/resources/temp/ocr/signature/";
        logger.info("SignatureController init end... webInfAbsolutePath:" + webInfAbsolutePath);
        logger.info("SignatureController init end... storePath:" + storePath);
        logger.info("SignatureController init end... resourceBasePath:" + resourceBasePath);
    }

    @PreDestroy
    public void destory() {
    }

    @RequestMapping(path = {UrlCenter.OCR.SIGNATURE})
    public String index(ModelMap model) {
        model.addAttribute("view_path", UrlCenter.OCR.SIGNATURE);
        model.addAttribute("writer-list", RandomUtil.loadNameList());
        model.addAttribute("biz-tag-list", RandomUtil.loadBizTag());
        model.addAttribute("layout-list", RandomUtil.loadLayout());
        return View.SIGNATURE;
    }


    @RequestMapping(path = {UrlCenter.OCR.SIGNATURE_UPLOAD}, headers = ("content-type=multipart/*"), method = RequestMethod.POST)
    @ResponseBody
    public Object signatureUpload(ModelMap model, @RequestParam("file") MultipartFile file, @RequestParam("checkType") String checkType) {
        BaseJsonResult baseJsonResult = new BaseJsonResult();
        if (file == null) {
            return baseJsonResult.marketFail("no signature upload...");
        }
        try {
            baseJsonResult.markeSuccess("upload succ", saveFile(file, checkType));
        } catch (Exception e) {
            logger.error("signatureUpload error -- " + e.getMessage(), e);
            baseJsonResult.marketFail("signature upload fail--" + e.getMessage());
        }
        return baseJsonResult;
    }

    @RequestMapping(path = UrlCenter.OCR.ROTATE_IMAGE, method = RequestMethod.POST)
    @ResponseBody
    public Object imageRotate(@RequestParam("imgPath") String imgPath, @RequestParam("imgName") String imgName, @RequestParam("degree") String degree, @RequestParam("transparency") String transparency) {
        logger.info("begin to rotate with imgPath：" + imgPath + ", imgName:" + imgName);
        BaseJsonResult baseJsonResult = new BaseJsonResult();
        if (StringUtils.isBlank(imgPath)) {
            logger.error("Cannot find your file path");
            return baseJsonResult.marketFail("Cannot find your file path.");
        }
        if (StringUtils.isBlank(imgName)) {
            logger.error("Cannot find your file name ");
            return baseJsonResult.marketFail("Cannot find your file name.");
        }
        try {
            double degreeD = NumberUtils.toDouble(degree, 0);
            if (degreeD % 360 == 0) {
                return baseJsonResult.marketFail("degree is zero");
            }
            String type = imgName.substring(imgName.lastIndexOf(".") + 1);
            String targetName = FileUtil.randomFileName(imgName);
            String realPath = imgPath;
            String contextPath = context.getContextPath();
            String join = "/";
            if (StringUtils.endsWith(imgPath, "/") || StringUtils.endsWith(imgPath, "\\")) {
                join = "";
            }
            if (StringUtils.startsWith(imgPath, contextPath)) {
                realPath = imgPath.substring(contextPath.length(), imgPath.length());
            }

            BufferedImage src = ImageIO.read(new File(webInfAbsolutePath + realPath + join + imgName));
            BufferedImage rotate = (BufferedImage) ImageUtils.rotateImage(src, degreeD, NumberUtils.toInt(transparency, 1));
            ImageIO.write(rotate, type, new File(webInfAbsolutePath + realPath + join + targetName));
            FileInfo fileInfo = new FileInfo(imgPath + join + targetName, 0L);
            fileInfo.setPath(imgPath);
            fileInfo.setShortName(targetName);
            fileInfo.setType(type);
            baseJsonResult.markeSuccess("rotate succ", fileInfo);

        } catch (Exception e) {
            logger.error("image rotate error", e);
        }
        return baseJsonResult;
    }

    @RequestMapping(path = UrlCenter.OCR.SIGNATURE_LOAD_SAMPLE_NAME, method = RequestMethod.GET)
    @ResponseBody
    public Object sampleNameLoad(@RequestParam("name") String name) {
        BaseJsonResult baseJsonResult = new BaseJsonResult();
        if (StringUtils.isBlank(name)) {
            return baseJsonResult.markeSuccess("load succ", RandomUtil.loadNameList());
        }
        try {
            return baseJsonResult.markeSuccess("load succ", RandomUtil.queryName(name));
        } catch (Exception e) {
            logger.error("sampleNameLoad error - " + name, e);
            baseJsonResult.marketFail(e.getLocalizedMessage());
        }
        return baseJsonResult;
    }

    @RequestMapping(path = {UrlCenter.OCR.SIGNATURE_VALIDATE}, method = RequestMethod.POST)
    @ResponseBody
    public Object validate(@RequestParam("imgPath") String imgPath, @RequestParam("imgName") String imgName, @RequestParam("fileName") String fileName, @RequestParam("page") String page) {
        BaseJsonResult baseJsonResult = new BaseJsonResult();
        if (StringUtils.isBlank(imgPath)) {
            logger.error("Cannot find your file path");
            return baseJsonResult.marketFail("Validate signature fail.");
        }
        if (StringUtils.isBlank(imgName)) {
            logger.error("Cannot find your file name ");
            return baseJsonResult.marketFail("Validate signature fail.");
        }
        try {
            String realPath = imgPath;
            String contextPath = context.getContextPath();
            if (StringUtils.startsWith(imgPath, contextPath)) {
                realPath = imgPath.substring(contextPath.length(), imgPath.length());
            }
            return signatureFactory.getService().scan(webInfAbsolutePath + realPath, imgName);
        } catch (Exception e) {
            logger.error("validate error -- " + e.getMessage(), e);
            baseJsonResult.marketFail("Validate signature fail--" + e.getMessage());
        }
        return baseJsonResult;
    }

    @RequestMapping(path = {UrlCenter.OCR.SIGNATURE_VALIDATE_2}, method = RequestMethod.POST)
    @ResponseBody
    public Object validate2(@RequestParam("imgPath") String imgPath, @RequestParam("imgName") String imgName, @RequestParam("fileName") String fileName, @RequestParam("page") String page) {
        BaseJsonResult baseJsonResult = new BaseJsonResult();
        if (StringUtils.isBlank(imgPath)) {
            logger.error("Cannot find your file path");
            return baseJsonResult.marketFail("Validate signature fail.");
        }
        if (StringUtils.isBlank(imgName)) {
            logger.error("Cannot find your file name ");
            return baseJsonResult.marketFail("Validate signature fail.");
        }
        try {
            String realPath = imgPath;
            String contextPath = context.getContextPath();
            if (StringUtils.startsWith(imgPath, contextPath)) {
                realPath = imgPath.substring(contextPath.length(), imgPath.length());
            }
            return signatureFactory.getService().scan2(webInfAbsolutePath + realPath, imgName);
        } catch (Exception e) {
            logger.error("validate error -- " + e.getMessage(), e);
            baseJsonResult.marketFail("Validate signature fail--" + e.getMessage());
        }
        return baseJsonResult;
    }

    @RequestMapping(path = {UrlCenter.OCR.SIGNATURE_SUBMIT}, method = RequestMethod.POST)
    @ResponseBody
    public Object submit(@RequestBody SignatureReq signatureReq) {
        logger.info("submit signatureReq:" + signatureReq);
        BaseJsonResult baseJsonResult = new BaseJsonResult();
        try {
            if (signatureReq.getFixedArea() != null) {
                signatureFactory.getService().fix(convert2FixFax(signatureReq));
            }
            baseJsonResult.markeSuccess("Signature submit successfully!", true);
        } catch (Exception e) {
            logger.error("submit error -- " + e.getMessage(), e);
            baseJsonResult.marketFail("submit signature fail--" + e.getMessage());
        }
        return baseJsonResult;
    }

    @RequestMapping(path = {UrlCenter.OCR.BATCH_SIGNATURE_SUBMIT}, method = RequestMethod.POST)
    @ResponseBody
    public Object batchSubmit(@RequestBody BatchSignReq batchSignReq) {
        logger.info("batchSubmit batchSignReq:" + batchSignReq);
        BaseJsonResult baseJsonResult = new BaseJsonResult();
        try {
            if (CollectionUtils.isEmpty(batchSignReq.getBatchReq())) {
                return baseJsonResult.marketFail("no request received here...");
            }
            for (SignatureReq signatureReq : batchSignReq.getBatchReq()) {
                if (signatureReq.getFixedArea() != null) {
                    signatureFactory.getService().fix(convert2FixFax(signatureReq));
                }
            }
            baseJsonResult.markeSuccess("Signature submit successfully!", true);
        } catch (Exception e) {
            logger.error("batchSubmit error -- " + e.getMessage(), e);
            baseJsonResult.marketFail("batchSubmit signature fail--" + e.getMessage());
        }
        return baseJsonResult;
    }

    @RequestMapping(path = {UrlCenter.OCR.BATCH_SIGNATURE_SUBMIT_2}, method = RequestMethod.POST)
    @ResponseBody
    public Object batchSubmit2(@RequestBody BatchSignReq batchSignReq) {
        logger.info("batchSubmit batchSignReq:" + batchSignReq);
        BaseJsonResult baseJsonResult = new BaseJsonResult();
        try {
            if (CollectionUtils.isEmpty(batchSignReq.getBatchReq())) {
                return baseJsonResult.marketFail("no request received here...");
            }
            for (SignatureReq signatureReq : batchSignReq.getBatchReq()) {
                if (signatureReq.getFixedArea() != null) {
                    signatureFactory.getService().fix2(convert2FixFax(signatureReq));
                }
            }
            baseJsonResult.markeSuccess("Signature submit successfully!", true);
        } catch (Exception e) {
            logger.error("batchSubmit error -- " + e.getMessage(), e);
            baseJsonResult.marketFail("batchSubmit signature fail--" + e.getMessage());
        }
        return baseJsonResult;
    }

    @RequestMapping(path = {UrlCenter.OCR.SIGNATURE_LOAD}, method = RequestMethod.POST)
    @ResponseBody
    public Object getPicList(@RequestBody SignatureReq signatureReq) {
        logger.info("getPicList signatureReq:" + signatureReq);
        BaseJsonResult baseJsonResult = new BaseJsonResult();
        try {
            if (CollectionUtils.isEmpty(signatureReq.getFolderPath())) {
                return baseJsonResult.marketFail("No path find!");
            }
            File picStoreDir = new File(storePath);
            if (!picStoreDir.exists()) {
                picStoreDir.mkdirs();
            }
            File[] files = picStoreDir.listFiles();
            List<FileInfo> fileInfos = new ArrayList<>();
            TreeMap<Long, FileInfo> treeMap = new TreeMap<>();
            if (files != null) {
                for (File file : files) {
                    FileInfo fileInfo = new FileInfo(resourceBasePath + file.getName(), file.length());
                    fileInfo.setPath(resourceBasePath);
                    fileInfo.setShortName(file.getName());
                    fileInfo.setType(StringUtils.substringAfterLast(file.getName(), "."));
                    Long lastModified = file.lastModified();
                    treeMap.put(lastModified, fileInfo);
                }
                fileInfos.addAll(treeMap.values());
                Collections.reverse(fileInfos);
            }

            baseJsonResult.markeSuccess("Load picture successfully!", fileInfos);
        } catch (Exception e) {
            logger.error("Load Picture error -- " + e.getMessage(), e);
            baseJsonResult.marketFail("Load Picture error--" + e.getMessage());
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
        String savedName = FileUtil.randomFileName(originalFilename);

        String serverFilePath = dir.getAbsolutePath() + File.separator + savedName;
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
        FileInfo fileInfo = new FileInfo(resourceBasePath + savedName, file.getSize());
        fileInfo.setPath(resourceBasePath);
        fileInfo.setShortName(savedName);
        fileInfo.setType(file.getContentType());
        List<SubFile> subFiles = null;
        if (StringUtils.endsWith(file.getOriginalFilename(), ".pdf")) {
            subFiles = splitPdf2Img(serverFilePath, file);
        } else if (StringUtils.endsWith(file.getOriginalFilename(), ".tif") || StringUtils.endsWith(file.getOriginalFilename(), ".tiff")) {
            subFiles = splitTiff2Img2(serverFilePath, file);
        }
        if (CollectionUtils.isNotEmpty(subFiles)) {
            fileInfo.setSubFileList(subFiles);
        }
        return fileInfo;
    }

    private List<SubFile> splitPdf2Img(String serverFilePath, MultipartFile file) {
        String originalFile = file.getOriginalFilename();
        if (!StringUtils.endsWith(originalFile, "." + Constant.imgType)) {
            return null;
        }
        String tiffSrc = FileUtil.randomFileName();

//        String tiffSrc = originalFile.substring(0, originalFile.indexOf("."));
        List<SubFile> subFiles = new ArrayList<SubFile>();
        File dir = new File(storeTempPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        PDDocument document = null;
        try {
            document = PDDocument.load(new File(serverFilePath));
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            for (int page = 0; page < document.getNumberOfPages(); ++page) {
                String tempName = dir.getAbsolutePath() + File.separator + tiffSrc + "-" + page + "." + Constant.imgType;
                String shortName = tiffSrc + "-" + page + "." + Constant.imgType;
                String retTempName = tempPath + shortName;

                BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);

                ImageIOUtil.writeImage(bim, tempName, 300);

                SubFile subFile = new SubFile();
                subFile.setName(retTempName);
                subFile.setPath(tempPath);
                subFile.setShortName(shortName);
                subFiles.add(subFile);
            }
        } catch (Exception e) {
            logger.error("splitPdf2Img error", e);
            throw new RuntimeException("splitPdf2Img error", e);
        } finally {
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    logger.error("close document error when splitPdf2Img", e);
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
        String tiffSrc = FileUtil.randomFileName();
//        String tiffSrc = originalFile.substring(0, originalFile.indexOf("."));
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

                String tempName = dir.getAbsolutePath() + File.separator + tiffSrc + "-" + i + "." + Constant.imgType;
                String retTempName = tempPath + tiffSrc + "-" + i + "." + Constant.imgType;
                RenderedImage page = imageDecoder.decodeAsRenderedImage(i);
                ParameterBlock parameterBlock = new ParameterBlock();
                /* add source of page */
                parameterBlock.addSource(page);
                /* add o/p file path */
                parameterBlock.add(tempName);
                /* add o/p file type */
                parameterBlock.add(Constant.imgType);
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


    private FixedFax convert2FixFax(SignatureReq signatureReq) {
        FixedFax fixedFax = new FixedFax();
        Box box = signatureReq.getFixedArea();
        fixedFax.setWidth(NumberUtils.toDouble(box.getW(), 0));
        fixedFax.setHeight(NumberUtils.toDouble(box.getH(), 0));
        fixedFax.setXmin(NumberUtils.toDouble(box.getX(), 0));
        fixedFax.setYmin(NumberUtils.toDouble(box.getY(), 0));

        fixedFax.setFixedImageId(signatureReq.getValidateId());
        fixedFax.setWriterId(NumberUtils.toLong(signatureReq.getWriterId(), 0));

        fixedFax.setWriterName(signatureReq.getName());

        fixedFax.setWriterEmail(signatureReq.getEmail());

        fixedFax.setComments(signatureReq.getComments());

        return fixedFax;
    }

}

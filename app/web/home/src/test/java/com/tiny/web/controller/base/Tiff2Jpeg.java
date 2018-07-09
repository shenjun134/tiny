package com.tiny.web.controller.base;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class Tiff2Jpeg {
    public static void main(String[] args) throws IOException {

        String fileName = "FTTMT1J6Z6CFIJ_REPLIX_FAX_2017083011260708.tiff";

        String tiffSrc = "C:\\Users\\e521907\\Desktop\\OCR_DEMO\\Cash_Multi\\";

        File file = new File(tiffSrc + fileName);


        String srcFileName = fileName.substring(0, fileName.indexOf("."));

        ImageInputStream iis = ImageIO.createImageInputStream(new FileInputStream(file));

        Iterator readers = ImageIO.getImageReaders(iis);

// create ImageReader object to parse the Image

        ImageReader ir = (ImageReader) readers.next();

// specify the input source

        ir.setInput(iis);

// get the count of frames in TIFF image

        int frameCount = ir.getNumImages(true);

// iterate through each TIFF frame

        for (int i = 0; i < frameCount; ++i) {
            // save the individual TIFF frame as separate image with JPEG compression
            String tempName = tiffSrc + srcFileName + "-" + i + ".png";
            ImageIO.write(ir.read(i), "png", new File(tempName));
        }


    }
}

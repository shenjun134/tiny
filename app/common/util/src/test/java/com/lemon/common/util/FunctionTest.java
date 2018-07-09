/**
 * FunctionTest.java
 *
 *
 */
package com.lemon.common.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.apache.commons.lang.ArrayUtils;


/**
 * @author e521907
 * @version 1.0
 *
 */
public class FunctionTest {
	public static void main(String[] args) {

		BufferedImage bufferedImage;

		try {
			
			System.out.println(1.111111111111111111d);
			System.out.println(1.111111111111111111f);
			System.out.println(Arrays.asList(ArrayUtils.toObject(new int[]{1,2,3})));

			System.out.println((int)Double.parseDouble("0.1"));
			
			// read image file
			bufferedImage = ImageIO.read(new File("C:\\Users\\e521907\\Desktop\\1.jpg"));

			// create a blank, RGB, same width and height, and a white background
			BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
					BufferedImage.TYPE_INT_RGB);
			newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

			// write to jpeg file
			ImageIO.write(newBufferedImage, "jpg", new File("C:\\Users\\e521907\\Desktop\\1_.jpg"));

			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

}

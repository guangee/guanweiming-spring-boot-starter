package com.guanweiming.common.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author chezhu.xin
 */
public class ImageCropUtil {
    /**
     * @param inputStream  输入流
     * @param outputStream 输出流
     * @param x            x坐标
     * @param y            y坐标
     * @param desWidth     宽度
     * @param desHeight    高度
     * @throws IOException
     */
    public static void imgCut(InputStream inputStream, OutputStream outputStream, int x, int y, int desWidth,
                              int desHeight) throws IOException {
        Image img;
        ImageFilter cropFilter;
        BufferedImage bi = ImageIO.read(inputStream);
        int srcWidth = bi.getWidth();
        int srcHeight = bi.getHeight();
        System.out.println(srcWidth);
        System.out.println(srcHeight);
        if (srcWidth >= desWidth && srcHeight >= desHeight) {
            Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
            cropFilter = new CropImageFilter(x, y, desWidth, desHeight);
            img = Toolkit.getDefaultToolkit().createImage(
                    new FilteredImageSource(image.getSource(), cropFilter));
            BufferedImage tag = new BufferedImage(desWidth, desHeight,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();
            //输出文件
            ImageIO.write(tag, "JPEG", outputStream);
        }
    }
}

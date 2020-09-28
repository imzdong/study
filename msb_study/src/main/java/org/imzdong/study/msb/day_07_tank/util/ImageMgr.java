package org.imzdong.study.msb.day_07_tank.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageMgr {

    public static BufferedImage tankU, tankD, tankL, tankR;
    public static BufferedImage bulletU, bulletD, bulletL, bulletR;

    static {
        ClassLoader classLoader = ImageMgr.class.getClassLoader();
        try {
            tankU = ImageIO.read(classLoader.getResourceAsStream("images/tankU.gif"));
            tankD = ImageIO.read(classLoader.getResourceAsStream("images/tankD.gif"));
            tankL = ImageIO.read(classLoader.getResourceAsStream("images/tankL.gif"));
            tankR = ImageIO.read(classLoader.getResourceAsStream("images/tankR.gif"));

            bulletU = ImageIO.read(classLoader.getResourceAsStream("images/bulletU.gif"));
            bulletD = ImageIO.read(classLoader.getResourceAsStream("images/bulletD.gif"));
            bulletL = ImageIO.read(classLoader.getResourceAsStream("images/bulletL.gif"));
            bulletR = ImageIO.read(classLoader.getResourceAsStream("images/bulletR.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

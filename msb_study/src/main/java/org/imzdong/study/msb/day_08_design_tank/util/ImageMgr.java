package org.imzdong.study.msb.day_08_design_tank.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageMgr {

    public static BufferedImage tankU, tankD, tankL, tankR;
    public static BufferedImage badTankU, badTankD, badTankL, badTankR;
    public static BufferedImage bulletU, bulletD, bulletL, bulletR;
    public static BufferedImage [] booms = new BufferedImage[16];
    public static BufferedImage [] blockBooms = new BufferedImage[11];

    public static BufferedImage simpleTankU, simpleTankD, simpleTankL, simpleTankR;
    public static BufferedImage simpleBadTankU, simpleBadTankD, simpleBadTankL, simpleBadTankR;

    private ImageMgr(){}

    static {
        ClassLoader classLoader = ImageMgr.class.getClassLoader();
        try {
            tankU = ImageIO.read(classLoader.getResourceAsStream("images/GoodTank1.png"));
            tankD = ImageUtil.rotateImage(tankU,180);
            tankL = ImageUtil.rotateImage(tankU,-90);
            tankR = ImageUtil.rotateImage(tankU,90);

            badTankU = ImageIO.read(classLoader.getResourceAsStream("images/BadTank1.png"));
            badTankD = ImageUtil.rotateImage(badTankU,180);
            badTankL = ImageUtil.rotateImage(badTankU,-90);
            badTankR = ImageUtil.rotateImage(badTankU,90);

            bulletU = ImageIO.read(classLoader.getResourceAsStream("images/bulletU.png"));
            bulletD = ImageUtil.rotateImage(bulletU,180);
            bulletL = ImageUtil.rotateImage(bulletU,-90);
            bulletR = ImageUtil.rotateImage(bulletU,90);

            for (int i = 0; i < 16; i++) {
                booms[i] = ImageIO.read(classLoader.getResourceAsStream("images/e"+(i+1)+".gif"));
            }

            simpleTankU = ImageIO.read(classLoader.getResourceAsStream("images/tankU.gif"));
            simpleTankD = ImageUtil.rotateImage(simpleTankU,180);
            simpleTankL = ImageUtil.rotateImage(simpleTankU,-90);
            simpleTankR = ImageUtil.rotateImage(simpleTankU,90);

            simpleBadTankU = ImageIO.read(classLoader.getResourceAsStream("images/BadTank2.png"));
            simpleBadTankD = ImageUtil.rotateImage(simpleBadTankU,180);
            simpleBadTankL = ImageUtil.rotateImage(simpleBadTankU,-90);
            simpleBadTankR = ImageUtil.rotateImage(simpleBadTankU,90);

            for (int i = 0; i < 11; i++) {
                blockBooms[i] = ImageIO.read(classLoader.getResourceAsStream("images/"+i+".gif"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

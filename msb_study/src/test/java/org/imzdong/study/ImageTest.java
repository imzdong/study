package org.imzdong.study;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Unit test for simple App.
 */
public class ImageTest{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void getImage(){
        InputStream resourceAsStream = ImageTest.class.getClassLoader().getResourceAsStream("images/tank.png");
        try {
            BufferedImage bufferedImage = ImageIO.read(resourceAsStream);
            assertNotNull(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package org.imzdong.tool.easy;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author dong.zhou
 * @since 2022/3/2 15:57
 */
public class TestIO {

    private String testFilePath = "D:\\WorkSpace\\task\\7、c4权限\\";

    @Test
    public void testText() throws Exception{
        String txtName = "1232.txt";
        File textFile = new File(testFilePath + txtName);
        Files.lines(textFile.toPath()).forEach(System.out::println);
    }

    @Test(expected = IOException.class)
    public void testExcel() throws Exception{
        String excelName = "TLF-Resource.xlsx";
        File excelFile = new File(testFilePath + excelName);
        Files.lines(excelFile.toPath()).forEach(System.out::println);
    }

    @Test
    public void testFile() throws Exception{
        String excelName = "TLF-Resource.xlsx";
        File excelFile = new File(testFilePath + excelName);
        System.out.println(excelFile.toPath());
    }
}

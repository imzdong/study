package org.imzdong.tool.easy;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import org.imzdong.tool.excel.Resource;
import org.imzdong.tool.excel.ResourceAction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dong.zhou
 * @since 2022/3/2 16:13
 */
public class EasyExcelTest {

    private String filePath = "D:\\WorkSpace\\task\\7c4role\\";

    @Test
    public void testRead(){
        String excelName = "TLF-Resources.xlsx";
        EasyExcel.read(filePath + excelName, Resource.class,
                new PageReadListener<Resource>(records->{
                    records.forEach(m-> System.out.println(m));
                }))
                .sheet("RootResources")
                .doRead();
    }

    @Test
    public void testMultSheetRead(){
        String excelName = "TLF-Resources.xlsx";
        /*EasyExcel.read(filePath + excelName, Resource.class,
                new PageReadListener<Resource>(records->{
                    records.forEach(m-> System.out.println(m));
                }))
                .doReadAll();*/

        // 读取部分sheet
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(filePath + excelName).build();

            // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
            ReadSheet readSheet1 =
                    EasyExcel.readSheet("SubResources").head(Resource.class)
                            .registerReadListener(new PageReadListener<Resource>(records->{
                                records.forEach(m-> System.out.println(m));
                            }))
                            .build();
            ReadSheet readSheet2 =
                    EasyExcel.readSheet("Actions").head(ResourceAction.class)
                            .registerReadListener(new PageReadListener<ResourceAction>(records->{
                                records.forEach(m-> System.out.println(m));
                            }))
                            .build();
            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(readSheet1, readSheet2);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }

    @Test
    public void testWrite(){
        List<Resource> rootList = new ArrayList<>();
        String excelName = "TLF-Resources.xlsx";
        EasyExcel.read(filePath + excelName, Resource.class,
                new PageReadListener<Resource>(rootList::addAll))
                .sheet("RootResources")
                .doRead();
        String writeExcelName = "TLF-ResourcesWrite.xlsx";
        EasyExcel.write(filePath + writeExcelName, Resource.class).sheet("write").doWrite(rootList);
    }

}

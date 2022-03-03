package org.imzdong.tool.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author dong.zhou
 * @since 2022/3/3 10:10
 */
@Data
public class Result {

    @ExcelProperty("idOne")
    private String idOne;
    @ExcelProperty("parentIdOne")
    private String parentIdOne;

    @ExcelProperty("idTwo")
    private String idTwo;
    @ExcelProperty("parentIdTwo")
    private String parentIdTwo;

    @ExcelProperty("idThree")
    private String idThree;
    @ExcelProperty("parentIdThree")
    private String parentIdThree;

    @ExcelProperty("idFour")
    private String idFour;
    @ExcelProperty("parentIdFour")
    private String parentIdFour;

    @ExcelProperty("showName")
    private String showName;
    @ExcelProperty("code")
    private String code;
    @ExcelProperty("path")
    private String path;


}

package org.imzdong.tool.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author dong.zhou
 * @since 2022/3/2 16:25
 */
@Data
public class Resource {

    @ExcelProperty("APP_RESOURCE_ID")
    private String resourceId;
    @ExcelProperty("APPLICATION_ID")
    private String applicationId;
    @ExcelProperty("APP_RESOURCE_NAME")
    private String resourceName;
    @ExcelProperty("APP_RESOURCE_SHOW_NAME")
    private String showName;
    @ExcelProperty("APP_RESOURCE_PARENT_ID")
    private String parentId;

}

package org.imzdong.tool.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author dong.zhou
 * @since 2022/3/2 16:25
 */
@Data
public class ResourceAction {

    @ExcelProperty("APP_RESOURCE_ACTION_ID")
    private String actionId;
    @ExcelProperty("APP_RESOURCE_ID")
    private String resourceId;
    @ExcelProperty("ACTION_NAME")
    private String actionName;
    @ExcelProperty("ACTION_SHOW_NAME")
    private String showName;

}

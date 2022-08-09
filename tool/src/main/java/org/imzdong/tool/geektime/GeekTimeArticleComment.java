package org.imzdong.tool.geektime;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author dong.zhou
 * @since 2022/8/9 13:20
 */
@Data
public class GeekTimeArticleComment {

    @JSONField(name = "comment_content")
    private String commentContent;

}

package org.imzdong.geektime.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author DongZhou
 * @since 2024/11/25 10:35
 */
@Setter
@Getter
public class CourseInfo {

    //"article_count": 33
    //"column_title": "AI大模型实战高手课",
    //"author_name": "独行",
    //"column_poster": "https://static001.geekbang.org/resource/image/8a/72/8a0e24f78b6fec6ae7270ec71e84b472.png",
    private String columnTitle;
    private String authorName;
    private Integer articleCount;
    private String cover;



}

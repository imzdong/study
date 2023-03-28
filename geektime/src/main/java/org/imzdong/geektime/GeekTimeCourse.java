package org.imzdong.geektime;

/**
 * 课程信息
 * @author zhoud
 * @since 2021/3/3 21:52
 */
public class GeekTimeCourse {

    private String id; //课程id
    private String title; //课程题目

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "GeekTimeCourse{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}

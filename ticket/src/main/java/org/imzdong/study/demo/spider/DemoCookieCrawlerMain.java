package org.imzdong.study.demo.spider;

/**
 * @description:
 * @author: Winter
 * @time: 2020/1/17
 */
public class DemoCookieCrawlerMain {

    public static void main(String[] args) throws Exception {
        DemoCookieCrawler demoCookieCrawler = new DemoCookieCrawler("", true);
        demoCookieCrawler.start(1);
    }
}

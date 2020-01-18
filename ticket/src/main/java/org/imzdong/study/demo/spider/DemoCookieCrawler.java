package org.imzdong.study.demo.spider;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.rocks.BreadthCrawler;

/**
 * @description:
 * @author: Winter
 * @time: 2020/1/17
 */
public class DemoCookieCrawler extends BreadthCrawler {

    public DemoCookieCrawler(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
    }

    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {

    }
}

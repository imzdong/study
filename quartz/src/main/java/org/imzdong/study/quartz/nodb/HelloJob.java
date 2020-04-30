package org.imzdong.study.quartz.nodb;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @description:
 * @author: Winter
 * @time: 2020年4月29日, 0029 13:43
 */
public class HelloJob implements Job {

    public HelloJob() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Logger logger = LoggerFactory.getLogger(HelloJob.class);
        JobKey jobKey = context.getJobDetail().getKey();
        logger.info("---" + jobKey + " executing at " + new Date());
        logger.debug("Hello Job!");
    }
}

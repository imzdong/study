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
public class FinalJob implements Job {

    public FinalJob() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Logger logger = LoggerFactory.getLogger(FinalJob.class);
        JobKey jobKey = context.getJobDetail().getKey();
        logger.info("---" + jobKey + " executing at " + new Date());
        logger.debug("FinalJob!");
        try {
            logger.debug("FinalJob shutdown start!");
            context.getScheduler().shutdown();
            logger.debug("FinalJob shutdown end!");
        }catch (SchedulerException e){
            logger.error("FinalJob e",e);
        }

    }
}

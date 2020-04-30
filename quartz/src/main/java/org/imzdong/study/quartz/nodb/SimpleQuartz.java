package org.imzdong.study.quartz.nodb;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @description:
 * @author: Winter
 * @time: 2020年4月29日, 0029 10:52
 */
public class SimpleQuartz {

    public void run(){
        Logger logger = LoggerFactory.getLogger(SimpleQuartz.class);
        try {
            Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
            defaultScheduler.start();
            JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)//实际job，通过反射初始化
                    .withIdentity("Winter","group1").build();
            //Date todayAt = DateBuilder.todayAt(10, 37, 0);
            Date startTime = DateBuilder.nextGivenSecondDate(null, 15);
            Trigger trigger = newTrigger().withIdentity("trigger3", "group1").startAt(startTime)
                    .withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10))
                    .build();
            Date date = defaultScheduler.scheduleJob(jobDetail, trigger);
            SchedulerContext context = defaultScheduler.getContext();
            context.put("globalCount",10);
            logger.debug("job执行日期：{}",date);

            // Set up the listener
            JobListener listener = new FinalJobListener();
            Matcher<JobKey> matcher = KeyMatcher.keyEquals(jobDetail.getKey());
            defaultScheduler.getListenerManager().addJobListener(listener, matcher);

            //defaultScheduler.shutdown();
        }catch (Exception e){
            logger.error("启动异常：",e);
        }
    }

    public static void main(String[] args) {
        SimpleQuartz quartz = new SimpleQuartz();
        quartz.run();
    }
}

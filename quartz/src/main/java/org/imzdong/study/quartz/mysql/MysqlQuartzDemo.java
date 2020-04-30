package org.imzdong.study.quartz.mysql;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * @description:
 * @author: Winter
 * @time: 2020年4月30日, 0030 14:53
 */
public class MysqlQuartzDemo {

    private void run(){
        //quartz/src/main/resources/quartz_mysql.properties
        //D:\workspace\jci-java\study\quartz\src\main\resources\quartz_mysql.properties
        System.setProperty(StdSchedulerFactory.PROPERTIES_FILE,"quartz_mysql.properties");
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            JobDetail mysql = JobBuilder.newJob(MysqlJob.class).withIdentity("mysql").build();
            SimpleTrigger trigger = TriggerBuilder.newTrigger().startNow()
                    .withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(10))
                    .build();
            scheduler.scheduleJob(mysql,trigger);
            scheduler.start();
            Thread.sleep(6000);
            scheduler.shutdown();
        } catch (SchedulerException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MysqlQuartzDemo demo = new MysqlQuartzDemo();
        demo.run();
    }
}

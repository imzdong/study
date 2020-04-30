package org.imzdong.study.quartz.mysql;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @description:
 * @author: Winter
 * @time: 2020年4月30日, 0030 14:45
 */
public class MysqlJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("mysqlJob");
    }
}

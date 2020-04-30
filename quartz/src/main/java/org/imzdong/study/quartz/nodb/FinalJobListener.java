/*
 * All content copyright Terracotta, Inc., unless otherwise indicated. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 */

package org.imzdong.study.quartz.nodb;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import jdk.nashorn.internal.parser.JSONParser;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wkratzer
 */
public class FinalJobListener implements JobListener {

    private static Logger _log = LoggerFactory.getLogger(FinalJobListener.class);

    @Override
    public String getName() {
        return "job1_to_job2";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext inContext) {
        _log.info("FinalJobListener says: Job Is about to be executed.");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext inContext) {
        _log.info("FinalJobListener says: Job Execution was vetoed.");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobContext, JobExecutionException inException) {
        _log.info("FinalJobListener says: Job was executed.");
        int globalCount = 10;
        try {
            Scheduler scheduler = jobContext.getScheduler();
            SchedulerContext schedulerContext = scheduler.getContext();
            if (schedulerContext.get("globalCount") != null) {
                globalCount = Integer.class.cast(schedulerContext.get("globalCount"));
                schedulerContext.put("globalCount", --globalCount);
                _log.debug("finalJob! globalCount:{}", globalCount);
            }
            if (globalCount == 0) {
                // Simple job #2
                JobDetail finalJob = newJob(FinalJob.class).withIdentity("finalJob").build();
                Trigger trigger = newTrigger().withIdentity("finalJobTrigger").startNow().build();
                // schedule the job to run!
                scheduler.scheduleJob(finalJob, trigger);
            }
        } catch (SchedulerException e) {
            _log.warn("Unable to schedule job2!");
            e.printStackTrace();
        }

    }

}

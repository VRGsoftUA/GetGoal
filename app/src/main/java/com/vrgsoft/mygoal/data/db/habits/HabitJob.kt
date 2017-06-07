package com.vrgsoft.mygoal.data.db.habits

import com.evernote.android.job.Job
import com.evernote.android.job.JobRequest

 class HabitJob : Job() {
    override fun onRunJob(params: Params?): Result {
        return Result.SUCCESS
    }

     companion object {
        val TAG: String = "habit_job_tag"
        fun scheduleJob() {
            JobRequest.Builder(TAG)
                    .setExact(System.currentTimeMillis()+3000)
                    .build()
                    .schedule()
        }
    }

}

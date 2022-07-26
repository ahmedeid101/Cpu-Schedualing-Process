package Lab.Algorithm;

import Lab.Job.Job;
import Lab.Job.JobWithPriority;

import java.util.*;


public class Priority extends AllocationStrategy {

    List<JobWithPriority> jobWithPriorityList;
    private int systemTime;

    public Priority(List<JobWithPriority> jobs) {
        super(jobs);
        this.jobWithPriorityList = jobs;
    }

    @Override
    public void run() {
        // 1. sort by priority, then by arrival time.
        this.jobWithPriorityList.sort(Comparator.comparing(JobWithPriority::getPriority).thenComparing(JobWithPriority::getArrivalTime));

        // to check if a job has finished, and the number of the jobs that have finished
        Set<JobWithPriority> jobFinished = new HashSet<>();

        while (jobFinished.size() < this.jobWithPriorityList.size()) {
            boolean noJobSelected = true;

            for (JobWithPriority job : this.jobWithPriorityList) {
                if (!jobFinished.contains(job) && job.getArrivalTime() <= this.systemTime) {
                    job.waitingTime = this.systemTime - job.getArrivalTime();
                    this.systemTime += job.getCpuTime();
                    job.turnAroundTime = this.systemTime - job.getArrivalTime();
                    jobFinished.add(job);
                    noJobSelected = false;
                    break;
                }
            }

            if (noJobSelected) {
                systemTime++;
            }
        }

        display();
    }
}

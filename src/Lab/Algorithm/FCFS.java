package Lab.Algorithm;

import Lab.Job.Job;

import java.util.List;

public class FCFS extends AllocationStrategy {

    public FCFS(List<Job> jobs) {
        super(jobs);
    }

    @Override
    public void run() {
        boolean firstJob = true;
        int systemTime = 0;

        for (Job job : super.jobList) {
            if (firstJob) {
                job.ProcessCompletionTime = job.getArrivalTime() + job.getCpuTime();
                firstJob = false;
            } else {
                job.ProcessCompletionTime = systemTime + job.getCpuTime();
            }
            systemTime = job.ProcessCompletionTime;
            job.turnAroundTime = systemTime - job.getArrivalTime();
            job.waitingTime = job.turnAroundTime - job.getCpuTime();
        }

        super.display();
    }
}


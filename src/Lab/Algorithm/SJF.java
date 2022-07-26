package Lab.Algorithm;

import Lab.Job.Job;

import java.util.List;
import java.util.Optional;

public class SJF extends AllocationStrategy {

    boolean[] jobCompletionList;

    int systemTime = 0, completedJobs = 0;

    public SJF(List<Job> jobs) {
        super(jobs);

        jobCompletionList = new boolean[this.jobList.size() + 1];
    }

    @Override
    public void run() {
        for (Job job : this.jobList) {
            jobCompletionList[job.getProcessId()] = false;
        }

        while (true) {
            if (this.completedJobs == this.jobList.size()) break;

            int currentJobId = this.jobList.size() + 1;
            int minCpuTime = Integer.MAX_VALUE;

            for (Job job : this.jobList) {
                if ((job.getArrivalTime() <= this.systemTime) && (!this.jobCompletionList[job.getProcessId()]) && (job.getCpuTime() < minCpuTime)) {
                    minCpuTime = job.getCpuTime();
                    currentJobId = job.getProcessId();
                }
            }
            // If value can not be updated because no process arrival time < system time, we increase the system time
            if (currentJobId == this.jobList.size() + 1) this.systemTime++;
            else {
                int finalC = currentJobId;
                Optional<? extends Job> optionalJob = this.jobList.stream().filter(j -> j.getProcessId() == finalC).findFirst();

                if (optionalJob.isPresent()) {
                    Job job = optionalJob.get();
                    job.ProcessCompletionTime = this.systemTime + job.getCpuTime();
                    this.systemTime += job.getCpuTime();
                    job.turnAroundTime = job.ProcessCompletionTime - job.getArrivalTime();
                    job.waitingTime = job.turnAroundTime - job.getCpuTime();
                    jobCompletionList[job.getProcessId()] = true;
                    this.completedJobs++;
                }
            }
        }

        super.display();

    }

}

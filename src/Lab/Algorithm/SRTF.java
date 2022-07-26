package Lab.Algorithm;

import Lab.Job.Job;

import java.util.List;
import java.util.Optional;

public class SRTF extends AllocationStrategy {
    boolean[] jobCompletionList;
    int[] cpuTimeList;
    int systemTime = 0, completedJobs = 0;

    public SRTF(List<Job> jobs) {
        super(jobs);

        jobCompletionList = new boolean[this.jobList.size() + 1];
        cpuTimeList = new int[this.jobList.size() + 1];
    }

    public void run() {
        for (Job job : this.jobList) {
            jobCompletionList[job.getProcessId()] = false;
            cpuTimeList[job.getProcessId()] = job.getCpuTime();
        }

        while (true) {
            if (this.completedJobs == this.jobList.size()) break;

            int currentJob = this.jobList.size() + 1;
            int minCpuTime = Integer.MAX_VALUE;

            for (Job job : this.jobList) {
                if ((job.getArrivalTime() <= this.systemTime)
                        && (!this.jobCompletionList[job.getProcessId()])
                        && (cpuTimeList[job.getProcessId()] < minCpuTime)) {
                    minCpuTime = cpuTimeList[job.getProcessId()];
                    currentJob = job.getProcessId();
                }
            }
            // If value can not be updated because no process arrival time < system time, we increase the system time
            if (currentJob == this.jobList.size() + 1) this.systemTime++;
            else {
                int finalC = currentJob;
                Optional<? extends Job> optionalJob = this.jobList.stream().filter(j -> j.getProcessId() == finalC).findFirst();

                if (optionalJob.isPresent()) {
                    Job job = optionalJob.get();

                    cpuTimeList[job.getProcessId()]--;
                    this.systemTime++;

                    if (cpuTimeList[job.getProcessId()] == 0) {
                        job.ProcessCompletionTime = this.systemTime;
                        job.turnAroundTime = job.ProcessCompletionTime - job.getArrivalTime();
                        job.waitingTime = job.turnAroundTime - job.getCpuTime();
                        this.jobCompletionList[job.getProcessId()] = true;
                        this.completedJobs++;
                    }
                }
            }
        }

        super.display();
    }
}

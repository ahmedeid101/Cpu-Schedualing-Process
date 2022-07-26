package Lab.Algorithm;

import Lab.Job.Job;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RoundRobin extends AllocationStrategy {
    private final int quantum;
    int[] completionTimeList;
    int totalCpuTime, cpuTimeFinished;
    int[][] jobRemainingCpuTimeList;

    int systemTime;

    public RoundRobin(List<Job> jobs, int quantum) {
        super(jobs);

        this.quantum = quantum;
    }


    @Override
    public void run() {
        jobRemainingCpuTimeList = new int[this.jobList.size()][2];
        completionTimeList = new int[this.jobList.size()];

        int count = 0;
        for (Job job : this.jobList) {
            jobRemainingCpuTimeList[count][0] = job.getProcessId();
            jobRemainingCpuTimeList[count][1] = job.getCpuTime();

            totalCpuTime += job.getCpuTime();

            count++;
        }

        loopOverJobs();


        int jobIndex = 0;
        for (int completionTime : completionTimeList) {
            System.out.println(jobRemainingCpuTimeList[jobIndex][0]);
            int finalJobIndex = jobIndex;
            Optional<? extends Job> optionalJob = this.jobList.stream().filter(j -> j.getProcessId() == jobRemainingCpuTimeList[finalJobIndex][0]).findFirst();
            if (optionalJob.isPresent()) {
                Job job = optionalJob.get();
                job.turnAroundTime = completionTime - job.getArrivalTime();
                job.waitingTime = job.turnAroundTime - job.getCpuTime();
                jobIndex++;
            }
        }

        display();
    }

    void loopOverJobs() {
        boolean noJobProccesed = true;

        for (int i = 0; i < this.jobList.size(); i++) {
            int remaingCpuTime = jobRemainingCpuTimeList[i][1];

            int finalI = i;
            Optional<? extends Job> optionalJob = this.jobList.stream()
                    .filter(j -> j.getProcessId() == jobRemainingCpuTimeList[finalI][0]).findFirst();
            if (optionalJob.isPresent()) {
                Job job = optionalJob.get();

                if (job.getArrivalTime() <= systemTime && remaingCpuTime > 0) {
                    noJobProccesed = false;

                    if (remaingCpuTime <= this.quantum) {
                        completionTimeList[i] = systemTime + remaingCpuTime;
                        systemTime += remaingCpuTime;
                        cpuTimeFinished += remaingCpuTime;

                        remaingCpuTime -= remaingCpuTime;
                    } else {
                        completionTimeList[i] = systemTime + this.quantum;
                        systemTime += this.quantum;
                        cpuTimeFinished += this.quantum;

                        remaingCpuTime -= this.quantum;
                    }

                    jobRemainingCpuTimeList[i][1] = remaingCpuTime;
                }

            }
        }

        if (noJobProccesed) {
            systemTime++;
        }

        if (cpuTimeFinished != totalCpuTime) loopOverJobs();
    }
}
package Lab.Algorithm;

import Lab.Job.Job;

import java.util.List;

public abstract class AllocationStrategy {
    protected List<? extends Job> jobList;

    double avgWaitingTime, avgTurnAroundTime;

    public AllocationStrategy(List<? extends Job> jobs) {
        super();
        this.jobList = jobs;
    }

    public abstract void run();

    public void display() {
        System.out.println("============================================ ");
        System.out.println("Process ID | Turnaround time | Waiting time ");
        System.out.println("============================================ ");

        for (Job job : this.jobList) {
            System.out.println("    " + job.getProcessId() + "       |     " + job.turnAroundTime + "            |   " + job.waitingTime);

            this.avgTurnAroundTime += job.turnAroundTime;
            this.avgWaitingTime += job.waitingTime;
        }

        System.out.println("===============================================");
        System.out.println("Avg waiting time = " + this.avgWaitingTime / this.jobList.size());
        System.out.println("===============================================");
        System.out.println("Avg turn round time = " + this.avgTurnAroundTime / this.jobList.size());
        System.out.println("===============================================");
    }

}

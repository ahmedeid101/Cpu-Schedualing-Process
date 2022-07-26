package Lab.Job;

import Lab.Job.Job;

public class JobWithPriority extends Job {
    private final int priority;

    public JobWithPriority(int processId, int arrivalTime, int cpuTime, int priority) {
        super(processId, arrivalTime, cpuTime);

        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}

package Lab.Job;

public class Job {
    public int turnAroundTime, ProcessCompletionTime, waitingTime;
    private final int processId, cpuTime, arrivalTime;

    public Job(int processId, int arrivalTime, int cpuTime) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.cpuTime = cpuTime;
    }

    public int getProcessId() {
        return processId;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }
    public int getCpuTime() {
        return cpuTime;
    }
}




package Lab;

import Lab.Algorithm.*;
import Lab.Job.Job;
import Lab.Job.JobWithPriority;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {

            System.out.println("SELECT THE ALGORITHM:");
            System.out.println("1. FCFS");
            System.out.println("2. SJF");
            System.out.println("3. SRTF");
            System.out.println("4. Round Robin");
            System.out.println("5. Priority");

            Scanner scanner = new Scanner(System.in);
            int allocationStrategy = scanner.nextInt();

            List<Job> jobList = new ArrayList<>();
            List<JobWithPriority> jobWithPriorityList = new ArrayList<>();

            String sCurrentLine;
            String filename = "C:\\Users\\Ahmed\\schedualing\\src\\Lab//testFile";


            BufferedReader br = new BufferedReader(new FileReader(filename));

            while ((sCurrentLine = br.readLine()) != null) {
                String[] a = sCurrentLine.split(",");
                int processId = Integer.parseInt(a[0]);
                int arrivalTime = Integer.parseInt(a[1]);
                int cpuTime = Integer.parseInt(a[2]);

                if (allocationStrategy == 5) {
                    int priority = Integer.parseInt(a[3]);
                    JobWithPriority job = new JobWithPriority(processId, arrivalTime, cpuTime, priority);
                    jobWithPriorityList.add(job);
                } else {
                    Job job = new Job(processId, arrivalTime, cpuTime);
                    jobList.add(job);
                    System.out.println(processId + " " + arrivalTime + " " + cpuTime);
                }
            }

            // TODO: move to the run method
            if (allocationStrategy != 5)
                jobList.sort(Comparator.comparingInt(Job::getArrivalTime));


            switch (allocationStrategy) {
                case 1 -> {
                    FCFS firstComeFirstServed = new FCFS(jobList);
                    firstComeFirstServed.run();
                }
                case 2 -> {
                    SJF sjf = new SJF(jobList);
                    sjf.run();
                }
                case 3 -> {
                    SRTF srtf = new SRTF(jobList);
                    srtf.run();
                }
                case 4 -> {
                    System.out.print("Enter Quantum value: ");
                    int quantum = scanner.nextInt();
                    RoundRobin roundRobin = new RoundRobin(jobList, quantum);
                    roundRobin.run();
                }
                case 5 -> {
                    Priority priority = new Priority(jobWithPriorityList);
                    priority.run();
                }
                default -> throw new Exception();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}


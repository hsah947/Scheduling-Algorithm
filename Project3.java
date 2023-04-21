import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Project3 {
    public static void main(String[] args) {
        // check if scheduler choice is passed
        if (args.length < 1) {
            throw new RuntimeException("Should pass scheduler choice as command line param");
        }

        // choice passed
        String scheduler = args[0].trim().toUpperCase();
        // read from a file
        List<Job> jobs = readJobsFromFile();

        // submit jobs to scheduler of user's choice
        if ("RR".equals(scheduler)) { // Round Robin (RR) Scheduler
            printJobNames(jobs);
            RRScheduler.INSTANCE.submit(jobs);
        } else if ("SRT".equals(scheduler)) { // Shortest Remaining Time (SRT) scheduler
            printJobNames(jobs);
            SRTScheduler.INSTANCE.submit(jobs);
        } else if ("FB".equals(scheduler)) { // Feedback (FB) Scheduler
            printJobNames(jobs);
            FBScheduler.INSTANCE.submit(jobs);
        } else if ("ALL".equals(scheduler)) { // All three schedulers: RR, SRT, FB
            printJobNames(jobs);
            RRScheduler.INSTANCE.submit(jobs);
            System.out.println("----------------------------------");
            printJobNames(jobs);
            SRTScheduler.INSTANCE.submit(readJobsFromFile());
            System.out.println("----------------------------------");
            printJobNames(jobs);
            FBScheduler.INSTANCE.submit(readJobsFromFile());
        } else {
            throw new RuntimeException("Unknown scheduler choice");
        }
    }

    // print header (job names)
    static void printJobNames(List<Job> jobs) {
        jobs.stream()
                .map(Job::getName)
                .forEach(System.out::print);
        System.out.println(); // new line
    }


    // read a file; convert each line to job object then collect all jobs into a list
    static List<Job> readJobsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("jobs.txt"))) {
            return reader.lines()
                    .map(Job::new)
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
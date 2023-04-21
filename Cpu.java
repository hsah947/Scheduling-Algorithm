// CPU to execute a job when submitted by scheduler
public class Cpu {

    private int clock;

    // run job, increment clock, print execution
    public void execute(Job job) {
        job.run();
        clock++;
        printExecution(job.getName());
    }

    public void idleUntil(int arrivalTime) {
        int idleTime = arrivalTime - clock - 1;
        for (int i = 0; i < idleTime; i++) {
            clock++;
            System.out.println();
        }
    }

    public int clock() {
        return clock;
    }

    private void printExecution(char jobName) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < jobName - 'A'; i++) {
            builder.append(' ');
        }
        builder.append('X');
        System.out.println(builder);
    }
}

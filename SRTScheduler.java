import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

// Shortest Running Time (SRT) scheduler
public enum SRTScheduler {
    INSTANCE;

    public void submit(List<Job> jobs) {
        Cpu cpu = new Cpu();
        Queue<Job> queue = new PriorityQueue<>(Comparator.comparingInt(Job::getRemainingTime));

        int arrivalIndex = 0; // index to look for new job arrival
        queue.offer(jobs.get(arrivalIndex++));  // assuming first job will always at 0

        do {
            Job job = queue.remove();

            // execute job in cpu
            cpu.execute(job);

            // if new jobs have arrived, put in ready queue
            while (arrivalIndex < jobs.size() && jobs.get(arrivalIndex).getArrivalTime() <= cpu.clock()) {
                queue.offer(jobs.get(arrivalIndex++));
            }

            // if current job is not completed add it to ready queue
            if (!job.isCompleted()) {
                queue.offer(job);
            }

            // special case: when nothing is running, then get the first job and reset cpu clock
            if (queue.isEmpty() && arrivalIndex < jobs.size()) {
                job = jobs.get(arrivalIndex++);
                cpu.idleUntil(job.getArrivalTime());
                queue.offer(job);
            }
        } while (!queue.isEmpty());
    }
}

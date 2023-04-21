import java.util.*;

// Feedback Scheduler
public enum FBScheduler {
    INSTANCE;

    public void submit(List<Job> jobs) {
        Cpu cpu = new Cpu();
        List<Queue<Job>> queues = Arrays.asList(new LinkedList<>(), new LinkedList<>(), new LinkedList<>());

        int arrivalIndex = 0; // index to look for new job arrival
        queues.get(0).offer(jobs.get(arrivalIndex++)); // assuming first job will always at 0

        do {
            int readyQueue = firstNonEmptyReadyQueue(queues);
            Job job = queues.get(readyQueue).peek();

            // execute job in cpu
            cpu.execute(job);

            // if new jobs have arrived, put in first level queue
            while (arrivalIndex < jobs.size() && jobs.get(arrivalIndex).getArrivalTime() <= cpu.clock()) {
                queues.get(0).offer(jobs.get(arrivalIndex++));
            }


            if (job.isCompleted()) { // if job completed, remove from ready queue
                queues.get(readyQueue).remove();
            } else if (waitingReadyJobs(queues) > 1) { // if other waiting ready jobs, move running job to next level queue
                int nextLevelQueue = Math.min(readyQueue + 1, 2);
                queues.get(nextLevelQueue).offer(queues.get(readyQueue).remove());
            }

            // special case: when nothing is running, then get the first job and reset cpu clock
            if (areEmpty(queues) && arrivalIndex < jobs.size()) {
                job = jobs.get(arrivalIndex++);
                cpu.idleUntil(job.getArrivalTime());
                queues.get(0).offer(job);
            }
        } while (!areEmpty(queues));
    }

    // checks if all queues in the list are empty
    private boolean areEmpty(List<Queue<Job>> queues) {
        return firstNonEmptyReadyQueue(queues) == -1;
    }

    // gets number of jobs in ready queue
    private int waitingReadyJobs(List<Queue<Job>> queues) {
        int readyJobs = 0;
        for (Queue<Job> queue : queues) {
            readyJobs += queue.size();
        }

        return readyJobs;
    }

    // get the index of the highest level non-empty ready queue (total 3 levels)
    // if all are empty, return -1
    private int firstNonEmptyReadyQueue(List<Queue<Job>> queues) {
        for (int i = 0; i < queues.size(); i++) {
            if (!queues.get(i).isEmpty()) {
                return i;
            }
        }
        return -1;
    }
}

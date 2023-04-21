public class Job {
    private final char name;
    private final int arrivalTime;
    private final int duration;

    private int remainingTime;

    public Job(String line) {
        String[] parts = line.split("\\s+");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid line. Line should have 'jobName\tarrivalTime\tduration'");
        }

        name = parts[0].charAt(0);
        arrivalTime = Integer.parseInt(parts[1]);
        duration = Integer.parseInt(parts[2]);
        remainingTime = duration;
    }

    public char getName() {
        return name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void run() {
        remainingTime--;
    }

    public boolean isCompleted() {
        return remainingTime <= 0;
    }

    @Override
    public String toString() {
        return String.format("Job(name:%c, arrivalTime:%d, duration:%d)", name, arrivalTime, duration);
    }
}

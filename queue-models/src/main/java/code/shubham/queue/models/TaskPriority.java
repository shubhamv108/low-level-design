package code.shubham.queue.models;

public enum TaskPriority {
    HIGH(3D),
    MEDIUM(2D),
    LOW(1D);

    private final Double priority;
    TaskPriority(final Double priority) {
        this.priority = priority;
    }

    public Double getPriority() {
        return priority;
    }
}

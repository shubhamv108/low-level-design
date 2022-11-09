package code.shubham.models.ratelimit.rules;

public enum Plan {
    FREE, BASIC, PROFESSIONAL;

    public static Plan get(String key) {
        return Plan.FREE;
    }
}
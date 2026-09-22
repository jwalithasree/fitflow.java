package recommendation;

import model.enums.FitnessGoal;

/**
 * FitFlow Academic AOP Project
 * Concrete Workout Routine subclass for High-Intensity Interval Training.
 *
 * OOP Concept: Inheritance and Polymorphism.
 * Overrides calculateEstimatedCaloriesBurned() using MET = 9.0 for intense interval circuits.
 */
public class HIITRoutine extends WorkoutRoutine {
    private final int workIntervalSeconds;
    private final int restIntervalSeconds;

    public HIITRoutine(String id, String name, FitnessGoal targetGoal,
                       int targetDurationMinutes, String difficultyLevel,
                       int workIntervalSeconds, int restIntervalSeconds,
                       String warmUpTip, String coolDownTip) {
        super(id, name, targetGoal, targetDurationMinutes, difficultyLevel, warmUpTip, coolDownTip);
        this.workIntervalSeconds = workIntervalSeconds;
        this.restIntervalSeconds = restIntervalSeconds;
    }

    public int getWorkIntervalSeconds() {
        return workIntervalSeconds;
    }

    public int getRestIntervalSeconds() {
        return restIntervalSeconds;
    }

    @Override
    public int calculateEstimatedCaloriesBurned(int durationMinutes, double weightKg) {
        // MET for high intensity circuit/HIIT is ~9.0
        double met = 9.0;
        double caloriesPerMin = (met * 3.5 * weightKg) / 200.0;
        return (int) Math.round(caloriesPerMin * durationMinutes);
    }

    @Override
    public String getRoutineType() {
        return "High-Intensity Interval Training (HIIT)";
    }

    @Override
    public String getIntensityBadge() {
        return "Peak Burn (" + workIntervalSeconds + "s on / " + restIntervalSeconds + "s off)";
    }
}

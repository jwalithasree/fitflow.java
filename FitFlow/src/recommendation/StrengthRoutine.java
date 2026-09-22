package recommendation;

import model.enums.FitnessGoal;

/**
 * FitFlow Academic AOP Project
 * Concrete Workout Routine subclass for Resistance and Strength Training.
 *
 * OOP Concept: Inheritance and Polymorphism.
 * Overrides calculateEstimatedCaloriesBurned() using MET = 6.0 for resistance work.
 */
public class StrengthRoutine extends WorkoutRoutine {
    private final String splitType; // e.g. "Upper Body", "Full Body", "Lower Body"

    public StrengthRoutine(String id, String name, FitnessGoal targetGoal,
                           int targetDurationMinutes, String difficultyLevel,
                           String splitType, String warmUpTip, String coolDownTip) {
        super(id, name, targetGoal, targetDurationMinutes, difficultyLevel, warmUpTip, coolDownTip);
        this.splitType = splitType;
    }

    public String getSplitType() {
        return splitType;
    }

    @Override
    public int calculateEstimatedCaloriesBurned(int durationMinutes, double weightKg) {
        // MET for moderate/vigorous strength training is ~6.0
        double met = 6.0;
        double caloriesPerMin = (met * 3.5 * weightKg) / 200.0;
        return (int) Math.round(caloriesPerMin * durationMinutes);
    }

    @Override
    public String getRoutineType() {
        return "Strength & Hypertrophy";
    }

    @Override
    public String getIntensityBadge() {
        return "High Resistance";
    }
}

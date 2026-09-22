package recommendation;

import model.enums.FitnessGoal;

/**
 * FitFlow Academic AOP Project
 * Concrete Workout Routine subclass for Mobility, Yoga, and Active Recovery.
 *
 * OOP Concept: Inheritance and Polymorphism.
 * Overrides calculateEstimatedCaloriesBurned() using MET = 3.0 for gentle stretching and restorative work.
 */
public class FlexibilityRoutine extends WorkoutRoutine {
    private final String flexibilityType; // e.g., "Full Body Mobility", "Postural Realignment"

    public FlexibilityRoutine(String id, String name, FitnessGoal targetGoal,
                              int targetDurationMinutes, String difficultyLevel,
                              String flexibilityType, String warmUpTip, String coolDownTip) {
        super(id, name, targetGoal, targetDurationMinutes, difficultyLevel, warmUpTip, coolDownTip);
        this.flexibilityType = flexibilityType;
    }

    public String getFlexibilityType() {
        return flexibilityType;
    }

    @Override
    public int calculateEstimatedCaloriesBurned(int durationMinutes, double weightKg) {
        // MET for light stretching/mobility is ~3.0
        double met = 3.0;
        double caloriesPerMin = (met * 3.5 * weightKg) / 200.0;
        return (int) Math.round(caloriesPerMin * durationMinutes);
    }

    @Override
    public String getRoutineType() {
        return "Mobility & Restorative Recovery";
    }

    @Override
    public String getIntensityBadge() {
        return "Low Impact / Restorative";
    }
}

package recommendation;

import model.enums.FitnessGoal;

/**
 * FitFlow Academic AOP Project
 * Concrete Workout Routine subclass for Aerobic and Cardiovascular conditioning.
 *
 * OOP Concept: Inheritance and Polymorphism.
 * Overrides calculateEstimatedCaloriesBurned() using MET = 7.5 for steady aerobic training.
 */
public class CardioRoutine extends WorkoutRoutine {
    private final String cardioFocus; // e.g. "Zone 2 Stamina", "Pacing"

    public CardioRoutine(String id, String name, FitnessGoal targetGoal,
                         int targetDurationMinutes, String difficultyLevel,
                         String cardioFocus, String warmUpTip, String coolDownTip) {
        super(id, name, targetGoal, targetDurationMinutes, difficultyLevel, warmUpTip, coolDownTip);
        this.cardioFocus = cardioFocus;
    }

    public String getCardioFocus() {
        return cardioFocus;
    }

    @Override
    public int calculateEstimatedCaloriesBurned(int durationMinutes, double weightKg) {
        // MET for moderate cardio/jogging/cycling is ~7.5
        double met = 7.5;
        double caloriesPerMin = (met * 3.5 * weightKg) / 200.0;
        return (int) Math.round(caloriesPerMin * durationMinutes);
    }

    @Override
    public String getRoutineType() {
        return "Cardiovascular Conditioning";
    }

    @Override
    public String getIntensityBadge() {
        return "Endurance";
    }
}

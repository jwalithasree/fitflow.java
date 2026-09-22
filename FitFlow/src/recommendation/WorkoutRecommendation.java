package recommendation;

import model.enums.EnergyLevel;

/**
 * FitFlow Academic AOP Project
 * Concrete recommendation subclass encapsulating a customized workout proposal.
 *
 * OOP Concept: Inheritance from abstract class Recommendation.
 */
public class WorkoutRecommendation extends Recommendation {
    private final WorkoutRoutine routine;
    private final int requestedDurationMinutes;
    private final EnergyLevel userEnergyLevel;
    private final int estimatedCaloriesBurned;

    public WorkoutRecommendation(String id, String title, WorkoutRoutine routine,
                                 int requestedDurationMinutes, EnergyLevel userEnergyLevel,
                                 double userWeightKg) {
        super(id, title, "WORKOUT");
        this.routine = routine;
        this.requestedDurationMinutes = requestedDurationMinutes;
        this.userEnergyLevel = userEnergyLevel;
        this.estimatedCaloriesBurned = (routine != null)
                ? routine.calculateEstimatedCaloriesBurned(requestedDurationMinutes, userWeightKg)
                : 0;
    }

    public WorkoutRoutine getRoutine() {
        return routine;
    }

    public int getRequestedDurationMinutes() {
        return requestedDurationMinutes;
    }

    public EnergyLevel getUserEnergyLevel() {
        return userEnergyLevel;
    }

    public int getEstimatedCaloriesBurned() {
        return estimatedCaloriesBurned;
    }

    @Override
    public String generateSummary() {
        if (routine == null) return "No workout routine selected.";
        return String.format("%s: %s (%d mins, %s energy) - Approx. %d kcal burned",
                getTitle(), routine.getName(), requestedDurationMinutes,
                userEnergyLevel.getDisplayName(), estimatedCaloriesBurned);
    }
}

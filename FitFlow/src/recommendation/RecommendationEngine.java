package recommendation;

import calculator.CalorieCalculator;
import model.HealthProfile;
import model.WellnessData;
import model.enums.EnergyLevel;

/**
 * FitFlow Academic AOP Project
 * High-Level Recommendation Engine coordinating domain planners.
 *
 * OOP Concept: Facade Pattern and Aggregation.
 * Aggregates WorkoutPlanner, NutritionPlanner, and WellnessPlanner to provide
 * a clean, unified interface for generating all personalized recommendations.
 */
public class RecommendationEngine {
    private final WorkoutPlanner workoutPlanner;
    private final NutritionPlanner nutritionPlanner;
    private final WellnessPlanner wellnessPlanner;

    public RecommendationEngine() {
        this.workoutPlanner = new WorkoutPlanner();
        this.nutritionPlanner = new NutritionPlanner();
        this.wellnessPlanner = new WellnessPlanner();
    }

    public WorkoutRecommendation getWorkoutRecommendation(HealthProfile profile, int durationMinutes,
                                                         EnergyLevel energyLevel, double weightKg) {
        WorkoutRoutine routine = workoutPlanner.createWorkoutPlan(profile, durationMinutes, energyLevel);
        return new WorkoutRecommendation(
                "REC-WRK-" + System.currentTimeMillis(),
                routine.getName(),
                routine,
                durationMinutes,
                energyLevel,
                weightKg
        );
    }

    public NutritionRecommendation getNutritionRecommendation(HealthProfile profile,
                                                             CalorieCalculator.CalorieResult calorieResult) {
        return nutritionPlanner.createNutritionPlan(profile, calorieResult);
    }

    public WellnessRecommendation getWellnessRecommendation(WellnessData wellnessData) {
        return wellnessPlanner.createWellnessPlan(wellnessData);
    }

    public WorkoutPlanner getWorkoutPlanner() {
        return workoutPlanner;
    }

    public NutritionPlanner getNutritionPlanner() {
        return nutritionPlanner;
    }

    public WellnessPlanner getWellnessPlanner() {
        return wellnessPlanner;
    }
}

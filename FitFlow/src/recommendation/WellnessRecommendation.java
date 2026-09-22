package recommendation;

import model.WellnessData;

/**
 * FitFlow Academic AOP Project
 * Concrete recommendation subclass encapsulating holistic wellness and recovery advice.
 *
 * OOP Concept: Inheritance and Polymorphism.
 */
public class WellnessRecommendation extends Recommendation {
    private final int wellnessScore;
    private final String readinessStatus;
    private final String recoveryAdvice;
    private final String workoutAdjustment;
    private final double sleepHours;

    public WellnessRecommendation(String id, String title, WellnessData wellnessData) {
        super(id, title, "WELLNESS");
        if (wellnessData != null) {
            this.wellnessScore = wellnessData.calculateWellnessScore();
            this.readinessStatus = wellnessData.getReadinessClassification();
            this.recoveryAdvice = wellnessData.getRecoveryAdvice();
            this.workoutAdjustment = wellnessData.getWorkoutAdjustmentAdvice();
            this.sleepHours = wellnessData.getSleepHours();
        } else {
            this.wellnessScore = 75;
            this.readinessStatus = "Baseline Readiness";
            this.recoveryAdvice = "Ensure consistent 7-8 hours of sleep and regular hydration.";
            this.workoutAdjustment = "Maintain normal workout routine.";
            this.sleepHours = 7.5;
        }
    }

    public int getWellnessScore() {
        return wellnessScore;
    }

    public String getReadinessStatus() {
        return readinessStatus;
    }

    public String getRecoveryAdvice() {
        return recoveryAdvice;
    }

    public String getWorkoutAdjustment() {
        return workoutAdjustment;
    }

    public double getSleepHours() {
        return sleepHours;
    }

    @Override
    public String generateSummary() {
        return String.format("%s: Score %d/100 (%s) - %s",
                getTitle(), wellnessScore, readinessStatus, recoveryAdvice);
    }
}

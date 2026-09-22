package model;

import exception.InvalidHealthDataException;
import model.enums.EnergyLevel;
import model.enums.StressLevel;

/**
 * FitFlow Academic AOP Project
 * Model representing daily subjective wellness, sleep, and recovery metrics.
 *
 * OOP Concept: Encapsulation with integrated algorithmic scoring.
 */
public class WellnessData {
    private double sleepHours;
    private StressLevel stressLevel;
    private EnergyLevel energyLevel;
    private String dailyActivity;

    public WellnessData(double sleepHours, StressLevel stressLevel, EnergyLevel energyLevel, String dailyActivity)
            throws InvalidHealthDataException {
        validateAndSetSleepHours(sleepHours);
        this.stressLevel = stressLevel != null ? stressLevel : StressLevel.MEDIUM;
        this.energyLevel = energyLevel != null ? energyLevel : EnergyLevel.MEDIUM;
        this.dailyActivity = (dailyActivity != null) ? dailyActivity.trim() : "";
    }

    public void validateAndSetSleepHours(double sleepHours) throws InvalidHealthDataException {
        if (sleepHours < 0.0 || sleepHours > 24.0) {
            throw new InvalidHealthDataException("sleepHours", sleepHours, "Sleep hours must be between 0 and 24 hours.");
        }
        this.sleepHours = sleepHours;
    }

    public double getSleepHours() {
        return sleepHours;
    }

    public StressLevel getStressLevel() {
        return stressLevel;
    }

    public void setStressLevel(StressLevel stressLevel) {
        this.stressLevel = stressLevel;
    }

    public EnergyLevel getEnergyLevel() {
        return energyLevel;
    }

    public void setEnergyLevel(EnergyLevel energyLevel) {
        this.energyLevel = energyLevel;
    }

    public String getDailyActivity() {
        return dailyActivity;
    }

    public void setDailyActivity(String dailyActivity) {
        this.dailyActivity = dailyActivity;
    }

    /**
     * Calculates an overall holistic wellness readiness score (0 - 100) in Java.
     * Weights:
     * - Sleep: 50% (optimal: 7-9 hours)
     * - Energy: 25%
     * - Stress: 25% (lower stress = higher score)
     */
    public int calculateWellnessScore() {
        double sleepScore;
        if (sleepHours >= 7.0 && sleepHours <= 9.0) {
            sleepScore = 100.0;
        } else if (sleepHours >= 6.0 && sleepHours < 7.0) {
            sleepScore = 80.0;
        } else if (sleepHours > 9.0 && sleepHours <= 10.5) {
            sleepScore = 85.0;
        } else if (sleepHours >= 5.0 && sleepHours < 6.0) {
            sleepScore = 60.0;
        } else {
            sleepScore = 40.0;
        }

        double energyScore;
        switch (energyLevel) {
            case HIGH: energyScore = 100.0; break;
            case MEDIUM: energyScore = 75.0; break;
            case LOW: energyScore = 40.0; break;
            default: energyScore = 70.0;
        }

        double stressScore;
        switch (stressLevel) {
            case LOW: stressScore = 100.0; break;
            case MEDIUM: stressScore = 70.0; break;
            case HIGH: stressScore = 35.0; break;
            default: stressScore = 70.0;
        }

        double total = (sleepScore * 0.50) + (energyScore * 0.25) + (stressScore * 0.25);
        return (int) Math.round(total);
    }

    /**
     * Generates recovery and workout guidance based on Java conditional logic.
     */
    public String getReadinessClassification() {
        int score = calculateWellnessScore();
        if (score >= 80) return "Optimal Readiness";
        if (score >= 60) return "Moderate Readiness";
        return "Rest & Recovery Priority";
    }

    public String getRecoveryAdvice() {
        if (sleepHours < 6.0 && stressLevel == StressLevel.HIGH) {
            return "Critical Recovery Needed: Sleep deficit paired with high stress significantly increases cortisol and injury risk. Prioritize 20 minutes of restorative stretching, drink at least 2.5L water, and aim for an early bedtime tonight.";
        }
        if (sleepHours < 6.5) {
            return "Sleep Deprived: Your neuromuscular response time may be impaired. Reduce training intensity by 20%, focus on hydration, and avoid high-caffeine stimulants after 2 PM.";
        }
        if (stressLevel == StressLevel.HIGH) {
            return "High Mental Stress: Channel physical energy into moderate cardiovascular flow or yoga. Deep diaphragmatic breathing for 5 minutes can help regulate autonomic nervous function.";
        }
        if (energyLevel == EnergyLevel.HIGH && sleepHours >= 7.5) {
            return "Peak Physical Condition: Excellent sleep and high energy! Today is an ideal day for high-intensity training, progressive overload, or pushing personal fitness records.";
        }
        return "Balanced Wellness: Your biometric recovery state is steady. Maintain regular hydration, balanced nutrition, and standard workout schedule.";
    }

    public String getWorkoutAdjustmentAdvice() {
        int score = calculateWellnessScore();
        if (score < 55) {
            return "Recommended: Replace intense lifting with a 20-min light walk, gentle mobility stretches, and early sleep.";
        } else if (score < 75) {
            return "Recommended: Proceed with standard workout plan at RPE 6-7/10. Take extra 30s rest between sets.";
        } else {
            return "Recommended: Green light for full-intensity workout, HIIT, or challenging resistance training!";
        }
    }
}

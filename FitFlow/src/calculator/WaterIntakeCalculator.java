package calculator;

import exception.InvalidHealthDataException;
import model.HealthProfile;
import model.enums.ActivityLevel;

/**
 * FitFlow Academic AOP Project
 * Dedicated Personalized Water Intake Calculator.
 *
 * OOP Concept: Interface Implementation and Encapsulation.
 *
 * Mathematical Formula:
 *   Base Hydration (ml) = Body Weight (kg) * 35 ml
 *   Adjusted Hydration (ml) = Base Hydration * ActivityMultiplier
 *
 * Activity Multipliers:
 *   - Sedentary: 1.0 (baseline)
 *   - Lightly Active: 1.1 (+10% for light sweat & recovery)
 *   - Moderately Active: 1.2 (+20% for moderate exertion)
 *   - Very Active: 1.35 (+35% for heavy training replenishment)
 *
 * Measurements:
 *   - Milliliters (ml)
 *   - Liters (L = ml / 1000.0)
 *   - Standard Glasses (250 ml per glass)
 */
public class WaterIntakeCalculator implements HealthCalculator<WaterIntakeCalculator.WaterResult> {
    public static final int ML_PER_KG = 35;
    public static final int STANDARD_GLASS_ML = 250;

    @Override
    public String getMetricName() {
        return "Daily Hydration Target";
    }

    @Override
    public WaterResult calculate(HealthProfile profile) throws InvalidHealthDataException {
        if (profile == null) {
            throw new InvalidHealthDataException("profile", null, "HealthProfile cannot be null for water calculation.");
        }
        return compute(profile.getWeightKg(), profile.getActivityLevel());
    }

    /**
     * Computes the daily recommended water intake.
     *
     * @param weightKg User's body weight in kg
     * @param activityLevel User's physical activity level
     * @return WaterResult containing ml, liters, and glass counts
     * @throws InvalidHealthDataException if weight is invalid
     */
    public static WaterResult compute(double weightKg, ActivityLevel activityLevel) throws InvalidHealthDataException {
        if (weightKg <= 0.0) {
            throw new InvalidHealthDataException("weightKg", weightKg, "Weight must be greater than zero for hydration calculation.");
        }

        ActivityLevel level = (activityLevel != null) ? activityLevel : ActivityLevel.MODERATELY_ACTIVE;

        // Base intake: 35 ml per kg of body weight
        double baseWaterMl = weightKg * ML_PER_KG;

        // Activity adjustment: multiplier applied to compensate for sweat and metabolic demand
        double adjustedWaterMl = baseWaterMl * level.getWaterMultiplier();

        // Round to nearest 50 ml for practical usability
        int totalMl = (int) (Math.round(adjustedWaterMl / 50.0) * 50);
        double totalLiters = Math.round((totalMl / 1000.0) * 10.0) / 10.0;
        int totalGlasses = (int) Math.ceil((double) totalMl / STANDARD_GLASS_ML);

        return new WaterResult(totalMl, totalLiters, totalGlasses, level);
    }

    /**
     * Value object encapsulating water calculation results.
     */
    public static class WaterResult {
        private final int milliliters;
        private final double liters;
        private final int glasses;
        private final ActivityLevel activityLevel;

        public WaterResult(int milliliters, double liters, int glasses, ActivityLevel activityLevel) {
            this.milliliters = milliliters;
            this.liters = liters;
            this.glasses = glasses;
            this.activityLevel = activityLevel;
        }

        public int getMilliliters() {
            return milliliters;
        }

        public double getLiters() {
            return liters;
        }

        public int getGlasses() {
            return glasses;
        }

        public ActivityLevel getActivityLevel() {
            return activityLevel;
        }

        @Override
        public String toString() {
            return String.format("%d ml (%.1f L) ~ %d glasses [Activity: %s]",
                    milliliters, liters, glasses, activityLevel.getDisplayName());
        }
    }
}

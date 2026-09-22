package calculator;

import exception.InvalidHealthDataException;
import model.HealthProfile;
import model.enums.BMICategory;

/**
 * FitFlow Academic AOP Project
 * Dedicated BMI Calculator implementing the HealthCalculator interface.
 *
 * OOP Concept: Interface Implementation, Encapsulation, and Nested Value Objects.
 *
 * Mathematical Formula:
 *   BMI = Weight (kg) / [Height (m)]^2
 *   Where Height (m) = Height (cm) / 100.0
 *
 * WHO Classifications:
 *   - Underweight: BMI < 18.5
 *   - Normal Weight: 18.5 <= BMI < 25.0
 *   - Overweight: 25.0 <= BMI < 30.0
 *   - Obese: BMI >= 30.0
 */
public class BMICalculator implements HealthCalculator<BMICalculator.BMIResult> {

    @Override
    public String getMetricName() {
        return "Body Mass Index (BMI)";
    }

    @Override
    public BMIResult calculate(HealthProfile profile) throws InvalidHealthDataException {
        if (profile == null) {
            throw new InvalidHealthDataException("profile", null, "HealthProfile cannot be null for BMI calculation.");
        }
        return compute(profile.getWeightKg(), profile.getHeightCm());
    }

    /**
     * Standalone computation method with validation.
     *
     * @param weightKg Weight in kilograms
     * @param heightCm Height in centimeters
     * @return Immutable BMIResult object containing value, category, and health metrics
     * @throws InvalidHealthDataException if inputs are non-positive or irrational
     */
    public static BMIResult compute(double weightKg, double heightCm) throws InvalidHealthDataException {
        if (weightKg <= 0.0) {
            throw new InvalidHealthDataException("weightKg", weightKg, "Weight must be greater than zero.");
        }
        if (heightCm <= 0.0) {
            throw new InvalidHealthDataException("heightCm", heightCm, "Height must be greater than zero.");
        }

        double heightMeters = heightCm / 100.0;
        // Formula: BMI = weight / (height * height)
        double rawBmi = weightKg / (heightMeters * heightMeters);
        double roundedBmi = Math.round(rawBmi * 10.0) / 10.0;

        BMICategory category = BMICategory.classify(roundedBmi);

        // Calculate healthy weight range for this specific height (BMI 18.5 - 24.9)
        double minHealthyWeight = Math.round(18.5 * (heightMeters * heightMeters) * 10.0) / 10.0;
        double maxHealthyWeight = Math.round(24.9 * (heightMeters * heightMeters) * 10.0) / 10.0;

        return new BMIResult(roundedBmi, category, minHealthyWeight, maxHealthyWeight);
    }

    /**
     * Immutable result class encapsulating the BMI analysis output.
     * OOP Concept: Value Object pattern.
     */
    public static class BMIResult {
        private final double bmiValue;
        private final BMICategory category;
        private final double minHealthyWeightKg;
        private final double maxHealthyWeightKg;

        public BMIResult(double bmiValue, BMICategory category, double minHealthyWeightKg, double maxHealthyWeightKg) {
            this.bmiValue = bmiValue;
            this.category = category;
            this.minHealthyWeightKg = minHealthyWeightKg;
            this.maxHealthyWeightKg = maxHealthyWeightKg;
        }

        public double getBmiValue() {
            return bmiValue;
        }

        public BMICategory getCategory() {
            return category;
        }

        public String getCategoryName() {
            return category.getDisplayName();
        }

        public String getColorHex() {
            return category.getColorHex();
        }

        public String getAdvice() {
            return category.getAdvice();
        }

        public double getMinHealthyWeightKg() {
            return minHealthyWeightKg;
        }

        public double getMaxHealthyWeightKg() {
            return maxHealthyWeightKg;
        }

        @Override
        public String toString() {
            return String.format("BMI: %.1f (%s) [Healthy Range: %.1f - %.1f kg]",
                    bmiValue, category.getDisplayName(), minHealthyWeightKg, maxHealthyWeightKg);
        }
    }
}

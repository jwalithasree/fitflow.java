package model.enums;

/**
 * FitFlow Academic AOP Project
 * Enum representing standard World Health Organization (WHO) BMI classifications.
 *
 * OOP Concept: Encapsulation of range logic, classification method, and UI color/status.
 */
public enum BMICategory {
    UNDERWEIGHT("Underweight", 0.0, 18.49, "#38bdf8", "Below standard healthy range. Focus on nutrient-dense calorie surplus."),
    NORMAL("Normal", 18.5, 24.99, "#10b981", "Healthy weight range. Maintain balanced nutrition and functional fitness."),
    OVERWEIGHT("Overweight", 25.0, 29.99, "#f59e0b", "Above standard range. Recommended moderate calorie deficit and active cardio/strength."),
    OBESE("Obese", 30.0, 100.0, "#ef4444", "High health risk range. Prioritize structured cardio, low-impact exercise and deficit.");

    private final String displayName;
    private final double minBmi;
    private final double maxBmi;
    private final String colorHex;
    private final String advice;

    BMICategory(String displayName, double minBmi, double maxBmi, String colorHex, String advice) {
        this.displayName = displayName;
        this.minBmi = minBmi;
        this.maxBmi = maxBmi;
        this.colorHex = colorHex;
        this.advice = advice;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getMinBmi() {
        return minBmi;
    }

    public double getMaxBmi() {
        return maxBmi;
    }

    public String getColorHex() {
        return colorHex;
    }

    public String getAdvice() {
        return advice;
    }

    /**
     * Classifies a numerical BMI value into its official WHO category.
     * Pure Java implementation.
     */
    public static BMICategory classify(double bmi) {
        if (bmi < 18.5) {
            return UNDERWEIGHT;
        } else if (bmi < 25.0) {
            return NORMAL;
        } else if (bmi < 30.0) {
            return OVERWEIGHT;
        } else {
            return OBESE;
        }
    }
}

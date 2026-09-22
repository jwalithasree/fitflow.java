package model.enums;

/**
 * FitFlow Academic AOP Project
 * Enum representing physical activity levels and their corresponding
 * metabolic multiplier (TDEE) and water intake adjustment factor.
 *
 * OOP Concept: Enum with encapsulated state and specific domain behavior.
 */
public enum ActivityLevel {
    SEDENTARY("Sedentary", 1.2, 1.0, "Little to no regular exercise"),
    LIGHTLY_ACTIVE("Lightly Active", 1.375, 1.1, "Light exercise or sports 1-3 days/week"),
    MODERATELY_ACTIVE("Moderately Active", 1.55, 1.2, "Moderate exercise or sports 3-5 days/week"),
    VERY_ACTIVE("Very Active", 1.725, 1.35, "Hard exercise or physical job 6-7 days/week");

    private final String displayName;
    private final double calorieMultiplier;
    private final double waterMultiplier;
    private final String description;

    ActivityLevel(String displayName, double calorieMultiplier, double waterMultiplier, String description) {
        this.displayName = displayName;
        this.calorieMultiplier = calorieMultiplier;
        this.waterMultiplier = waterMultiplier;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getCalorieMultiplier() {
        return calorieMultiplier;
    }

    public double getWaterMultiplier() {
        return waterMultiplier;
    }

    public String getDescription() {
        return description;
    }

    public static ActivityLevel fromString(String text) {
        if (text == null) return MODERATELY_ACTIVE;
        for (ActivityLevel a : ActivityLevel.values()) {
            if (a.name().equalsIgnoreCase(text) || a.displayName.equalsIgnoreCase(text)) {
                return a;
            }
        }
        return MODERATELY_ACTIVE;
    }
}

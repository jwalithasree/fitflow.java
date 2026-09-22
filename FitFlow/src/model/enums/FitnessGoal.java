package model.enums;

/**
 * FitFlow Academic AOP Project
 * Enum representing user fitness goals and corresponding caloric adjustment.
 *
 * OOP Concept: Encapsulation of domain logic within Enum types.
 */
public enum FitnessGoal {
    WEIGHT_LOSS("Weight Loss", -500, "Safe caloric deficit to burn fat while preserving muscle mass"),
    WEIGHT_GAIN("Weight Gain", 450, "Caloric surplus focused on healthy mass and energy"),
    MUSCLE_BUILDING("Muscle Building", 300, "Optimized surplus with high protein for hypertrophy"),
    GENERAL_FITNESS("General Fitness", 0, "Balanced energy intake to boost cardiovascular and functional health"),
    MAINTENANCE("Maintenance", 0, "Caloric equilibrium to maintain current body weight and health");

    private final String displayName;
    private final int calorieAdjustment;
    private final String description;

    FitnessGoal(String displayName, int calorieAdjustment, String description) {
        this.displayName = displayName;
        this.calorieAdjustment = calorieAdjustment;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getCalorieAdjustment() {
        return calorieAdjustment;
    }

    public String getDescription() {
        return description;
    }

    public static FitnessGoal fromString(String text) {
        if (text == null) return GENERAL_FITNESS;
        for (FitnessGoal g : FitnessGoal.values()) {
            if (g.name().equalsIgnoreCase(text) || g.displayName.equalsIgnoreCase(text)) {
                return g;
            }
        }
        return GENERAL_FITNESS;
    }
}

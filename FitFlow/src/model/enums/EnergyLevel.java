package model.enums;

/**
 * FitFlow Academic AOP Project
 * Enum representing user energy levels for adaptive workout planning.
 */
public enum EnergyLevel {
    LOW("Low", 0.8, "Focus on light mobility, walking, and dynamic stretches"),
    MEDIUM("Medium", 1.0, "Moderate pace bodyweight, core stability, and cardio"),
    HIGH("High", 1.25, "Maximum intensity strength, full-body circuits, and HIIT");

    private final String displayName;
    private final double intensityFactor;
    private final String recommendationTheme;

    EnergyLevel(String displayName, double intensityFactor, String recommendationTheme) {
        this.displayName = displayName;
        this.intensityFactor = intensityFactor;
        this.recommendationTheme = recommendationTheme;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getIntensityFactor() {
        return intensityFactor;
    }

    public String getRecommendationTheme() {
        return recommendationTheme;
    }

    public static EnergyLevel fromString(String text) {
        if (text == null) return MEDIUM;
        for (EnergyLevel e : EnergyLevel.values()) {
            if (e.name().equalsIgnoreCase(text) || e.displayName.equalsIgnoreCase(text)) {
                return e;
            }
        }
        return MEDIUM;
    }
}

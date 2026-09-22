package model.enums;

/**
 * FitFlow Academic AOP Project
 * Enum representing subjective stress level for holistic wellness recovery assessment.
 */
public enum StressLevel {
    LOW("Low", "Parasympathetic dominance. Body is well-primed for progressive overload."),
    MEDIUM("Medium", "Moderate autonomic load. Balance active exertion with mindful recovery."),
    HIGH("High", "Elevated cortisol risk. Recommend hydration, breathwork, and restorative sleep.");

    private final String displayName;
    private final String advice;

    StressLevel(String displayName, String advice) {
        this.displayName = displayName;
        this.advice = advice;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAdvice() {
        return advice;
    }

    public static StressLevel fromString(String text) {
        if (text == null) return MEDIUM;
        for (StressLevel s : StressLevel.values()) {
            if (s.name().equalsIgnoreCase(text) || s.displayName.equalsIgnoreCase(text)) {
                return s;
            }
        }
        return MEDIUM;
    }
}

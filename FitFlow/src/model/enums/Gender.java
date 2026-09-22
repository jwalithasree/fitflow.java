package model.enums;

/**
 * FitFlow Academic AOP Project
 * Enum representing biological gender for metabolic rate calculations.
 *
 * OOP Concept: Enum with encapsulation and behavior.
 * Used in Mifflin-St Jeor BMR calculation formula.
 */
public enum Gender {
    MALE("Male", 5.0),
    FEMALE("Female", -161.0),
    OTHER("Other", -78.0); // Neutral average metabolic baseline

    private final String displayName;
    private final double bmrOffset;

    Gender(String displayName, double bmrOffset) {
        this.displayName = displayName;
        this.bmrOffset = bmrOffset;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getBmrOffset() {
        return bmrOffset;
    }

    public static Gender fromString(String text) {
        if (text == null) return MALE;
        for (Gender g : Gender.values()) {
            if (g.name().equalsIgnoreCase(text) || g.displayName.equalsIgnoreCase(text)) {
                return g;
            }
        }
        return MALE;
    }
}

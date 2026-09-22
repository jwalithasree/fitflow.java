package model;

/**
 * FitFlow Academic AOP Project
 * Model representing an individual exercise item within a workout routine.
 *
 * OOP Concept: Encapsulation with immutable or validated fields.
 */
public class Exercise {
    private final String name;
    private final String category; // e.g., "Compound Strength", "Core", "Mobility", "Cardio"
    private final int sets;
    private final String repsOrDuration; // e.g. "12 reps", "45 secs"
    private final String targetMuscle;
    private final String instructions;

    public Exercise(String name, String category, int sets, String repsOrDuration, String targetMuscle, String instructions) {
        this.name = name;
        this.category = category;
        this.sets = sets;
        this.repsOrDuration = repsOrDuration;
        this.targetMuscle = targetMuscle;
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getSets() {
        return sets;
    }

    public String getRepsOrDuration() {
        return repsOrDuration;
    }

    public String getTargetMuscle() {
        return targetMuscle;
    }

    public String getInstructions() {
        return instructions;
    }

    @Override
    public String toString() {
        return name + " (" + sets + " sets x " + repsOrDuration + ") - " + targetMuscle;
    }
}

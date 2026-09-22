package recommendation;

import model.Exercise;
import model.enums.FitnessGoal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * FitFlow Academic AOP Project
 * Base Abstract Class for physical workout routines.
 *
 * OOP Concept: Abstraction, Inheritance, Polymorphism, and Collections.
 * Subclasses represent specific exercise modalities and override calculation algorithms.
 */
public abstract class WorkoutRoutine {
    private final String id;
    private final String name;
    private final FitnessGoal targetGoal;
    private final int targetDurationMinutes;
    private final String difficultyLevel;
    private final List<Exercise> exercises;
    private final String warmUpTip;
    private final String coolDownTip;

    public WorkoutRoutine(String id, String name, FitnessGoal targetGoal,
                          int targetDurationMinutes, String difficultyLevel,
                          String warmUpTip, String coolDownTip) {
        this.id = id;
        this.name = name;
        this.targetGoal = targetGoal;
        this.targetDurationMinutes = targetDurationMinutes;
        this.difficultyLevel = difficultyLevel;
        this.exercises = new ArrayList<>();
        this.warmUpTip = warmUpTip;
        this.coolDownTip = coolDownTip;
    }

    public void addExercise(Exercise exercise) {
        if (exercise != null) {
            this.exercises.add(exercise);
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public FitnessGoal getTargetGoal() {
        return targetGoal;
    }

    public int getTargetDurationMinutes() {
        return targetDurationMinutes;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public List<Exercise> getExercises() {
        // OOP Concept: Defensive Copying to protect internal encapsulation
        return Collections.unmodifiableList(exercises);
    }

    public String getWarmUpTip() {
        return warmUpTip;
    }

    public String getCoolDownTip() {
        return coolDownTip;
    }

    /**
     * Polymorphic calculation of estimated calorie burn.
     * Uses Metabolic Equivalent of Task (MET) formula:
     * Calories = (MET * 3.5 * weightKg / 200) * durationMinutes
     *
     * OOP Concept: Polymorphism. Each subclass provides its own MET coefficient.
     */
    public abstract int calculateEstimatedCaloriesBurned(int durationMinutes, double weightKg);

    /**
     * Abstract discriminator returning the training modality type.
     */
    public abstract String getRoutineType();

    /**
     * Abstract visual style / intensity badge.
     */
    public abstract String getIntensityBadge();
}

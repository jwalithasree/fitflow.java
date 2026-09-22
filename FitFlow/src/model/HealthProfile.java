package model;

import exception.InvalidHealthDataException;
import model.enums.ActivityLevel;
import model.enums.FitnessGoal;
import model.enums.Gender;

/**
 * FitFlow Academic AOP Project
 * Model representing a user's biometric and lifestyle health profile.
 *
 * OOP Concept: Encapsulation.
 * All fields are private, initialized via constructor or validated setters.
 * Invariants are strictly checked before mutating state, preventing invalid data.
 */
public class HealthProfile {
    private int age;
    private Gender gender;
    private double heightCm;
    private double weightKg;
    private ActivityLevel activityLevel;
    private FitnessGoal fitnessGoal;

    public HealthProfile(int age, Gender gender, double heightCm, double weightKg,
                         ActivityLevel activityLevel, FitnessGoal fitnessGoal)
            throws InvalidHealthDataException {
        validateAndSetAge(age);
        setGender(gender);
        validateAndSetHeight(heightCm);
        validateAndSetWeight(weightKg);
        setActivityLevel(activityLevel);
        setFitnessGoal(fitnessGoal);
    }

    public void validateAndSetAge(int age) throws InvalidHealthDataException {
        if (age < 10 || age > 120) {
            throw new InvalidHealthDataException("age", age, "Age must be between 10 and 120 years.");
        }
        this.age = age;
    }

    public void validateAndSetHeight(double heightCm) throws InvalidHealthDataException {
        if (heightCm < 50.0 || heightCm > 260.0) {
            throw new InvalidHealthDataException("heightCm", heightCm, "Height must be between 50 cm and 260 cm.");
        }
        this.heightCm = heightCm;
    }

    public void validateAndSetWeight(double weightKg) throws InvalidHealthDataException {
        if (weightKg < 20.0 || weightKg > 350.0) {
            throw new InvalidHealthDataException("weightKg", weightKg, "Weight must be between 20 kg and 350 kg.");
        }
        this.weightKg = weightKg;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender != null ? gender : Gender.MALE;
    }

    public void setGender(Gender gender) {
        this.gender = gender != null ? gender : Gender.MALE;
    }

    public double getHeightCm() {
        return heightCm;
    }

    public double getHeightMeters() {
        return heightCm / 100.0;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel != null ? activityLevel : ActivityLevel.MODERATELY_ACTIVE;
    }

    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel != null ? activityLevel : ActivityLevel.MODERATELY_ACTIVE;
    }

    public FitnessGoal getFitnessGoal() {
        return fitnessGoal != null ? fitnessGoal : FitnessGoal.GENERAL_FITNESS;
    }

    public void setFitnessGoal(FitnessGoal fitnessGoal) {
        this.fitnessGoal = fitnessGoal != null ? fitnessGoal : FitnessGoal.GENERAL_FITNESS;
    }

    @Override
    public String toString() {
        return "HealthProfile{" +
                "age=" + age +
                ", gender=" + gender +
                ", heightCm=" + heightCm +
                ", weightKg=" + weightKg +
                ", activityLevel=" + activityLevel +
                ", fitnessGoal=" + fitnessGoal +
                '}';
    }
}

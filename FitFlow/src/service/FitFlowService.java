package service;

import calculator.BMICalculator;
import calculator.CalorieCalculator;
import calculator.WaterIntakeCalculator;
import exception.InvalidHealthDataException;
import model.Exercise;
import model.HealthProfile;
import model.HydrationLog;
import model.User;
import model.WellnessData;
import model.enums.*;
import recommendation.*;

import java.util.*;

/**
 * FitFlow Academic AOP Project
 * Central Application Service (Facade Pattern).
 *
 * OOP Concept: Facade Pattern, Encapsulation, and Layered Architecture.
 * This class coordinates model state, invokes dedicated calculation engines,
 * delegates recommendations, and supplies data to the HTTP controller layer.
 */
public class FitFlowService {
    private static FitFlowService instance;

    private User currentUser;
    private WaterTracker waterTracker;
    private WellnessData currentWellness;
    private final BMICalculator bmiCalculator;
    private final WaterIntakeCalculator waterCalculator;
    private final CalorieCalculator calorieCalculator;
    private final RecommendationEngine recommendationEngine;

    public FitFlowService() {
        this.bmiCalculator = new BMICalculator();
        this.waterCalculator = new WaterIntakeCalculator();
        this.calorieCalculator = new CalorieCalculator();
        this.recommendationEngine = new RecommendationEngine();

        // Initialize with default standard demo profile for immediate out-of-the-box exploration
        try {
            HealthProfile initialProfile = new HealthProfile(
                    21,
                    Gender.MALE,
                    175.0, // 175 cm
                    68.0,  // 68 kg
                    ActivityLevel.MODERATELY_ACTIVE,
                    FitnessGoal.MUSCLE_BUILDING
            );
            this.currentUser = new User("USR-101", "Alex Rivers", initialProfile);

            // Calculate initial water target
            WaterIntakeCalculator.WaterResult waterRes = waterCalculator.calculate(initialProfile);
            this.waterTracker = new WaterTracker(waterRes.getMilliliters());

            // Initialize default wellness state
            this.currentWellness = new WellnessData(7.5, StressLevel.LOW, EnergyLevel.HIGH, "College lectures and 5,000 brisk steps");
        } catch (InvalidHealthDataException e) {
            System.err.println("Default profile initialization error: " + e.getMessage());
        }
    }

    /**
     * Singleton Accessor for the Service instance.
     */
    public static synchronized FitFlowService getInstance() {
        if (instance == null) {
            instance = new FitFlowService();
        }
        return instance;
    }

    /**
     * Updates or creates the user's profile with strict Java validation.
     */
    public synchronized void updateProfile(String name, int age, String genderStr,
                                           double heightCm, double weightKg,
                                           String activityStr, String goalStr) throws InvalidHealthDataException {
        Gender gender = Gender.fromString(genderStr);
        ActivityLevel activity = ActivityLevel.fromString(activityStr);
        FitnessGoal goal = FitnessGoal.fromString(goalStr);

        HealthProfile profile = new HealthProfile(age, gender, heightCm, weightKg, activity, goal);

        if (currentUser == null) {
            currentUser = new User("USR-" + System.currentTimeMillis() % 10000, name, profile);
        } else {
            currentUser.setName(name);
            currentUser.setHealthProfile(profile);
        }

        // Automatically update the water target based on newly calculated needs
        WaterIntakeCalculator.WaterResult waterRes = waterCalculator.calculate(profile);
        if (waterTracker != null) {
            waterTracker.setTargetMl(waterRes.getMilliliters());
        } else {
            waterTracker = new WaterTracker(waterRes.getMilliliters());
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public HealthProfile getProfile() {
        return (currentUser != null) ? currentUser.getHealthProfile() : null;
    }

    /**
     * Calculates BMI in Java.
     */
    public BMICalculator.BMIResult getBMI() throws InvalidHealthDataException {
        HealthProfile p = getProfile();
        return bmiCalculator.calculate(p);
    }

    /**
     * Calculates Water Requirement in Java.
     */
    public WaterIntakeCalculator.WaterResult getWaterRequirement() throws InvalidHealthDataException {
        HealthProfile p = getProfile();
        return waterCalculator.calculate(p);
    }

    /**
     * Calculates Calorie Requirement (BMR + TDEE + Goal) in Java.
     */
    public CalorieCalculator.CalorieResult getCalorieRequirement() throws InvalidHealthDataException {
        HealthProfile p = getProfile();
        return calorieCalculator.calculate(p);
    }

    /**
     * Hydration Tracker state and operations.
     */
    public WaterTracker getWaterTracker() {
        return waterTracker;
    }

    public synchronized void logGlass() throws InvalidHealthDataException {
        if (waterTracker != null) {
            waterTracker.logGlass();
        }
    }

    public synchronized void logCustomWater(int ml) throws InvalidHealthDataException {
        if (waterTracker != null) {
            waterTracker.logCustomAmount(ml);
        }
    }

    public synchronized void resetWater() {
        if (waterTracker != null) {
            waterTracker.resetDaily();
        }
    }

    public synchronized void setWaterTarget(int ml) throws InvalidHealthDataException {
        if (waterTracker != null) {
            waterTracker.setTargetMl(ml);
        }
    }

    /**
     * Wellness state and updates.
     */
    public WellnessData getWellnessData() {
        return currentWellness;
    }

    public synchronized void updateWellness(double sleepHours, String stressStr, String energyStr, String dailyActivity)
            throws InvalidHealthDataException {
        StressLevel stress = StressLevel.fromString(stressStr);
        EnergyLevel energy = EnergyLevel.fromString(energyStr);
        this.currentWellness = new WellnessData(sleepHours, stress, energy, dailyActivity);
    }

    /**
     * Generates a tailored workout recommendation.
     */
    public WorkoutRecommendation generateWorkout(int minutes, String energyStr) {
        EnergyLevel energy = EnergyLevel.fromString(energyStr);
        HealthProfile p = getProfile();
        double weight = (p != null) ? p.getWeightKg() : 70.0;
        return recommendationEngine.getWorkoutRecommendation(p, minutes, energy, weight);
    }

    /**
     * Generates tailored nutrition recommendations.
     */
    public NutritionRecommendation generateNutrition() throws InvalidHealthDataException {
        HealthProfile p = getProfile();
        CalorieCalculator.CalorieResult cal = getCalorieRequirement();
        return recommendationEngine.getNutritionRecommendation(p, cal);
    }

    /**
     * Generates tailored wellness recommendations.
     */
    public WellnessRecommendation generateWellness() {
        return recommendationEngine.getWellnessRecommendation(currentWellness);
    }

    /**
     * Compiles a comprehensive Fitness Dashboard summary object.
     * All calculations are evaluated in Java.
     */
    public Map<String, Object> getDashboardData() throws InvalidHealthDataException {
        Map<String, Object> data = new LinkedHashMap<>();
        HealthProfile p = getProfile();

        if (currentUser != null) {
            data.put("userName", currentUser.getName());
            data.put("userId", currentUser.getId());
        } else {
            data.put("userName", "Guest Athlete");
            data.put("userId", "GUEST");
        }

        if (p != null) {
            BMICalculator.BMIResult bmi = getBMI();
            WaterIntakeCalculator.WaterResult water = getWaterRequirement();
            CalorieCalculator.CalorieResult cal = getCalorieRequirement();

            // Profile info
            data.put("age", p.getAge());
            data.put("gender", p.getGender().getDisplayName());
            data.put("heightCm", p.getHeightCm());
            data.put("weightKg", p.getWeightKg());
            data.put("activityLevel", p.getActivityLevel().getDisplayName());
            data.put("fitnessGoal", p.getFitnessGoal().getDisplayName());
            data.put("goalDescription", p.getFitnessGoal().getDescription());

            // BMI card data
            Map<String, Object> bmiMap = new LinkedHashMap<>();
            bmiMap.put("value", bmi.getBmiValue());
            bmiMap.put("category", bmi.getCategoryName());
            bmiMap.put("colorHex", bmi.getColorHex());
            bmiMap.put("advice", bmi.getAdvice());
            bmiMap.put("minHealthyWeight", bmi.getMinHealthyWeightKg());
            bmiMap.put("maxHealthyWeight", bmi.getMaxHealthyWeightKg());
            data.put("bmi", bmiMap);

            // Water card data
            Map<String, Object> waterMap = new LinkedHashMap<>();
            waterMap.put("targetMl", water.getMilliliters());
            waterMap.put("targetLiters", water.getLiters());
            waterMap.put("targetGlasses", water.getGlasses());
            if (waterTracker != null) {
                HydrationLog log = waterTracker.getLog();
                waterMap.put("consumedMl", log.getConsumedMl());
                waterMap.put("consumedLiters", log.getConsumedLiters());
                waterMap.put("remainingMl", log.getRemainingMl());
                waterMap.put("remainingLiters", log.getRemainingLiters());
                waterMap.put("progressPercent", log.getProgressPercentage());
                waterMap.put("glassesConsumed", log.getGlassesConsumed());
                waterMap.put("glassesRemaining", log.getGlassesRemaining());
            }
            data.put("water", waterMap);

            // Calorie card data
            Map<String, Object> calMap = new LinkedHashMap<>();
            calMap.put("bmr", cal.getBmr());
            calMap.put("maintenance", cal.getMaintenanceCalories());
            calMap.put("targetCalories", cal.getTargetCalories());
            calMap.put("proteinGrams", cal.getProteinGrams());
            calMap.put("carbGrams", cal.getCarbGrams());
            calMap.put("fatGrams", cal.getFatGrams());
            data.put("calories", calMap);

            // Quick Wellness overview
            if (currentWellness != null) {
                Map<String, Object> wellMap = new LinkedHashMap<>();
                wellMap.put("score", currentWellness.calculateWellnessScore());
                wellMap.put("readiness", currentWellness.getReadinessClassification());
                wellMap.put("sleepHours", currentWellness.getSleepHours());
                wellMap.put("stress", currentWellness.getStressLevel().getDisplayName());
                wellMap.put("energy", currentWellness.getEnergyLevel().getDisplayName());
                data.put("wellness", wellMap);
            }
        }

        return data;
    }
}

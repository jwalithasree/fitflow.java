package calculator;

import exception.InvalidHealthDataException;
import model.HealthProfile;
import model.enums.ActivityLevel;
import model.enums.FitnessGoal;
import model.enums.Gender;

/**
 * FitFlow Academic AOP Project
 * Dedicated Basal Metabolic Rate (BMR) and Total Daily Energy Expenditure (TDEE) Calculator.
 *
 * OOP Concept: Interface Implementation, Encapsulation, and Scientific Formula Modeling.
 *
 * Scientific Formula: The Mifflin-St Jeor Equation (Widely considered the clinical gold standard)
 *   Men:   BMR = 10 * weight (kg) + 6.25 * height (cm) - 5 * age (y) + 5
 *   Women: BMR = 10 * weight (kg) + 6.25 * height (cm) - 5 * age (y) - 161
 *   Other: BMR = 10 * weight (kg) + 6.25 * height (cm) - 5 * age (y) - 78
 *
 * TDEE (Maintenance Calories):
 *   TDEE = BMR * ActivityMultiplier
 *
 * Goal Adjustments:
 *   - Weight Loss:      TDEE - 500 kcal (approx. 0.5 kg fat loss / week)
 *   - Weight Gain:      TDEE + 450 kcal (healthy mass gain surplus)
 *   - Muscle Building:  TDEE + 300 kcal (lean hypertrophy surplus)
 *   - Maintenance:      TDEE (0 adjustment)
 *   - General Fitness:  TDEE (0 adjustment)
 *
 * Disclaimer: Academic and general wellness estimation, not clinical prescription.
 */
public class CalorieCalculator implements HealthCalculator<CalorieCalculator.CalorieResult> {

    @Override
    public String getMetricName() {
        return "Daily Caloric & Energy Target";
    }

    @Override
    public CalorieResult calculate(HealthProfile profile) throws InvalidHealthDataException {
        if (profile == null) {
            throw new InvalidHealthDataException("profile", null, "HealthProfile cannot be null for calorie calculation.");
        }
        return compute(
                profile.getAge(),
                profile.getGender(),
                profile.getHeightCm(),
                profile.getWeightKg(),
                profile.getActivityLevel(),
                profile.getFitnessGoal()
        );
    }

    /**
     * Computes BMR, TDEE, adjusted goal calories, and macronutrient targets.
     */
    public static CalorieResult compute(int age, Gender gender, double heightCm, double weightKg,
                                        ActivityLevel activityLevel, FitnessGoal goal) throws InvalidHealthDataException {
        if (age < 10 || age > 120) {
            throw new InvalidHealthDataException("age", age, "Age must be between 10 and 120.");
        }
        if (heightCm <= 0.0) {
            throw new InvalidHealthDataException("heightCm", heightCm, "Height must be greater than zero.");
        }
        if (weightKg <= 0.0) {
            throw new InvalidHealthDataException("weightKg", weightKg, "Weight must be greater than zero.");
        }

        Gender g = (gender != null) ? gender : Gender.MALE;
        ActivityLevel act = (activityLevel != null) ? activityLevel : ActivityLevel.MODERATELY_ACTIVE;
        FitnessGoal fitGoal = (goal != null) ? goal : FitnessGoal.GENERAL_FITNESS;

        // 1. Calculate Basal Metabolic Rate (BMR) using Mifflin-St Jeor Formula
        double bmr = (10.0 * weightKg) + (6.25 * heightCm) - (5.0 * age) + g.getBmrOffset();
        int roundedBmr = (int) Math.round(bmr);

        // 2. Calculate Total Daily Energy Expenditure (TDEE / Maintenance)
        double tdee = bmr * act.getCalorieMultiplier();
        int maintenanceCalories = (int) Math.round(tdee);

        // 3. Apply fitness goal caloric adjustment
        int targetCalories = maintenanceCalories + fitGoal.getCalorieAdjustment();

        // Safety floor: Never recommend below 1200 kcal for women or 1500 kcal for men to avoid starvation mode
        int floor = (g == Gender.FEMALE) ? 1200 : 1500;
        if (targetCalories < floor) {
            targetCalories = floor;
        }

        // 4. Calculate Recommended Macronutrient Split based on Goal
        // Protein: 4 kcal/g, Carbs: 4 kcal/g, Fats: 9 kcal/g
        double proteinRatio;
        double carbRatio;
        double fatRatio;

        switch (fitGoal) {
            case WEIGHT_LOSS:
                proteinRatio = 0.35; // 35% protein to preserve lean muscle
                carbRatio = 0.35;    // 35% carbs
                fatRatio = 0.30;     // 30% healthy fats
                break;
            case MUSCLE_BUILDING:
                proteinRatio = 0.30; // 30% protein
                carbRatio = 0.50;    // 50% carbs for workout glycogen
                fatRatio = 0.20;     // 20% fats
                break;
            case WEIGHT_GAIN:
                proteinRatio = 0.25; // 25% protein
                carbRatio = 0.55;    // 55% carbs
                fatRatio = 0.20;     // 20% fats
                break;
            case GENERAL_FITNESS:
            case MAINTENANCE:
            default:
                proteinRatio = 0.25; // 25% protein
                carbRatio = 0.50;    // 50% carbs
                fatRatio = 0.25;     // 25% fats
                break;
        }

        int proteinGrams = (int) Math.round((targetCalories * proteinRatio) / 4.0);
        int carbGrams = (int) Math.round((targetCalories * carbRatio) / 4.0);
        int fatGrams = (int) Math.round((targetCalories * fatRatio) / 9.0);

        return new CalorieResult(
                roundedBmr,
                maintenanceCalories,
                targetCalories,
                fitGoal,
                act,
                proteinGrams,
                carbGrams,
                fatGrams
        );
    }

    /**
     * Value object encapsulating all computed calorie and macronutrient data.
     */
    public static class CalorieResult {
        private final int bmr;
        private final int maintenanceCalories;
        private final int targetCalories;
        private final FitnessGoal goal;
        private final ActivityLevel activityLevel;
        private final int proteinGrams;
        private final int carbGrams;
        private final int fatGrams;

        public CalorieResult(int bmr, int maintenanceCalories, int targetCalories,
                             FitnessGoal goal, ActivityLevel activityLevel,
                             int proteinGrams, int carbGrams, int fatGrams) {
            this.bmr = bmr;
            this.maintenanceCalories = maintenanceCalories;
            this.targetCalories = targetCalories;
            this.goal = goal;
            this.activityLevel = activityLevel;
            this.proteinGrams = proteinGrams;
            this.carbGrams = carbGrams;
            this.fatGrams = fatGrams;
        }

        public int getBmr() {
            return bmr;
        }

        public int getMaintenanceCalories() {
            return maintenanceCalories;
        }

        public int getTargetCalories() {
            return targetCalories;
        }

        public FitnessGoal getGoal() {
            return goal;
        }

        public ActivityLevel getActivityLevel() {
            return activityLevel;
        }

        public int getProteinGrams() {
            return proteinGrams;
        }

        public int getCarbGrams() {
            return carbGrams;
        }

        public int getFatGrams() {
            return fatGrams;
        }

        @Override
        public String toString() {
            return String.format("Target: %d kcal (BMR: %d, Maint: %d) [P: %dg, C: %dg, F: %dg]",
                    targetCalories, bmr, maintenanceCalories, proteinGrams, carbGrams, fatGrams);
        }
    }
}

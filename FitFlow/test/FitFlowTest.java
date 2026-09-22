package test;

import calculator.BMICalculator;
import calculator.CalorieCalculator;
import calculator.WaterIntakeCalculator;
import exception.InvalidHealthDataException;
import model.Exercise;
import model.HealthProfile;
import model.HydrationLog;
import model.WellnessData;
import model.enums.*;
import recommendation.*;
import service.FitFlowService;
import service.WaterTracker;
import util.JsonUtil;

import java.util.Map;

/**
 * FitFlow Academic AOP Project
 * Comprehensive Automated Verification Test Suite.
 *
 * Verifies all mathematical formulas, OOP polymorphism, custom exceptions,
 * and service layer integrations without needing external test frameworks like JUnit.
 */
public class FitFlowTest {
    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("            FITFLOW ACADEMIC AOP TEST SUITE                   ");
        System.out.println("===============================================================\n");

        testBMICalculator();
        testBMIException();
        testWaterIntakeCalculator();
        testCalorieCalculator();
        testWorkoutPolymorphism();
        testWaterTracker();
        testWellnessScoring();
        testJsonUtility();
        testFitFlowServiceFacade();

        System.out.println("\n---------------------------------------------------------------");
        System.out.println(String.format("TEST RESULTS: %d Passed, %d Failed", testsPassed, testsFailed));
        System.out.println("---------------------------------------------------------------");

        if (testsFailed > 0) {
            System.err.println("FAILURES DETECTED! Review test log above.");
            System.exit(1);
        } else {
            System.out.println("ALL FITFLOW BACKEND TESTS COMPLETED SUCCESSFULLY!\n");
        }
    }

    private static void assertTrue(String testName, boolean condition) {
        if (condition) {
            System.out.println("[PASS] " + testName);
            testsPassed++;
        } else {
            System.err.println("[FAIL] " + testName);
            testsFailed++;
        }
    }

    private static void testBMICalculator() {
        try {
            // Test 1: 70kg, 175cm -> BMI = 70 / (1.75 * 1.75) = 22.857... ~ 22.9 (NORMAL)
            BMICalculator.BMIResult normal = BMICalculator.compute(70.0, 175.0);
            assertTrue("BMI normal calculation value", Math.abs(normal.getBmiValue() - 22.9) < 0.1);
            assertTrue("BMI normal classification", normal.getCategory() == BMICategory.NORMAL);

            // Test 2: Underweight (45kg, 175cm -> BMI ~ 14.7)
            BMICalculator.BMIResult under = BMICalculator.compute(45.0, 175.0);
            assertTrue("BMI underweight classification", under.getCategory() == BMICategory.UNDERWEIGHT);

            // Test 3: Overweight (85kg, 175cm -> BMI ~ 27.8)
            BMICalculator.BMIResult over = BMICalculator.compute(85.0, 175.0);
            assertTrue("BMI overweight classification", over.getCategory() == BMICategory.OVERWEIGHT);

            // Test 4: Obese (100kg, 170cm -> BMI ~ 34.6)
            BMICalculator.BMIResult obese = BMICalculator.compute(100.0, 170.0);
            assertTrue("BMI obese classification", obese.getCategory() == BMICategory.OBESE);
        } catch (Exception e) {
            assertTrue("BMI test failed with exception: " + e.getMessage(), false);
        }
    }

    private static void testBMIException() {
        boolean caught = false;
        try {
            // Negative weight should throw InvalidHealthDataException
            BMICalculator.compute(-50.0, 175.0);
        } catch (InvalidHealthDataException e) {
            caught = true;
        }
        assertTrue("BMI negative weight throws InvalidHealthDataException", caught);
    }

    private static void testWaterIntakeCalculator() {
        try {
            // 70kg, Moderately active (multiplier 1.2): 70 * 35 * 1.2 = 2940 ml ~ 2950 ml
            WaterIntakeCalculator.WaterResult res = WaterIntakeCalculator.compute(70.0, ActivityLevel.MODERATELY_ACTIVE);
            assertTrue("Water intake ml is positive and reasonable", res.getMilliliters() >= 2800 && res.getMilliliters() <= 3100);
            assertTrue("Water intake liters conversion", res.getLiters() > 2.5 && res.getLiters() < 3.2);
            assertTrue("Water intake glasses count (approx 250ml)", res.getGlasses() >= 11 && res.getGlasses() <= 13);
        } catch (Exception e) {
            assertTrue("Water calculator failed: " + e.getMessage(), false);
        }
    }

    private static void testCalorieCalculator() {
        try {
            // Male, 22 yo, 175cm, 70kg, Moderately active, Muscle building
            // BMR = 10*70 + 6.25*175 - 5*22 + 5 = 700 + 1093.75 - 110 + 5 = 1688.75 ~ 1689
            // TDEE = 1688.75 * 1.55 = 2617.56 ~ 2618
            // Muscle Building (+300) = ~ 2918 kcal
            CalorieCalculator.CalorieResult res = CalorieCalculator.compute(
                    22, Gender.MALE, 175.0, 70.0,
                    ActivityLevel.MODERATELY_ACTIVE,
                    FitnessGoal.MUSCLE_BUILDING
            );

            assertTrue("Calorie BMR calculation", Math.abs(res.getBmr() - 1689) <= 2);
            assertTrue("Calorie Maintenance TDEE calculation", Math.abs(res.getMaintenanceCalories() - 2618) <= 5);
            assertTrue("Calorie Target with +300 surplus", res.getTargetCalories() == res.getMaintenanceCalories() + 300);
            assertTrue("Calorie Macronutrients sum up logically",
                    (res.getProteinGrams() * 4 + res.getCarbGrams() * 4 + res.getFatGrams() * 9) > 0);
        } catch (Exception e) {
            assertTrue("Calorie calculator failed: " + e.getMessage(), false);
        }
    }

    private static void testWorkoutPolymorphism() {
        try {
            WorkoutPlanner planner = new WorkoutPlanner();
            HealthProfile profile = new HealthProfile(21, Gender.MALE, 175.0, 70.0,
                    ActivityLevel.MODERATELY_ACTIVE, FitnessGoal.MUSCLE_BUILDING);

            // 1. Low energy -> FlexibilityRoutine
            WorkoutRoutine lowRoutine = planner.createWorkoutPlan(profile, 10, EnergyLevel.LOW);
            assertTrue("Low energy generates FlexibilityRoutine", lowRoutine instanceof FlexibilityRoutine);
            int calFlex = lowRoutine.calculateEstimatedCaloriesBurned(10, 70.0);
            assertTrue("Flexibility routine estimated calories is non-zero", calFlex > 0);

            // 2. High energy -> HIITRoutine or StrengthRoutine
            WorkoutRoutine highRoutine = planner.createWorkoutPlan(profile, 45, EnergyLevel.HIGH);
            assertTrue("High energy generates valid WorkoutRoutine", highRoutine != null);
            int calHigh = highRoutine.calculateEstimatedCaloriesBurned(45, 70.0);
            assertTrue("High energy calorie burn > low energy calorie burn", calHigh > calFlex);

            // 3. Polymorphism: Calling common methods on abstract class
            assertTrue("Routine contains exercises", !highRoutine.getExercises().isEmpty());
            for (Exercise ex : highRoutine.getExercises()) {
                assertTrue("Exercise has valid name and sets", ex.getName() != null && ex.getSets() > 0);
            }
        } catch (Exception e) {
            assertTrue("Workout polymorphism test failed: " + e.getMessage(), false);
        }
    }

    private static void testWaterTracker() {
        try {
            WaterTracker tracker = new WaterTracker(2000);
            HydrationLog log = tracker.getLog();
            assertTrue("Initial consumed is 0", log.getConsumedMl() == 0);
            assertTrue("Initial remaining equals target", log.getRemainingMl() == 2000);

            tracker.logGlass(); // +250ml
            assertTrue("Consumed after 1 glass", log.getConsumedMl() == 250);
            assertTrue("Remaining after 1 glass", log.getRemainingMl() == 1750);
            assertTrue("Progress percentage updated", Math.abs(log.getProgressPercentage() - 12.5) < 0.1);

            tracker.logCustomAmount(750); // +750ml = 1000ml total
            assertTrue("Consumed after custom amount", log.getConsumedMl() == 1000);
            assertTrue("Progress percentage is 50%", Math.abs(log.getProgressPercentage() - 50.0) < 0.1);

            tracker.resetDaily();
            assertTrue("Reset clears consumed water", log.getConsumedMl() == 0);
        } catch (Exception e) {
            assertTrue("WaterTracker test failed: " + e.getMessage(), false);
        }
    }

    private static void testWellnessScoring() {
        try {
            // Optimal conditions: 8h sleep, low stress, high energy -> high score
            WellnessData optimal = new WellnessData(8.0, StressLevel.LOW, EnergyLevel.HIGH, "Good day");
            assertTrue("Optimal wellness score >= 90", optimal.calculateWellnessScore() >= 90);

            // Deprived conditions: 4h sleep, high stress, low energy -> low score
            WellnessData poor = new WellnessData(4.0, StressLevel.HIGH, EnergyLevel.LOW, "Exam week");
            assertTrue("Poor wellness score <= 50", poor.calculateWellnessScore() <= 50);
            assertTrue("Readiness prioritizes recovery", poor.getReadinessClassification().contains("Recovery"));
        } catch (Exception e) {
            assertTrue("Wellness scoring test failed: " + e.getMessage(), false);
        }
    }

    private static void testJsonUtility() {
        String testJson = "{\"name\":\"Alex\",\"age\":22,\"height\":175.5,\"active\":true}";
        Map<String, Object> map = JsonUtil.parseJsonObject(testJson);
        assertTrue("Json parser extracted string name", "Alex".equals(map.get("name")));
        assertTrue("Json parser extracted number age", Long.valueOf(22).equals(map.get("age")));
        assertTrue("Json parser extracted double height", Double.valueOf(175.5).equals(map.get("height")));
        assertTrue("Json parser extracted boolean active", Boolean.TRUE.equals(map.get("active")));

        String serialized = JsonUtil.toJson(map);
        assertTrue("Json serializer contains name key", serialized.contains("\"name\":\"Alex\""));
    }

    private static void testFitFlowServiceFacade() {
        try {
            FitFlowService service = FitFlowService.getInstance();
            service.updateProfile("Jordan", 23, "FEMALE", 168.0, 58.0, "MODERATELY_ACTIVE", "WEIGHT_LOSS");
            Map<String, Object> dashboard = service.getDashboardData();

            assertTrue("Dashboard user name matches", "Jordan".equals(dashboard.get("userName")));
            assertTrue("Dashboard contains BMI map", dashboard.containsKey("bmi"));
            assertTrue("Dashboard contains Water map", dashboard.containsKey("water"));
            assertTrue("Dashboard contains Calories map", dashboard.containsKey("calories"));

            WorkoutRecommendation workout = service.generateWorkout(30, "HIGH");
            assertTrue("Generated workout recommendation non-null", workout != null);
            assertTrue("Workout summary non-empty", !workout.generateSummary().isEmpty());

            NutritionRecommendation nutrition = service.generateNutrition();
            assertTrue("Nutrition plan non-empty", !nutrition.getPrimaryFoods().isEmpty());
        } catch (Exception e) {
            assertTrue("FitFlowService test failed: " + e.getMessage(), false);
        }
    }
}

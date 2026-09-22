package controller;

import calculator.BMICalculator;
import calculator.CalorieCalculator;
import calculator.WaterIntakeCalculator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import exception.InvalidHealthDataException;
import model.Exercise;
import model.HealthProfile;
import model.HydrationLog;
import model.WellnessData;
import recommendation.NutritionRecommendation;
import recommendation.WellnessRecommendation;
import recommendation.WorkoutRecommendation;
import recommendation.WorkoutRoutine;
import service.FitFlowService;
import util.JsonUtil;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * FitFlow Academic AOP Project
 * REST API Controller Handler.
 *
 * OOP Concept: Controller Layer, Polymorphism, Exception Handling,
 * and JSON serialization integration.
 */
public class ApiHandler implements HttpHandler {
    private final FitFlowService service;

    public ApiHandler() {
        this.service = FitFlowService.getInstance();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Enable CORS
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");

        String method = exchange.getRequestMethod().toUpperCase();
        if ("OPTIONS".equals(method)) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        String path = exchange.getRequestURI().getPath();

        try {
            if (path.equals("/api/dashboard") && "GET".equals(method)) {
                handleGetDashboard(exchange);
            } else if (path.equals("/api/profile") && "POST".equals(method)) {
                handleUpdateProfile(exchange);
            } else if (path.equals("/api/bmi") && "GET".equals(method)) {
                handleGetBmi(exchange);
            } else if (path.equals("/api/bmi/simulate") && "GET".equals(method)) {
                handleSimulateBmi(exchange);
            } else if (path.equals("/api/water") && "GET".equals(method)) {
                handleGetWater(exchange);
            } else if (path.equals("/api/water/add-glass") && "POST".equals(method)) {
                handleAddGlass(exchange);
            } else if (path.equals("/api/water/add-custom") && "POST".equals(method)) {
                handleAddCustomWater(exchange);
            } else if (path.equals("/api/water/target") && "POST".equals(method)) {
                handleSetWaterTarget(exchange);
            } else if (path.equals("/api/water/reset") && "POST".equals(method)) {
                handleResetWater(exchange);
            } else if (path.equals("/api/calories") && "GET".equals(method)) {
                handleGetCalories(exchange);
            } else if (path.equals("/api/workout") && "GET".equals(method)) {
                handleGetWorkout(exchange);
            } else if (path.equals("/api/nutrition") && "GET".equals(method)) {
                handleGetNutrition(exchange);
            } else if (path.equals("/api/wellness") && "GET".equals(method)) {
                handleGetWellness(exchange);
            } else if (path.equals("/api/wellness") && "POST".equals(method)) {
                handleUpdateWellness(exchange);
            } else {
                sendErrorResponse(exchange, 404, "Endpoint not found: " + path);
            }
        } catch (InvalidHealthDataException e) {
            Map<String, Object> err = new LinkedHashMap<>();
            err.put("success", false);
            err.put("error", e.getMessage());
            err.put("field", e.getInvalidField());
            err.put("value", e.getInvalidValue());
            sendJsonResponse(exchange, 400, err);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> err = new LinkedHashMap<>();
            err.put("success", false);
            err.put("error", "Internal server error: " + e.getMessage());
            sendJsonResponse(exchange, 500, err);
        }
    }

    private void handleGetDashboard(HttpExchange exchange) throws Exception {
        Map<String, Object> data = service.getDashboardData();
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("success", true);
        res.put("data", data);
        sendJsonResponse(exchange, 200, res);
    }

    private void handleUpdateProfile(HttpExchange exchange) throws Exception {
        String body = readRequestBody(exchange);
        Map<String, Object> json = JsonUtil.parseJsonObject(body);

        String name = String.valueOf(json.getOrDefault("name", "Athlete"));
        int age = parseInt(json.get("age"), 20);
        String gender = String.valueOf(json.getOrDefault("gender", "MALE"));
        double height = parseDouble(json.get("height"), 170.0);
        double weight = parseDouble(json.get("weight"), 65.0);
        String activity = String.valueOf(json.getOrDefault("activityLevel", "MODERATELY_ACTIVE"));
        String goal = String.valueOf(json.getOrDefault("fitnessGoal", "GENERAL_FITNESS"));

        service.updateProfile(name, age, gender, height, weight, activity, goal);

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("success", true);
        res.put("message", "Profile updated successfully.");
        res.put("data", service.getDashboardData());
        sendJsonResponse(exchange, 200, res);
    }

    private void handleGetBmi(HttpExchange exchange) throws Exception {
        BMICalculator.BMIResult bmi = service.getBMI();
        HealthProfile p = service.getProfile();

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("success", true);
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("bmiValue", bmi.getBmiValue());
        data.put("category", bmi.getCategoryName());
        data.put("colorHex", bmi.getColorHex());
        data.put("advice", bmi.getAdvice());
        data.put("minHealthyWeight", bmi.getMinHealthyWeightKg());
        data.put("maxHealthyWeight", bmi.getMaxHealthyWeightKg());
        data.put("currentWeight", p != null ? p.getWeightKg() : 0);
        data.put("currentHeight", p != null ? p.getHeightCm() : 0);
        res.put("data", data);

        sendJsonResponse(exchange, 200, res);
    }

    private void handleSimulateBmi(HttpExchange exchange) throws Exception {
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI().getQuery());
        HealthProfile p = service.getProfile();
        double defWeight = p != null ? p.getWeightKg() : 70.0;
        double defHeight = p != null ? p.getHeightCm() : 175.0;

        double weight = parseDouble(queryParams.get("weight"), defWeight);
        double height = parseDouble(queryParams.get("height"), defHeight);

        BMICalculator.BMIResult bmi = BMICalculator.compute(weight, height);

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("success", true);
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("bmiValue", bmi.getBmiValue());
        data.put("category", bmi.getCategoryName());
        data.put("colorHex", bmi.getColorHex());
        data.put("advice", bmi.getAdvice());
        data.put("minHealthyWeight", bmi.getMinHealthyWeightKg());
        data.put("maxHealthyWeight", bmi.getMaxHealthyWeightKg());
        data.put("simulatedWeight", weight);
        data.put("simulatedHeight", height);
        res.put("data", data);

        sendJsonResponse(exchange, 200, res);
    }

    private void handleGetWater(HttpExchange exchange) throws Exception {
        WaterIntakeCalculator.WaterResult req = service.getWaterRequirement();
        HydrationLog log = service.getWaterTracker().getLog();

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("targetMl", log.getDailyTargetMl());
        data.put("targetLiters", log.getDailyTargetLiters());
        data.put("consumedMl", log.getConsumedMl());
        data.put("consumedLiters", log.getConsumedLiters());
        data.put("remainingMl", log.getRemainingMl());
        data.put("remainingLiters", log.getRemainingLiters());
        data.put("progressPercent", log.getProgressPercentage());
        data.put("glassesConsumed", log.getGlassesConsumed());
        data.put("glassesRemaining", log.getGlassesRemaining());
        data.put("targetGlasses", log.getTotalTargetGlasses());
        data.put("recommendedIntake", req.getMilliliters());

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("success", true);
        res.put("data", data);
        sendJsonResponse(exchange, 200, res);
    }

    private void handleAddGlass(HttpExchange exchange) throws Exception {
        service.logGlass();
        handleGetWater(exchange);
    }

    private void handleAddCustomWater(HttpExchange exchange) throws Exception {
        String body = readRequestBody(exchange);
        Map<String, Object> json = JsonUtil.parseJsonObject(body);
        int amountMl = parseInt(json.get("amountMl"), 250);
        service.logCustomWater(amountMl);
        handleGetWater(exchange);
    }

    private void handleSetWaterTarget(HttpExchange exchange) throws Exception {
        String body = readRequestBody(exchange);
        Map<String, Object> json = JsonUtil.parseJsonObject(body);
        int targetMl = parseInt(json.get("targetMl"), 2500);
        service.setWaterTarget(targetMl);
        handleGetWater(exchange);
    }

    private void handleResetWater(HttpExchange exchange) throws Exception {
        service.resetWater();
        handleGetWater(exchange);
    }

    private void handleGetCalories(HttpExchange exchange) throws Exception {
        CalorieCalculator.CalorieResult cal = service.getCalorieRequirement();

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("bmr", cal.getBmr());
        data.put("maintenanceCalories", cal.getMaintenanceCalories());
        data.put("targetCalories", cal.getTargetCalories());
        data.put("goal", cal.getGoal().getDisplayName());
        data.put("goalAdjustment", cal.getGoal().getCalorieAdjustment());
        data.put("activityLevel", cal.getActivityLevel().getDisplayName());
        data.put("proteinGrams", cal.getProteinGrams());
        data.put("carbGrams", cal.getCarbGrams());
        data.put("fatGrams", cal.getFatGrams());

        // Caloric macro proportions
        data.put("proteinKcal", cal.getProteinGrams() * 4);
        data.put("carbKcal", cal.getCarbGrams() * 4);
        data.put("fatKcal", cal.getFatGrams() * 9);

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("success", true);
        res.put("data", data);
        sendJsonResponse(exchange, 200, res);
    }

    private void handleGetWorkout(HttpExchange exchange) throws Exception {
        Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI().getQuery());
        int duration = parseInt(queryParams.get("duration"), 30);
        String energy = queryParams.getOrDefault("energy", "MEDIUM");

        WorkoutRecommendation rec = service.generateWorkout(duration, energy);
        WorkoutRoutine routine = rec.getRoutine();

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("title", rec.getTitle());
        data.put("summary", rec.generateSummary());
        data.put("durationMinutes", rec.getRequestedDurationMinutes());
        data.put("energyLevel", rec.getUserEnergyLevel().getDisplayName());
        data.put("estimatedCaloriesBurned", rec.getEstimatedCaloriesBurned());

        if (routine != null) {
            data.put("routineName", routine.getName());
            data.put("routineType", routine.getRoutineType());
            data.put("intensityBadge", routine.getIntensityBadge());
            data.put("difficulty", routine.getDifficultyLevel());
            data.put("warmUpTip", routine.getWarmUpTip());
            data.put("coolDownTip", routine.getCoolDownTip());

            List<Map<String, Object>> exList = new ArrayList<>();
            for (Exercise ex : routine.getExercises()) {
                Map<String, Object> eMap = new LinkedHashMap<>();
                eMap.put("name", ex.getName());
                eMap.put("category", ex.getCategory());
                eMap.put("sets", ex.getSets());
                eMap.put("repsOrDuration", ex.getRepsOrDuration());
                eMap.put("targetMuscle", ex.getTargetMuscle());
                eMap.put("instructions", ex.getInstructions());
                exList.add(eMap);
            }
            data.put("exercises", exList);
        }

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("success", true);
        res.put("data", data);
        sendJsonResponse(exchange, 200, res);
    }

    private void handleGetNutrition(HttpExchange exchange) throws Exception {
        NutritionRecommendation rec = service.generateNutrition();

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("title", rec.getTitle());
        data.put("summary", rec.generateSummary());
        data.put("goal", rec.getGoal().getDisplayName());
        data.put("targetCalories", rec.getTargetCalories());
        data.put("proteinGrams", rec.getProteinGrams());
        data.put("carbGrams", rec.getCarbGrams());
        data.put("fatGrams", rec.getFatGrams());
        data.put("primaryFoods", rec.getPrimaryFoods());
        data.put("mealPlan", rec.getMealPlan());
        data.put("guidelines", rec.getNutritionGuidelines());

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("success", true);
        res.put("data", data);
        sendJsonResponse(exchange, 200, res);
    }

    private void handleGetWellness(HttpExchange exchange) throws Exception {
        WellnessRecommendation rec = service.generateWellness();
        WellnessData dataObj = service.getWellnessData();

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("title", rec.getTitle());
        data.put("summary", rec.generateSummary());
        data.put("score", rec.getWellnessScore());
        data.put("readinessStatus", rec.getReadinessStatus());
        data.put("recoveryAdvice", rec.getRecoveryAdvice());
        data.put("workoutAdjustment", rec.getWorkoutAdjustment());
        data.put("sleepHours", dataObj != null ? dataObj.getSleepHours() : 7.5);
        data.put("stressLevel", dataObj != null ? dataObj.getStressLevel().getDisplayName() : "Medium");
        data.put("energyLevel", dataObj != null ? dataObj.getEnergyLevel().getDisplayName() : "Medium");
        data.put("dailyActivity", dataObj != null ? dataObj.getDailyActivity() : "");

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("success", true);
        res.put("data", data);
        sendJsonResponse(exchange, 200, res);
    }

    private void handleUpdateWellness(HttpExchange exchange) throws Exception {
        String body = readRequestBody(exchange);
        Map<String, Object> json = JsonUtil.parseJsonObject(body);

        double sleepHours = parseDouble(json.get("sleepHours"), 7.5);
        String stress = String.valueOf(json.getOrDefault("stressLevel", "MEDIUM"));
        String energy = String.valueOf(json.getOrDefault("energyLevel", "MEDIUM"));
        String dailyActivity = String.valueOf(json.getOrDefault("dailyActivity", ""));

        service.updateWellness(sleepHours, stress, energy, dailyActivity);
        handleGetWellness(exchange);
    }

    // --- Helper Utility Methods ---

    private String readRequestBody(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int nRead;
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return new String(buffer.toByteArray(), StandardCharsets.UTF_8);
    }

    private Map<String, String> parseQueryParams(String query) {
        Map<String, String> params = new HashMap<>();
        if (query == null || query.isEmpty()) return params;
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            try {
                if (idx > 0) {
                    params.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
                            URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
                } else {
                    params.put(URLDecoder.decode(pair, "UTF-8"), "");
                }
            } catch (UnsupportedEncodingException ignored) {}
        }
        return params;
    }

    private int parseInt(Object val, int def) {
        if (val == null) return def;
        try {
            return Integer.parseInt(String.valueOf(val).trim());
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private double parseDouble(Object val, double def) {
        if (val == null) return def;
        try {
            return Double.parseDouble(String.valueOf(val).trim());
        } catch (NumberFormatException e) {
            return def;
        }
    }

    private void sendJsonResponse(HttpExchange exchange, int statusCode, Object data) throws IOException {
        String json = JsonUtil.toJson(data);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private void sendErrorResponse(HttpExchange exchange, int statusCode, String message) throws IOException {
        Map<String, Object> err = new LinkedHashMap<>();
        err.put("success", false);
        err.put("error", message);
        sendJsonResponse(exchange, statusCode, err);
    }
}

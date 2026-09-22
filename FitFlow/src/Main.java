import controller.FitFlowServer;

import java.io.File;

/**
 * FitFlow Academic AOP Project
 * Main Application Entry Point.
 *
 * This project demonstrates core and advanced Object-Oriented Programming (OOP) concepts:
 * 1. Classes & Objects: Encapsulation of domain state across User, HealthProfile, Exercises.
 * 2. Encapsulation: Strict data protection, private fields, invariant-checking getters/setters.
 * 3. Inheritance: Base Recommendation class extended by Workout, Nutrition, Wellness recommendations.
 *                Base WorkoutRoutine extended by Strength, Cardio, HIIT, and Flexibility routines.
 * 4. Polymorphism: Dynamic method dispatch in calorie burn estimation and recommendation generation.
 * 5. Abstraction: Interfaces (HealthCalculator<T>) and abstract classes (Recommendation, WorkoutRoutine).
 * 6. Collections: Robust generic collections (List<Exercise>, Map<String, Object>, ArrayList).
 * 7. Exception Handling: Custom exception hierarchy (FitFlowException, InvalidHealthDataException).
 * 8. Separation of Concerns: Clear multi-tier design (Model, Calculator, Recommendation, Service, Controller, Presentation).
 */
public class Main {
    public static void main(String[] args) {
        int port = FitFlowServer.DEFAULT_PORT;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException ignored) {}
        }

        // Determine project root directory dynamically
        String projectRoot = ".";
        File checkWeb = new File("web");
        if (!checkWeb.exists()) {
            File subWeb = new File("FitFlow/web");
            if (subWeb.exists()) {
                projectRoot = "FitFlow";
            }
        }

        try {
            FitFlowServer server = new FitFlowServer(port, projectRoot);
            server.start();

            int actualPort = server.getPort();
            System.out.println("\n[INFO] Open your web browser and navigate to:");
            System.out.println("       --> http://localhost:" + actualPort + "/");
            System.out.println("\n[VIVA NOTE] All business logic, BMI calculation, water calculation,");
            System.out.println("            BMR/TDEE calculation, and workout recommendations are executed");
            System.out.println("            by Java on the backend. JavaScript only acts as a presentation bridge.\n");

        } catch (Exception e) {
            System.err.println("Fatal: Could not start FitFlow server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

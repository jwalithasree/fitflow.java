# FitFlow — Advanced Object-Oriented Programming (AOP) Project

> **Intelligent, Personalized Fitness & Wellness Platform**  
> *Academic Submission for Advanced Object-Oriented Programming (AOP)*  
> **Backend:** 100% Pure Java 21 (Zero External Dependencies) &bull; **Frontend:** Modern HTML5 / CSS3 Glassmorphism

---

## 1. Project Title & Overview
**FitFlow** is an interactive, personalized fitness and wellness web application designed specifically for students and young adults. Unlike superficial client-side calculators, FitFlow is engineered on a rigorous **Java Object-Oriented Architecture** where **all business logic, mathematical formulas, state tracking, recommendations, and validations are computed by Java backend classes**.

HTML5 and CSS3 are utilized exclusively to deliver a vibrant, modern, dark-mode fitness app experience, while JavaScript functions strictly as a presentation bridge (`fetch` API), containing **zero** calculation or business logic.

---

## 2. Problem Statement
Young adults and college students face irregular schedules, stress, and variable energy levels. Existing fitness tools are either:
1. Static, disconnected web calculators with hardcoded JavaScript math.
2. Bloated commercial enterprise platforms that cannot be inspected, explained, or extended for academic study.

There is a distinct need for a cohesive, scientifically grounded, and modular application that models human physiology (BMI, Basal Metabolic Rate, Hydration, Recovery) using idiomatic Object-Oriented design principles.

---

## 3. Project Objectives
- **Demonstrate Real-World OOP**: Implement encapsulation, inheritance, polymorphism, abstraction, interfaces, generic collections, and custom exception hierarchies in an authentic domain.
- **Architectural Separation of Concerns**: Strictly decouple the presentation layer (HTML/CSS) from domain models, mathematical calculators, and recommendation planners.
- **Zero-Dependency Portability**: Built using standard Java 21 (`com.sun.net.httpserver`), enabling instantaneous compilation with `javac` and execution without configuring complex Tomcat or Spring containers.
- **Robust Invariant Protection**: Guard system integrity using custom checked exceptions (`InvalidHealthDataException`) and defensive validation.

---

## 4. Key Features

### 👤 1. User Profile Management
- Stores: Full Name, Age, Biological Gender, Height (cm), Weight (kg), Physical Activity Level, and Primary Fitness Goal.
- Fitness Goals: *Weight Loss*, *Weight Gain*, *Muscle Building*, *General Fitness*, *Maintenance*.
- Activity Multipliers: *Sedentary (1.2x)*, *Lightly Active (1.375x)*, *Moderately Active (1.55x)*, *Very Active (1.725x)*.

### ⚖️ 2. Body Mass Index (BMI) & Health Engine
- **Formula:**  
  $$\text{BMI} = \frac{\text{Weight (kg)}}{[\text{Height (m)}]^2}$$
- Evaluates WHO categories: **Underweight** ($< 18.5$), **Normal** ($18.5 - 24.9$), **Overweight** ($25.0 - 29.9$), **Obese** ($\ge 30.0$).
- Computes individualized healthy weight range $[\text{Min}, \text{Max}]$ in kg for the user's specific height.

### 💧 3. Hydration Calculator & Interactive Water Tracker
- **Formula:**  
  $$\text{Target (ml)} = \text{Body Weight (kg)} \times 35\text{ ml} \times \text{ActivityMultiplier}$$
- Calculates targets in milliliters, liters, and standard 250 ml glasses.
- **Stateful Java Tracker:** Real-time logging of glasses ($+250\text{ ml}$), bottles ($+500\text{ ml}$), or custom amounts, computing remaining volume and percentage completion in Java.

### 🔥 4. BMR & Calorie Calculator (Mifflin-St Jeor Equation)
- **Mifflin-St Jeor Formula for Basal Metabolic Rate (BMR):**
  - **Men:** $\text{BMR} = 10 \times \text{weight (kg)} + 6.25 \times \text{height (cm)} - 5 \times \text{age (yr)} + 5$
  - **Women:** $\text{BMR} = 10 \times \text{weight (kg)} + 6.25 \times \text{height (cm)} - 5 \times \text{age (yr)} - 161$
- **Total Daily Energy Expenditure (TDEE):** $\text{TDEE} = \text{BMR} \times \text{ActivityMultiplier}$
- **Goal Adjustments:**
  - *Weight Loss:* $\text{TDEE} - 500\text{ kcal}$
  - *Weight Gain:* $\text{TDEE} + 450\text{ kcal}$
  - *Muscle Building:* $\text{TDEE} + 300\text{ kcal}$
  - *General Fitness / Maintenance:* $\text{TDEE}$
- Goal-specific macronutrient partitioning (Protein, Carbohydrates, Fats in grams and kcal).

### 🏋️ 5. Dynamic Polymorphic Workout Recommendation System
- Selects routines based on a multi-factorial matrix: **Available Time** (10, 20, 30, 45, 60 min) $\times$ **Energy Level** (Low, Medium, High) $\times$ **Goal**.
- Polymorphic routine subclasses (`StrengthRoutine`, `CardioRoutine`, `HIITRoutine`, `FlexibilityRoutine`) calculate MET-based estimated calorie burn.
- Provides exercise list, target muscle groups, sets, reps/duration, warm-up, and cool-down protocols.

### 🥗 6. Nutrition & Meal Planning Blueprint
- Translates caloric goals into tailored food staples (e.g. eggs, chicken, paneer, oats, legumes, Greek yogurt).
- Generates structured daily meal schedules (Breakfast, Lunch, Pre/Post-workout, Dinner).

### 🌿 7. Holistic Wellness & Recovery Engine
- Ingests: Sleep hours, Subjective Stress (Low, Medium, High), Energy level, and Daily movement notes.
- Computes a weighted 0-100 Wellness Readiness Score in Java and returns restorative or progressive overload recommendations.

---

## 5. Object-Oriented Programming (AOP) Viva Cheat Sheet

| OOP Principle | Where Demonstrated in FitFlow | Academic Explanation |
| :--- | :--- | :--- |
| **Encapsulation** | `User`, `HealthProfile`, `HydrationLog`, `WellnessData` | All fields are `private`. State mutation occurs strictly through validated setters and synchronized methods. Defensive copies (`Collections.unmodifiableList`) prevent external tampering. |
| **Abstraction** | `HealthCalculator<T>` (interface), `Recommendation` (abstract class), `WorkoutRoutine` (abstract class) | Defines essential contracts without exposing low-level mechanics. Separates *what* a component does from *how* it does it. |
| **Inheritance** | `WorkoutRoutine` $\rightarrow$ `StrengthRoutine`, `CardioRoutine`, `HIITRoutine`, `FlexibilityRoutine`; `Recommendation` $\rightarrow$ `WorkoutRecommendation`, `NutritionRecommendation`, `WellnessRecommendation` | Eliminates redundant code by extracting common state (id, name, duration, exercises) into base classes while specializing subclasses. |
| **Polymorphism** | `calculateEstimatedCaloriesBurned()`, `generateSummary()`, `HealthCalculator.calculate()` | Dynamic method dispatch: The runtime JVM resolves the appropriate calculation algorithm based on the concrete subclass instance. |
| **Interfaces** | `HealthCalculator<T>` | Standardizes metric calculators (`BMICalculator`, `WaterIntakeCalculator`, `CalorieCalculator`) with generic return types. |
| **Collections** | `ArrayList<Exercise>`, `List<String>`, `Map<String, Object>`, `EnumMap` | Utilizes generic collections for typesafe data storage and dynamic exercise sequencing. |
| **Exception Handling** | `FitFlowException`, `InvalidHealthDataException` | Custom checked exception hierarchy. Catches non-positive weights, out-of-range ages (10-120), returning HTTP 400 errors without server crashes. |
| **Design Patterns** | **Facade Pattern** (`FitFlowService`), **Strategy / Factory Pattern** (`WorkoutPlanner`), **Value Object Pattern** (`BMIResult`, `WaterResult`, `CalorieResult`) | Provides simplified access to subsystems and creates objects without exposing instantiation logic. |

---

## 6. System Architecture

```
+------------------------------------------------------------------------+
|                      PRESENTATION TIER (Browser)                       |
|  - HTML5 Pages (index, profile, dashboard, health, hydration, workout) |
|  - CSS3 Glassmorphism (Neon Accents, Radial Progress SVG, Responsive)  |
|  - JavaScript (Strictly HTTP presentation bridge: fetch() - NO logic)  |
+-----------------------------------▲------------------------------------+
                                    │ JSON Requests / Responses
+-----------------------------------▼------------------------------------+
|                       JAVA 21 CONTROLLER TIER                          |
|  - FitFlowServer (HttpServer on port 8080/8081 with thread pool)       |
|  - StaticFileHandler (Path validation, MIME resolution, 404 handling)   |
|  - ApiHandler (REST routing: /api/profile, /api/bmi, /api/workout...)  |
+-----------------------------------▲------------------------------------+
                                    │
+-----------------------------------▼------------------------------------+
|                         SERVICE / FACADE TIER                          |
|  - FitFlowService (Central coordinator, session manager)               |
|  - WaterTracker (Stateful hydration tracking, glass logging)           |
+-----------------------------------▲------------------------------------+
                                    │
         +--------------------------+-------------------------+
         │                                                    │
+--------▼--------------------+            +------------------▼------------------+
|      CALCULATOR TIER        |            |        RECOMMENDATION TIER          |
| - BMICalculator             |            | - RecommendationEngine              |
| - WaterIntakeCalculator     |            | - WorkoutPlanner (Polymorphic)      |
| - CalorieCalculator (BMR)   |            | - NutritionPlanner                  |
+--------▲--------------------+            | - WellnessPlanner                   |
         │                                 +------------------▲------------------+
         +--------------------------+-------------------------+
                                    │
+-----------------------------------▼------------------------------------+
|                           MODEL TIER                                   |
|  - User, HealthProfile, Exercise, HydrationLog, WellnessData           |
|  - Enums: Gender, ActivityLevel, FitnessGoal, BMICategory, EnergyLevel |
|  - Exceptions: FitFlowException, InvalidHealthDataException             |
+------------------------------------------------------------------------+
```

---

## 7. Project Structure

```
FitFlow/
│
├── src/
│   ├── model/
│   │   ├── User.java                     # User entity (Composition: has-a HealthProfile)
│   │   ├── HealthProfile.java            # Encapsulated biometric state with validation
│   │   ├── Exercise.java                 # Individual exercise entity
│   │   ├── HydrationLog.java             # Stateful daily water intake tracker
│   │   ├── WellnessData.java             # Sleep, stress, energy & recovery scoring
│   │   └── enums/
│   │       ├── Gender.java               # Gender with BMR offset
│   │       ├── ActivityLevel.java        # Activity with calorie/water multipliers
│   │       ├── FitnessGoal.java          # Goals with caloric adjustment
│   │       ├── BMICategory.java          # WHO BMI categories & clinical advice
│   │       ├── EnergyLevel.java          # Energy states (Low, Medium, High)
│   │       └── StressLevel.java          # Stress indicators
│   │
│   ├── exception/
│   │   ├── FitFlowException.java         # Base custom exception
│   │   └── InvalidHealthDataException.java# Checked exception for out-of-bounds inputs
│   │
│   ├── calculator/
│   │   ├── HealthCalculator.java         # Generic interface for all calculators
│   │   ├── BMICalculator.java            # Quetelet BMI formula & healthy range
│   │   ├── WaterIntakeCalculator.java    # 35ml/kg * activity multiplier formula
│   │   └── CalorieCalculator.java        # Mifflin-St Jeor BMR & TDEE calculation
│   │
│   ├── recommendation/
│   │   ├── Recommendation.java           # Abstract base class for all recommendations
│   │   ├── WorkoutRoutine.java           # Abstract base class for exercise routines
│   │   ├── StrengthRoutine.java          # Subclass overriding MET = 6.0
│   │   ├── CardioRoutine.java            # Subclass overriding MET = 7.5
│   │   ├── HIITRoutine.java              # Subclass overriding MET = 9.0
│   │   ├── FlexibilityRoutine.java       # Subclass overriding MET = 3.0
│   │   ├── WorkoutRecommendation.java    # Concrete recommendation wrapper
│   │   ├── NutritionRecommendation.java  # Concrete nutrition guidance
│   │   ├── WellnessRecommendation.java   # Concrete wellness guidance
│   │   ├── WorkoutPlanner.java           # Factory creating polymorphic routines
│   │   ├── NutritionPlanner.java         # Generates food groups & meal blueprints
│   │   ├── WellnessPlanner.java          # Generates recovery actions
│   │   └── RecommendationEngine.java     # Facade coordinating all planners
│   │
│   ├── service/
│   │   ├── FitFlowService.java           # Primary Service Facade
│   │   └── WaterTracker.java             # Hydration session tracker
│   │
│   ├── util/
│   │   └── JsonUtil.java                 # Zero-dependency Java JSON parser/builder
│   │
│   ├── controller/
│   │   ├── FitFlowServer.java            # Embedded HttpServer with auto-port fallback
│   │   ├── ApiHandler.java               # REST API controller & JSON response handler
│   │   └── StaticFileHandler.java        # Serves static web assets safely
│   │
│   └── Main.java                         # Application entry point
│
├── test/
│   └── FitFlowTest.java                  # Automated unit test suite (47 verification checks)
│
├── web/
│   ├── index.html                        # Welcome & platform overview
│   ├── profile.html                      # Biometric profile setup & validation
│   ├── dashboard.html                    # Central fitness metrics & status cards
│   ├── health.html                       # BMI gauge & body composition analytics
│   ├── hydration.html                    # Interactive water logger & circular meter
│   ├── workout.html                      # Adaptive time/energy workout generator
│   ├── nutrition.html                    # Calorie targets, macros & meal schedules
│   └── wellness.html                     # Sleep, stress & recovery monitor
│
├── css/
│   └── style.css                         # Modern dark-mode glassmorphic styling
│
├── js/
│   └── app.js                            # Presentation bridge (fetch calls only)
│
├── run.bat                               # Windows 1-click compile & launch script
└── README.md                             # Comprehensive project documentation
```

---

## 8. How to Run the Project

### Prerequisites
- **Java 21 JDK** (or Java 17+) installed and configured on your `PATH`.
- Verify by opening terminal/PowerShell and running:
  ```bash
  javac -version
  java -version
  ```

### Option A: One-Click Launch (Windows)
Double-click `run.bat` (or open command prompt and run):
```cmd
run.bat
```
This script will:
1. Compile all Java source files into `bin/`.
2. Launch the embedded Java server.
3. Automatically launch your default browser to `http://localhost:8080/` (or `http://localhost:8081/` if 8080 is in use).

### Option B: Manual Command Line (Cross-Platform)

1. **Compile Backend Classes:**
   ```bash
   cd FitFlow
   javac -d bin src/model/enums/*.java src/model/*.java src/exception/*.java src/calculator/*.java src/recommendation/*.java src/util/*.java src/service/*.java src/controller/*.java src/Main.java
   ```

2. **Run Application:**
   ```bash
   java -Xms16m -Xmx64m -cp bin Main 8080
   ```

3. **Open in Browser:**
   Navigate to: `http://localhost:8080/` (or the port printed in the terminal).

### Option C: Run Automated Tests
```bash
javac -d bin -cp bin test/FitFlowTest.java
java -Xms16m -Xmx64m -cp bin test.FitFlowTest
```
*Executes all 47 tests verifying BMI categories, Mifflin-St Jeor math, water multipliers, routine polymorphism, and custom exceptions.*

---

## 9. Frequently Asked Viva Questions & Model Answers

### Q1: Why did you not use Spring Boot or Tomcat?
**Answer:** In this academic AOP project, using external frameworks like Spring Boot or Tomcat introduces thousands of third-party abstraction layers that obscure the student's direct Object-Oriented code. By using Java's standard `com.sun.net.httpserver`, we demonstrate pure, unadulterated Object-Oriented design (Encapsulation, Polymorphism, Custom Exceptions, Multithreading) with **zero external dependencies**.

### Q2: How does data move from the UI to Java and back?
**Answer:** The user interacts with HTML forms or buttons. JavaScript captures the DOM event and executes a standard `fetch('/api/...')` asynchronous call sending or requesting JSON. The Java `ApiHandler` parses the request, invokes the `FitFlowService` domain methods, executes the calculations in Java, and serializes the result back to JSON using `JsonUtil`. JavaScript simply updates the DOM elements. **No math or business decisions are made in JavaScript**.

### Q3: Where is Polymorphism demonstrated?
**Answer:** In `WorkoutRoutine` and its four concrete subclasses (`StrengthRoutine`, `CardioRoutine`, `HIITRoutine`, `FlexibilityRoutine`). Each subclass overrides `calculateEstimatedCaloriesBurned(duration, weight)` using its own Metabolic Equivalent of Task (MET) coefficient. When `WorkoutPlanner` returns a `WorkoutRoutine`, the service invokes `calculateEstimatedCaloriesBurned()` polymorphically without knowing the concrete routine type at compile time.

### Q4: How is Encapsulation maintained?
**Answer:** All fields across `HealthProfile`, `User`, `HydrationLog`, and `WellnessData` are strictly `private`. State cannot be modified directly. Methods like `validateAndSetAge()` and `validateAndSetWeight()` enforce physiological invariants, throwing `InvalidHealthDataException` if inputs are negative or illogical. Collections like `getExercises()` return unmodifiable defensive views via `Collections.unmodifiableList()`.

### Q5: What BMR formula is used and why?
**Answer:** The **Mifflin-St Jeor equation**, as published in the American Journal of Clinical Nutrition. It is clinically recognized as more accurate than the older 1919 Harris-Benedict equation. It accounts for biological gender offsets ($+5$ for men, $-161$ for women), weight in kg, height in cm, and age in years.

---

## 10. Future Enhancements
- **Persistent Database Storage:** Migrate in-memory state to SQLite/PostgreSQL using JDBC.
- **Wearable API Integration:** Ingest live heart rate and step counts from Google Health Connect / Apple Health.
- **Micro-Nutrient Tracking:** Expand nutrition engine to calculate vitamins, minerals, and dietary fiber targets.
- **Multi-Day Longitudinal Analytics:** Historical trend graphs of hydration and weight trajectory over weeks/months.

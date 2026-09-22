package recommendation;

import model.enums.FitnessGoal;
import java.util.List;

/**
 * FitFlow Academic AOP Project
 * Concrete recommendation subclass encapsulating personalized nutritional guidance.
 *
 * OOP Concept: Inheritance, Polymorphism, Collections (List).
 */
public class NutritionRecommendation extends Recommendation {
    private final FitnessGoal goal;
    private final int targetCalories;
    private final int proteinGrams;
    private final int carbGrams;
    private final int fatGrams;
    private final List<String> primaryFoods;
    private final List<String> mealPlan;
    private final List<String> nutritionGuidelines;

    public NutritionRecommendation(String id, String title, FitnessGoal goal,
                                   int targetCalories, int proteinGrams, int carbGrams, int fatGrams,
                                   List<String> primaryFoods, List<String> mealPlan,
                                   List<String> nutritionGuidelines) {
        super(id, title, "NUTRITION");
        this.goal = goal;
        this.targetCalories = targetCalories;
        this.proteinGrams = proteinGrams;
        this.carbGrams = carbGrams;
        this.fatGrams = fatGrams;
        this.primaryFoods = primaryFoods;
        this.mealPlan = mealPlan;
        this.nutritionGuidelines = nutritionGuidelines;
    }

    public FitnessGoal getGoal() {
        return goal;
    }

    public int getTargetCalories() {
        return targetCalories;
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

    public List<String> getPrimaryFoods() {
        return primaryFoods;
    }

    public List<String> getMealPlan() {
        return mealPlan;
    }

    public List<String> getNutritionGuidelines() {
        return nutritionGuidelines;
    }

    @Override
    public String generateSummary() {
        return String.format("%s (%s): %d kcal [P: %dg | C: %dg | F: %dg]",
                getTitle(), goal.getDisplayName(), targetCalories, proteinGrams, carbGrams, fatGrams);
    }
}

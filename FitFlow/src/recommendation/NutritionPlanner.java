package recommendation;

import calculator.CalorieCalculator;
import model.HealthProfile;
import model.enums.FitnessGoal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * FitFlow Academic AOP Project
 * Dedicated Nutrition Recommendation Planner.
 *
 * OOP Concept: Encapsulation and Domain Logic.
 * Translates caloric targets and fitness goals into actionable meal structures,
 * macronutrient distributions, and recommended nutrient-dense food groups.
 */
public class NutritionPlanner {

    /**
     * Builds a tailored NutritionRecommendation based on the user's HealthProfile and target calories.
     */
    public NutritionRecommendation createNutritionPlan(HealthProfile profile, CalorieCalculator.CalorieResult calorieResult) {
        FitnessGoal goal = (profile != null) ? profile.getFitnessGoal() : FitnessGoal.GENERAL_FITNESS;

        int calories = (calorieResult != null) ? calorieResult.getTargetCalories() : 2000;
        int protein = (calorieResult != null) ? calorieResult.getProteinGrams() : 125;
        int carbs = (calorieResult != null) ? calorieResult.getCarbGrams() : 250;
        int fat = (calorieResult != null) ? calorieResult.getFatGrams() : 55;

        List<String> primaryFoods = new ArrayList<>();
        List<String> mealPlan = new ArrayList<>();
        List<String> guidelines = new ArrayList<>();

        switch (goal) {
            case WEIGHT_LOSS:
                primaryFoods.addAll(Arrays.asList(
                        "Lean Protein: Chicken breast, egg whites, tofu, white fish, non-fat Greek yogurt",
                        "Cruciferous Vegetables: Broccoli, cauliflower, spinach, kale, bell peppers",
                        "Fiber-Rich Complex Carbs: Rolled oats, quinoa, brown rice, berries, sweet potatoes",
                        "Healthy Fats (Controlled): Extra virgin olive oil (1 tbsp), almonds (10-12), avocado (1/4)",
                        "Hydration & Thermogenic: Green tea, black coffee, lemon-infused water"
                ));
                mealPlan.addAll(Arrays.asList(
                        "Breakfast (approx. 400 kcal): 3 egg white scramble + 1 whole egg, 1/2 cup rolled oats with fresh blueberries",
                        "Lunch (approx. 500 kcal): Grilled chicken breast or tofu (150g), large mixed green salad with olive oil dressing, 1/2 cup quinoa",
                        "Snack (approx. 200 kcal): 1 cup non-fat Greek yogurt with sliced strawberries or 1 green apple with 10 almonds",
                        "Dinner (approx. 500 kcal): Baked fish or pan-seared paneer/tofu, steamed broccoli, roasted sweet potato wedges"
                ));
                guidelines.addAll(Arrays.asList(
                        "Maintain a steady caloric deficit of ~500 kcal below maintenance for steady, sustainable fat loss.",
                        "Aim for 1.6 - 2.0g protein per kg of body weight to safeguard lean muscle tissue while in a deficit.",
                        "Drink 500 ml of water 20 minutes before meals to promote natural satiety.",
                        "Prioritize high-volume, low-calorie foods (greens, cucumbers, berries) to stay comfortably full."
                ));
                break;

            case MUSCLE_BUILDING:
                primaryFoods.addAll(Arrays.asList(
                        "High Quality Protein: Whole eggs, chicken breast, paneer (cottage cheese), whey, lentils/dal",
                        "Energy-Dense Complex Carbs: Brown/jasmine rice, oats, sweet potatoes, whole wheat roti/bread",
                        "Micronutrients & Recovery: Bananas, oranges, dark leafy spinach, carrots",
                        "Healthy Hormonal Fats: Peanut butter, almonds, walnuts, chia seeds, extra virgin olive oil",
                        "Dairy & Bone Health: Curd, milk, fortified plant milk"
                ));
                mealPlan.addAll(Arrays.asList(
                        "Breakfast (approx. 600 kcal): 3 whole eggs omelet + 2 slices whole wheat toast, 1 banana with peanut butter",
                        "Lunch (approx. 700 kcal): 180g grilled chicken / paneer tikka, 1.5 cups steamed rice, 1 bowl dal, mixed vegetable curry",
                        "Pre/Post-Workout (approx. 350 kcal): Whey protein shake (or soy/pea protein) with 1 cup milk, oats, and 1 tbsp honey",
                        "Dinner (approx. 650 kcal): Grilled fish or paneer stir-fry with quinoa, sautéed green beans, and mixed salad"
                ));
                guidelines.addAll(Arrays.asList(
                        "Maintain a clean caloric surplus of +300 to +400 kcal to fuel muscle protein synthesis without excessive fat gain.",
                        "Distribute protein intake evenly across 4 meals (approx. 30-40g protein per meal) to maximize leucine triggers.",
                        "Consume high-glycemic carbohydrates alongside protein within 90 minutes post-workout for rapid glycogen replenishment.",
                        "Stay rigorously hydrated: Muscle tissue is 75% water; dehydration blunts strength and recovery."
                ));
                break;

            case WEIGHT_GAIN:
                primaryFoods.addAll(Arrays.asList(
                        "Calorie-Dense Whole Foods: Peanut butter, almond butter, whole milk, curd, cheese",
                        "Complex Carbs & Starches: Oats, potatoes, bananas, rice, whole grain pasta",
                        "Protein Staples: Eggs, paneer, chicken, chickpeas, kidney beans (rajma)",
                        "Nuts & Dried Fruits: Dates, raisins, almonds, cashews, pumpkin seeds"
                ));
                mealPlan.addAll(Arrays.asList(
                        "Breakfast (approx. 650 kcal): Oatmeal cooked in whole milk with peanut butter, banana slices, and chopped dates",
                        "Lunch (approx. 750 kcal): 2 bowls brown/white rice, generous dal, grilled paneer/chicken with olive oil drizzle",
                        "Evening Nutrient Snack (approx. 450 kcal): Homemade mass shake (milk, banana, peanut butter, oats, honey)",
                        "Dinner (approx. 650 kcal): Whole wheat rotis/rice with chickpea curry (chole), cottage cheese cubes, and curd"
                ));
                guidelines.addAll(Arrays.asList(
                        "Focus on caloric density: Add nuts, seeds, and healthy oils to meals without dramatically increasing volume.",
                        "Do not skip meals; eat every 3-4 hours to comfortably achieve your surplus target.",
                        "Incorporate liquid calories (nutrient-dense smoothies, milk) if feeling overly full from solid meals."
                ));
                break;

            case GENERAL_FITNESS:
            case MAINTENANCE:
            default:
                primaryFoods.addAll(Arrays.asList(
                        "Balanced Proteins: Free-range eggs, fish, tofu, lentils, beans, low-fat yogurt",
                        "Rainbow Vegetables & Fruits: Berries, apples, broccoli, carrots, bell peppers, leafy greens",
                        "Whole Grains: Rolled oats, quinoa, brown rice, whole grain bread",
                        "Essential Fats: Avocados, olive oil, walnuts, flaxseeds"
                ));
                mealPlan.addAll(Arrays.asList(
                        "Breakfast (approx. 450 kcal): 2 eggs poached with avocado toast and an orange",
                        "Lunch (approx. 600 kcal): Grain bowl with quinoa, roasted chickpeas/chicken, spinach, tomatoes, and tahini dressing",
                        "Snack (approx. 250 kcal): Mixed handful of walnuts and blueberries with green tea",
                        "Dinner (approx. 550 kcal): Baked salmon or grilled tofu with sautéed asparagus and roasted baby potatoes"
                ));
                guidelines.addAll(Arrays.asList(
                        "Follow the 80/20 rule: 80% whole nutrient-dense foods, 20% flexible lifestyle choices.",
                        "Prioritize colorful plates to ensure diverse intake of polyphenols, antioxidants, and trace minerals.",
                        "Eat mindfully without screens; chew thoroughly to optimize digestion and nutrient assimilation."
                ));
                break;
        }

        return new NutritionRecommendation(
                "NUTRITION-REC-" + goal.name(),
                goal.getDisplayName() + " Nutritional Blueprint",
                goal,
                calories,
                protein,
                carbs,
                fat,
                primaryFoods,
                mealPlan,
                guidelines
        );
    }
}

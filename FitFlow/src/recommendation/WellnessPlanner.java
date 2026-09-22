package recommendation;

import model.WellnessData;

/**
 * FitFlow Academic AOP Project
 * Dedicated Wellness & Recovery Planner.
 *
 * OOP Concept: Encapsulation and Domain Logic.
 * Translates sleep hours, stress indicators, and daily fatigue into recovery action steps.
 */
public class WellnessPlanner {

    /**
     * Creates a WellnessRecommendation wrapping the analyzed biometric state.
     */
    public WellnessRecommendation createWellnessPlan(WellnessData wellnessData) {
        return new WellnessRecommendation(
                "WELLNESS-REC-" + System.currentTimeMillis(),
                "Holistic Biometric Recovery Analysis",
                wellnessData
        );
    }
}

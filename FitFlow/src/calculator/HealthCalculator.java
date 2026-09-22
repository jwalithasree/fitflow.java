package calculator;

import exception.InvalidHealthDataException;
import model.HealthProfile;

/**
 * FitFlow Academic AOP Project
 * Generic Interface demonstrating Abstraction in Object-Oriented Programming.
 *
 * OOP Concept: Interface & Generics.
 * Defines a standardized contract that all health and biometric calculators must implement.
 *
 * @param <T> The return type of the calculation (e.g., Double, Integer, Result object)
 */
public interface HealthCalculator<T> {
    /**
     * Executes the calculation based on the user's encapsulated HealthProfile.
     *
     * @param profile The encapsulated health profile of the user
     * @return The calculated metric result of type T
     * @throws InvalidHealthDataException if biometric values are out of bounds
     */
    T calculate(HealthProfile profile) throws InvalidHealthDataException;

    /**
     * Returns the human-readable name of the metric being computed.
     */
    String getMetricName();
}

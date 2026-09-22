package exception;

/**
 * FitFlow Academic AOP Project
 * Checked exception thrown when user health inputs (height, weight, age)
 * violate physiological constraints or validation rules.
 *
 * OOP Concept: Domain-specific Exception subclassing FitFlowException.
 */
public class InvalidHealthDataException extends FitFlowException {
    private final String invalidField;
    private final Object invalidValue;

    public InvalidHealthDataException(String invalidField, Object invalidValue, String message) {
        super("INVALID_HEALTH_DATA", message);
        this.invalidField = invalidField;
        this.invalidValue = invalidValue;
    }

    public String getInvalidField() {
        return invalidField;
    }

    public Object getInvalidValue() {
        return invalidValue;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " [Field: '" + invalidField + "', Value: '" + invalidValue + "']";
    }
}

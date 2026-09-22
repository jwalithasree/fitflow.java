package exception;

/**
 * FitFlow Academic AOP Project
 * Base custom application exception.
 *
 * OOP Concept: Custom Exception Hierarchy demonstrating inheritance
 * and robust error propagation.
 */
public class FitFlowException extends Exception {
    private final String errorCode;

    public FitFlowException(String message) {
        super(message);
        this.errorCode = "FITFLOW_GENERAL_ERROR";
    }

    public FitFlowException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public FitFlowException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "FITFLOW_WRAPPED_ERROR";
    }

    public String getErrorCode() {
        return errorCode;
    }
}

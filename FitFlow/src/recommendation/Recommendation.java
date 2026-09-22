package recommendation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * FitFlow Academic AOP Project
 * Base Abstract Class for all system recommendations.
 *
 * OOP Concept: Abstraction and Inheritance.
 * Common attributes (id, title, category, generatedAt) are encapsulated here,
 * and subclasses implement the abstract contract.
 */
public abstract class Recommendation {
    private final String id;
    private final String title;
    private final String category; // e.g., "WORKOUT", "NUTRITION", "WELLNESS"
    private final String generatedAt;

    public Recommendation(String id, String title, String category) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.generatedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getGeneratedAt() {
        return generatedAt;
    }

    /**
     * Polymorphic method implemented by each recommendation subtype.
     * OOP Concept: Polymorphism via dynamic method dispatch.
     */
    public abstract String generateSummary();
}

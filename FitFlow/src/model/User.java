package model;

import exception.InvalidHealthDataException;

/**
 * FitFlow Academic AOP Project
 * User model representing an authenticated fitness app member.
 *
 * OOP Concept: Composition and Encapsulation.
 * A User "has-a" HealthProfile (Composition relationship).
 */
public class User {
    private final String id;
    private String name;
    private HealthProfile healthProfile;

    public User(String id, String name, HealthProfile healthProfile) throws InvalidHealthDataException {
        if (id == null || id.trim().isEmpty()) {
            throw new InvalidHealthDataException("id", id, "User ID cannot be empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidHealthDataException("name", name, "User name cannot be empty.");
        }
        this.id = id.trim();
        this.name = name.trim();
        this.healthProfile = healthProfile;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws InvalidHealthDataException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidHealthDataException("name", name, "User name cannot be empty.");
        }
        this.name = name.trim();
    }

    public HealthProfile getHealthProfile() {
        return healthProfile;
    }

    public void setHealthProfile(HealthProfile healthProfile) {
        this.healthProfile = healthProfile;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", profile=" + healthProfile +
                '}';
    }
}

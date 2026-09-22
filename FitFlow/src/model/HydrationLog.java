package model;

import exception.InvalidHealthDataException;

/**
 * FitFlow Academic AOP Project
 * Model representing a user's daily water intake tracking state.
 *
 * OOP Concept: Encapsulation of domain state and calculations.
 * All computations (remaining, percentage, glasses) are performed in Java.
 */
public class HydrationLog {
    public static final int STANDARD_GLASS_ML = 250;

    private int dailyTargetMl;
    private int consumedMl;
    private long lastUpdated;

    public HydrationLog(int dailyTargetMl) throws InvalidHealthDataException {
        if (dailyTargetMl <= 0) {
            throw new InvalidHealthDataException("dailyTargetMl", dailyTargetMl, "Target water intake must be greater than zero.");
        }
        this.dailyTargetMl = dailyTargetMl;
        this.consumedMl = 0;
        this.lastUpdated = System.currentTimeMillis();
    }

    public synchronized void addWater(int amountMl) throws InvalidHealthDataException {
        if (amountMl <= 0) {
            throw new InvalidHealthDataException("amountMl", amountMl, "Added water must be a positive number.");
        }
        this.consumedMl += amountMl;
        this.lastUpdated = System.currentTimeMillis();
    }

    public synchronized void setDailyTargetMl(int dailyTargetMl) throws InvalidHealthDataException {
        if (dailyTargetMl <= 0) {
            throw new InvalidHealthDataException("dailyTargetMl", dailyTargetMl, "Daily target must be greater than 0.");
        }
        this.dailyTargetMl = dailyTargetMl;
        this.lastUpdated = System.currentTimeMillis();
    }

    public synchronized void reset() {
        this.consumedMl = 0;
        this.lastUpdated = System.currentTimeMillis();
    }

    public int getDailyTargetMl() {
        return dailyTargetMl;
    }

    public double getDailyTargetLiters() {
        return Math.round((dailyTargetMl / 1000.0) * 10.0) / 10.0;
    }

    public int getConsumedMl() {
        return consumedMl;
    }

    public double getConsumedLiters() {
        return Math.round((consumedMl / 1000.0) * 10.0) / 10.0;
    }

    public int getRemainingMl() {
        return Math.max(0, dailyTargetMl - consumedMl);
    }

    public double getRemainingLiters() {
        return Math.round((getRemainingMl() / 1000.0) * 10.0) / 10.0;
    }

    public double getProgressPercentage() {
        if (dailyTargetMl == 0) return 0.0;
        double pct = ((double) consumedMl / dailyTargetMl) * 100.0;
        return Math.round(pct * 10.0) / 10.0;
    }

    public int getGlassesConsumed() {
        return consumedMl / STANDARD_GLASS_ML;
    }

    public int getGlassesRemaining() {
        return (int) Math.ceil((double) getRemainingMl() / STANDARD_GLASS_ML);
    }

    public int getTotalTargetGlasses() {
        return (int) Math.ceil((double) dailyTargetMl / STANDARD_GLASS_ML);
    }

    public long getLastUpdated() {
        return lastUpdated;
    }
}

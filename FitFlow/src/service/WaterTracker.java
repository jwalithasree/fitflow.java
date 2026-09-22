package service;

import exception.InvalidHealthDataException;
import model.HydrationLog;

/**
 * FitFlow Academic AOP Project
 * Dedicated Water Tracking Service.
 *
 * OOP Concept: Encapsulation of stateful tracking logic and business rules.
 * All computations (remaining, percentages, glasses) are performed in Java.
 */
public class WaterTracker {
    private HydrationLog log;

    public WaterTracker(int initialTargetMl) throws InvalidHealthDataException {
        this.log = new HydrationLog(initialTargetMl > 0 ? initialTargetMl : 2500);
    }

    public synchronized void setTargetMl(int targetMl) throws InvalidHealthDataException {
        if (targetMl <= 0) {
            throw new InvalidHealthDataException("targetMl", targetMl, "Target water intake must be greater than zero.");
        }
        log.setDailyTargetMl(targetMl);
    }

    public synchronized void logGlass() throws InvalidHealthDataException {
        log.addWater(HydrationLog.STANDARD_GLASS_ML);
    }

    public synchronized void logCustomAmount(int amountMl) throws InvalidHealthDataException {
        if (amountMl <= 0) {
            throw new InvalidHealthDataException("amountMl", amountMl, "Amount to add must be positive.");
        }
        log.addWater(amountMl);
    }

    public synchronized void resetDaily() {
        log.reset();
    }

    public HydrationLog getLog() {
        return log;
    }
}

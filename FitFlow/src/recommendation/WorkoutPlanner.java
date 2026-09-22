package recommendation;

import model.Exercise;
import model.HealthProfile;
import model.enums.BMICategory;
import model.enums.EnergyLevel;
import model.enums.FitnessGoal;

/**
 * FitFlow Academic AOP Project
 * Dedicated Workout Recommendation Planner.
 *
 * OOP Concept: Factory & Strategy patterns.
 * Generates tailored polymorphic WorkoutRoutine objects according to
 * multi-factorial inputs: Goal, Energy Level, Available Time, and BMI Category.
 */
public class WorkoutPlanner {

    /**
     * Generates a customized workout routine based on user constraints.
     *
     * @param profile User's health profile (for Goal, BMI, Weight)
     * @param durationMinutes Available time: 10, 20, 30, 45, or 60 minutes
     * @param energyLevel Energy level: LOW, MEDIUM, or HIGH
     * @return Fully populated WorkoutRoutine polymorphic instance
     */
    public WorkoutRoutine createWorkoutPlan(HealthProfile profile, int durationMinutes, EnergyLevel energyLevel) {
        FitnessGoal goal = (profile != null) ? profile.getFitnessGoal() : FitnessGoal.GENERAL_FITNESS;
        double weight = (profile != null) ? profile.getWeightKg() : 70.0;
        double height = (profile != null) ? profile.getHeightCm() : 170.0;
        BMICategory bmiCat = BMICategory.classify(weight / ((height / 100.0) * (height / 100.0)));
        EnergyLevel energy = (energyLevel != null) ? energyLevel : EnergyLevel.MEDIUM;

        // Normalize duration to closest supported tier (10, 20, 30, 45, 60)
        int duration = normalizeDuration(durationMinutes);

        // Core Selection Logic based on Energy Level & Duration:
        if (energy == EnergyLevel.LOW) {
            return buildLowEnergyRoutine(goal, duration, bmiCat);
        } else if (energy == EnergyLevel.MEDIUM) {
            return buildMediumEnergyRoutine(goal, duration, bmiCat);
        } else {
            return buildHighEnergyRoutine(goal, duration, bmiCat);
        }
    }

    private int normalizeDuration(int minutes) {
        if (minutes <= 15) return 10;
        if (minutes <= 25) return 20;
        if (minutes <= 35) return 30;
        if (minutes <= 50) return 45;
        return 60;
    }

    /**
     * Low Energy Tier:
     * Focuses on mobility, dynamic stretching, posture, joint health, and restorative walking.
     */
    private WorkoutRoutine buildLowEnergyRoutine(FitnessGoal goal, int duration, BMICategory bmiCat) {
        FlexibilityRoutine routine = new FlexibilityRoutine(
                "ROUTINE-LOW-" + duration,
                "Gentle Flow & Restorative Mobility (" + duration + " min)",
                goal,
                duration,
                "Gentle / Restorative",
                "Full-body articular rotations and mindful thoracic breathing",
                "5 min arm circles, neck mobility, and deep nasal breathing",
                "Hamstring reach and restorative child's pose"
        );

        routine.addExercise(new Exercise("Cat-Cow Spinal Mobility", "Mobility", 3, "10 slow cycles",
                "Spine & Thoracic Cage", "Synchronize inhale with spinal arch, exhale with full hollow curve."));
        routine.addExercise(new Exercise("Dynamic World's Greatest Stretch", "Mobility", 3, "5 reps/side",
                "Hips, Groin & Thoracic", "Step into a deep lunge, rotate elbow to floor then gaze towards sky."));

        if (duration >= 20) {
            routine.addExercise(new Exercise("Bird-Dog Stability Hold", "Core Stability", 3, "30 sec hold/side",
                    "Lower Back & Glutes", "Extend opposite arm and leg parallel to ground with braced core."));
            routine.addExercise(new Exercise("Wall Angels & Posture Opener", "Postural", 3, "12 slow reps",
                    "Upper Back & Rhomboids", "Keep elbows, wrists, and lower back firmly against the wall."));
        }

        if (duration >= 30) {
            routine.addExercise(new Exercise("Gentle Walking Lunge Flow", "Low-Impact Cardio", 3, "12 paces",
                    "Quads & Hip Flexors", "Gentle stride with upright torso, focusing on joint lubrication."));
            routine.addExercise(new Exercise("Glute Bridges with 2-sec Pause", "Glute Activation", 3, "15 reps",
                    "Glutes & Hamstrings", "Drive through heels, squeeze glutes at the top without hyperextending back."));
        }

        if (duration >= 45) {
            routine.addExercise(new Exercise("Seated Butterfly & Piriformis Stretch", "Restorative", 3, "45 sec hold",
                    "Outer Hips & Pelvis", "Breathe smoothly into any pelvic tension."));
            routine.addExercise(new Exercise("Dead Bug Neutral Spine Drills", "Core Activation", 3, "10 reps/side",
                    "Transverse Abdominis", "Keep lower back flat into the mat as opposite limbs extend slowly."));
        }

        return routine;
    }

    /**
     * Medium Energy Tier:
     * Balanced bodyweight, strength endurance, core progression, and functional circuits.
     */
    private WorkoutRoutine buildMediumEnergyRoutine(FitnessGoal goal, int duration, BMICategory bmiCat) {
        // If the user's goal is muscle building or strength, provide StrengthRoutine
        if (goal == FitnessGoal.MUSCLE_BUILDING || goal == FitnessGoal.WEIGHT_GAIN) {
            StrengthRoutine routine = new StrengthRoutine(
                    "ROUTINE-MED-STR-" + duration,
                    "Total-Body Progressive Calisthenics (" + duration + " min)",
                    goal,
                    duration,
                    "Moderate / Intermediate",
                    "Full Body Compound",
                    "3 min jumping jacks and dynamic arm swings",
                    "Passive shoulder hang and quad stretch"
            );

            routine.addExercise(new Exercise("Tempo Bodyweight Squats", "Legs Compound", 3, "15 reps (3s down)",
                    "Quadriceps & Glutes", "Slow eccentric descent, pause for 1s at bottom, explosively stand."));
            routine.addExercise(new Exercise("Standard Push-Ups (or Incline)", "Upper Push", 3, "12 reps",
                    "Chest, Shoulders & Triceps", "Maintain rigid plank line, elbows tracking back at 45 degrees."));
            routine.addExercise(new Exercise("Reverse Lunges", "Lower Unilateral", 3, "10 reps/leg",
                    "Glutes & Hamstrings", "Step backward smoothly, keep front knee stacked above ankle."));

            if (duration >= 20) {
                routine.addExercise(new Exercise("Inverted Rows / Doorframe Rows", "Upper Pull", 3, "12 reps",
                        "Lats, Rhomboids & Biceps", "Pull chest toward hands while engaging shoulder blades."));
                routine.addExercise(new Exercise("Forearm Plank to Pike", "Core Integration", 3, "40 seconds",
                        "Core & Shoulders", "Hold hollow plank, then press hips up into pike position and return."));
            }

            if (duration >= 30) {
                routine.addExercise(new Exercise("Pike Push-Ups", "Shoulder Strength", 3, "8-10 reps",
                        "Anterior Deltoids & Triceps", "Hips high in pike position, lower crown of head forward."));
                routine.addExercise(new Exercise("Single-Leg Romanian Deadlift", "Posterior Chain", 3, "10 reps/leg",
                        "Hamstrings & Balance", "Hinge at the hips with a flat back, extending rear leg straight."));
            }

            if (duration >= 45) {
                routine.addExercise(new Exercise("Diamond Push-Ups", "Arm Hypertrophy", 3, "10 reps",
                        "Triceps & Inner Chest", "Place thumbs and index fingers together under sternum."));
                routine.addExercise(new Exercise("Hollow Body Rockers", "Gymnastic Core", 3, "30 seconds",
                        "Deep Abdominals", "Lock ribs to pelvis and rock gently with pointed toes."));
            }

            return routine;
        } else {
            // General fitness or weight loss: Cardio / Metabolic Conditioning
            CardioRoutine routine = new CardioRoutine(
                    "ROUTINE-MED-CARDIO-" + duration,
                    "Metabolic Cardio & Core Burn (" + duration + " min)",
                    goal,
                    duration,
                    "Moderate Intensity",
                    "Aerobic Capacity & Fat Oxidation",
                    "3 min march-in-place and high knees",
                    "Calf stretch and cobra abdominal release"
            );

            routine.addExercise(new Exercise("Speed Skater Hops", "Cardio Plyo", 3, "40 seconds",
                    "Legs & Lateral Agility", "Bound laterally from side to side, absorbing landing smoothly."));
            routine.addExercise(new Exercise("Mountain Climbers", "Core Cardio", 3, "45 seconds",
                    "Core & Cardiovascular", "Drive knees rhythmically into chest with stable shoulders."));
            routine.addExercise(new Exercise("Prisoner Air Squats", "Lower Endurance", 3, "20 reps",
                    "Quads & Cardiovascular", "Keep hands behind head and maintain a high chest tempo."));

            if (duration >= 20) {
                routine.addExercise(new Exercise("Plank Shoulder Taps", "Anti-Rotational Core", 3, "20 taps",
                        "Core & Deltoids", "Tap opposite shoulder without letting hips sway side to side."));
                routine.addExercise(new Exercise("Jumping Jacks with Squat Pulse", "Metabolic Combo", 3, "45 seconds",
                        "Full Body & Heart Rate", "Jump feet wide, drop into quarter squat, jump together."));
            }

            if (duration >= 30) {
                routine.addExercise(new Exercise("Step-Ups or High Knee Marches", "Leg Conditioning", 3, "15 reps/leg",
                        "Quads & Glutes", "Step forcefully onto a sturdy bench or chair, controlling descent."));
                routine.addExercise(new Exercise("Bicycle Crunches", "Core Rotation", 3, "20 total reps",
                        "Obliques & Rectus Abdominis", "Slow, deliberate rotation touching opposite elbow to knee."));
            }

            if (duration >= 45) {
                routine.addExercise(new Exercise("Shadow Boxing Combinations", "Upper Body Cardio", 3, "60 seconds",
                        "Shoulders & Stamina", "Jab, cross, hook rhythmically while constantly bouncing on toes."));
                routine.addExercise(new Exercise("Side Plank with Hip Dips", "Lateral Core", 3, "12 reps/side",
                        "Obliques & Quadratus Lumborum", "Lower hip slightly toward mat then drive back up straight."));
            }

            return routine;
        }
    }

    /**
     * High Energy Tier:
     * High Intensity Interval Training (HIIT) or Heavy Hypertrophy Circuits.
     */
    private WorkoutRoutine buildHighEnergyRoutine(FitnessGoal goal, int duration, BMICategory bmiCat) {
        if (goal == FitnessGoal.WEIGHT_LOSS || duration <= 30) {
            HIITRoutine routine = new HIITRoutine(
                    "ROUTINE-HIGH-HIIT-" + duration,
                    "High-Octane Tabata & HIIT Blast (" + duration + " min)",
                    goal,
                    duration,
                    "High Intensity / Advanced",
                    40, 20, // 40 seconds work, 20 seconds rest
                    "Dynamic leg swings, torso twists, and seal jacks for 4 min",
                    "Deep breathing child's pose and foam-rolling simulation"
            );

            routine.addExercise(new Exercise("Burpees with Chest-to-Floor", "Full Body Power", 4, "40s on / 20s off",
                    "Chest, Quads, Core & Lungs", "Drop chest to floor, explode upward with overhead clap."));
            routine.addExercise(new Exercise("Jump Squats (Explosive)", "Lower Body Plyometric", 4, "40s on / 20s off",
                    "Glutes & Quads", "Sink deep, load hips, and jump forcefully with soft landing."));
            routine.addExercise(new Exercise("High-Intensity Mountain Climbers", "Cardio Sprint", 4, "40s on / 20s off",
                    "Core & Cardiovascular", "Maximum speed knee drives while locking upper torso."));

            if (duration >= 20) {
                routine.addExercise(new Exercise("Tuck Jumps or Power Skips", "Explosive Plyo", 4, "30s on / 30s off",
                        "Hip Flexors & Calves", "Drive both knees upward to chest height at the apex."));
                routine.addExercise(new Exercise("Push-Up to Renegade Row", "Compound Hypertrophy", 4, "40s on / 20s off",
                        "Chest, Back & Core", "Perform push-up then pull elbow high without rotating hips."));
            }

            if (duration >= 30) {
                routine.addExercise(new Exercise("Broad Jumps into Reverse Bear Crawl", "Agility & Power", 3, "45s on / 15s off",
                        "Full Body Coordination", "Leap forward explosively, land softly, crawl backwards into reset."));
                routine.addExercise(new Exercise("V-Up Explosive Crunches", "Core Compression", 3, "40s on / 20s off",
                        "Rectus Abdominis", "Simultaneously lift torso and straight legs to touch toes at apex."));
            }

            if (duration >= 45) {
                routine.addExercise(new Exercise("Thrusters (Bodyweight or DB)", "Metabolic Compound", 4, "45s on / 15s off",
                        "Quads, Shoulders & Triceps", "Deep front squat seamlessly transitioning into overhead press."));
                routine.addExercise(new Exercise("Sprint Shuttle Intervals", "Anaerobic Capacity", 4, "30s sprint / 30s rest",
                        "Cardiovascular Endurance", "Rapid directional change sprints at 90-95% maximal effort."));
            }

            return routine;
        } else {
            // High energy + longer time + muscle building: High Intensity Strength
            StrengthRoutine routine = new StrengthRoutine(
                    "ROUTINE-HIGH-STR-" + duration,
                    "Apex Hypertrophy & Strength Circuit (" + duration + " min)",
                    goal,
                    duration,
                    "Intense Resistance",
                    "Full Body Power & Hypertrophy",
                    "Joint mobilization, wrist prep, and 3 min cardio warmup",
                    "Static chest doorway stretch, pigeon pose, and rehydration"
            );

            routine.addExercise(new Exercise("Bulgarian Split Squats", "Quad & Glute Power", 4, "12 reps/leg",
                    "Quadriceps & Glute Medius", "Rear foot elevated on bench/chair, lower until front thigh is parallel."));
            routine.addExercise(new Exercise("Decline / Archer Push-Ups", "Chest & Triceps", 4, "10-12 reps",
                    "Pectorals & Triceps", "Feet elevated on platform for greater upper-chest recruitment."));
            routine.addExercise(new Exercise("Chin-Ups or Heavy Bodyweight Pull-Ups", "Back & Biceps", 4, "8-10 reps",
                    "Latissimus Dorsi & Biceps", "Full hang at bottom, clear chin over bar with controlled eccentric."));

            if (duration >= 45) {
                routine.addExercise(new Exercise("Single-Leg Calf Raises with Pause", "Lower Chain", 4, "15 reps/leg",
                        "Gastrocnemius & Soleus", "Drive high onto big toe, hold for 2s, 3s descent."));
                routine.addExercise(new Exercise("Pike Handstand Push-Up Progression", "Shoulder Hypertrophy", 4, "8-10 reps",
                        "Deltoids & Trapezius", "Elevate feet on bench, lower crown of head towards floor."));
                routine.addExercise(new Exercise("Hanging Knee-to-Elbows", "Advanced Core", 4, "12 reps",
                        "Lower Abs & Grip Strength", "Hang from bar and curl pelvis until knees contact elbows."));
            }

            if (duration >= 60) {
                routine.addExercise(new Exercise("Walking Lunge Burnout", "Finisher", 3, "25 paces",
                        "Full Lower Body", "Continuous non-stop lunges focusing on mind-muscle connection."));
                routine.addExercise(new Exercise("Tabata Core Finisher (Hollow Hold / Plank)", "Endurance", 1, "4 min circuit",
                        "Total Core", "Alternating 20s hollow hold and 20s elbow plank for 8 rounds."));
            }

            return routine;
        }
    }
}

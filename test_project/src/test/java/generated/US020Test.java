package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US020Test {

private HabitTracker habitTracker;

    @BeforeEach
    public void setUp() {
        habitTracker = new HabitTracker();
    }

    @Test
    public void US020_EnsurePastRecordsRemainAvailable() {
        // Create a habit
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");

        // Mark the habit as completed
        habitTracker.markHabitCompleted("Drink Water");

        // Edit the habit's description
        habitTracker.editHabitDescription("Drink Water", "Drink 8 glasses of water daily (updated)");

        // Edit the habit's category
        habitTracker.editHabitCategory("Drink Water", "Wellness");

        // Verify that the past record remains available
        assertTrue(habitTracker.isHabitCompleted("Drink Water"));
    }

    @Test
    public void US020_EnsurePastRecordsRemainAvailableAfterDeletingAndRecreatingHabit() {
        // Create a habit
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");

        // Mark the habit as completed
        habitTracker.markHabitCompleted("Drink Water");

        // Delete the habit
        habitTracker.deleteHabit("Drink Water");

        // Recreate the habit with the same name
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily (recreated)");

        // Verify that the past record remains available
        assertTrue(habitTracker.isHabitCompleted("Drink Water"));
    }

    @Test
    public void US020_EnsurePastRecordsRemainAvailableAfterEditingHabitName() {
        // Create a habit
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");

        // Mark the habit as completed
        habitTracker.markHabitCompleted("Drink Water");

        // Edit the habit's name
        habitTracker.editHabitName("Drink Water", "Stay Hydrated");

        // Verify that the past record remains available
        assertTrue(habitTracker.isHabitCompleted("Stay Hydrated"));
    }

}

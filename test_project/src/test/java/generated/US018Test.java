package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US018Test {

private HabitTracker habitTracker;

    @BeforeEach
    public void setUp() {
        habitTracker = new HabitTracker();
    }

    @Test
    public void US018_StatisticsUpdateImmediatelyAfterCompletion() {
        // Create a habit
        habitTracker.createHabit("Drink Water", "Daily");

        // Mark the habit as completed
        habitTracker.markHabitAsCompleted("Drink Water");

        // Verify statistics update immediately
        assertEquals(1, habitTracker.getTotalCompletions());
        assertEquals(1, habitTracker.getCurrentStreak());
        assertEquals(1, habitTracker.getLongestStreak());
    }

    @Test
    public void US018_StatisticsUpdateImmediatelyAfterDeletion() {
        // Create a habit
        habitTracker.createHabit("Drink Water", "Daily");

        // Mark the habit as completed
        habitTracker.markHabitAsCompleted("Drink Water");

        // Delete the habit
        habitTracker.deleteHabit("Drink Water");

        // Verify statistics update immediately
        assertEquals(0, habitTracker.getTotalCompletions());
        assertEquals(0, habitTracker.getCurrentStreak());
        assertEquals(0, habitTracker.getLongestStreak());
    }

    @Test
    public void US018_StatisticsUpdateImmediatelyAfterEdit() {
        // Create a habit
        habitTracker.createHabit("Drink Water", "Daily");

        // Mark the habit as completed
        habitTracker.markHabitAsCompleted("Drink Water");

        // Edit the habit
        habitTracker.editHabit("Drink Water", "Drink Water", "Weekly");

        // Verify statistics update immediately
        assertEquals(1, habitTracker.getTotalCompletions());
        assertEquals(1, habitTracker.getCurrentStreak());
        assertEquals(1, habitTracker.getLongestStreak());
    }

    @Test
    public void US018_StatisticsUpdateImmediatelyAfterUndo() {
        // Create a habit
        habitTracker.createHabit("Drink Water", "Daily");

        // Mark the habit as completed
        habitTracker.markHabitAsCompleted("Drink Water");

        // Undo the completion
        habitTracker.undoCompletion("Drink Water");

        // Verify statistics update immediately
        assertEquals(0, habitTracker.getTotalCompletions());
        assertEquals(0, habitTracker.getCurrentStreak());
        assertEquals(0, habitTracker.getLongestStreak());
    }

}

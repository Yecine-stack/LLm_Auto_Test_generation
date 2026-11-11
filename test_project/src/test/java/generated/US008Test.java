package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US008Test {

private HabitTracker habitTracker;

    @BeforeEach
    public void setUp() {
        habitTracker = new HabitTracker();
    }

    @Test
    public void US008_PreventMultipleCompletionsOnSameDay() {
        // Create a habit
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");

        // Mark the habit as completed for today
        habitTracker.markHabitCompleted("Drink Water");

        // Attempt to mark the habit as completed again for today
        habitTracker.markHabitCompleted("Drink Water");

        // Verify that the habit is not marked as completed again
        assertFalse(habitTracker.isHabitCompletedToday("Drink Water"));
    }

    @Test
    public void US008_PreventMultipleCompletionsOnSameDay_WithDifferentHabit() {
        // Create two habits
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        habitTracker.createHabit("Read Book", "Personal Development", "Daily", "Read for at least 30 minutes daily");

        // Mark the first habit as completed for today
        habitTracker.markHabitCompleted("Drink Water");

        // Mark the second habit as completed for today
        habitTracker.markHabitCompleted("Read Book");

        // Verify that both habits are marked as completed for today
        assertTrue(habitTracker.isHabitCompletedToday("Drink Water"));
        assertTrue(habitTracker.isHabitCompletedToday("Read Book"));
    }

    @Test
    public void US008_PreventMultipleCompletionsOnSameDay_WithDifferentDays() {
        // Create a habit
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");

        // Mark the habit as completed for today
        habitTracker.markHabitCompleted("Drink Water");

        // Mark the habit as completed for tomorrow
        habitTracker.markHabitCompleted("Drink Water");

        // Verify that the habit is marked as completed for both today and tomorrow
        assertTrue(habitTracker.isHabitCompletedToday("Drink Water"));
        assertTrue(habitTracker.isHabitCompletedTomorrow("Drink Water"));
    }

    @Test
    public void US008_PreventMultipleCompletionsOnSameDay_WithInvalidHabitName() {
        // Attempt to mark an invalid habit name as completed
        habitTracker.markHabitCompleted("Invalid Habit Name");

        // Verify that the habit is not marked as completed
        assertFalse(habitTracker.isHabitCompletedToday("Invalid Habit Name"));
    }

}

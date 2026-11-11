package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US007Test {

private HabitTracker habitTracker;

    @BeforeEach
    public void setUp() {
        habitTracker = new HabitTracker();
    }

    @Test
    public void US007_undosTodayCompletion() {
        // Create a habit
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink at least 8 glasses of water daily");

        // Mark the habit as completed
        habitTracker.markHabitCompleted("Drink Water");

        // Undo the completion
        habitTracker.undoTodayCompletion("Drink Water");

        // Verify the habit is not marked as completed
        assertFalse(habitTracker.isHabitCompletedToday("Drink Water"));
    }

    @Test
    public void US007_cannotUndoCompletionIfNotCompleted() {
        // Create a habit
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink at least 8 glasses of water daily");

        // Attempt to undo the completion
        habitTracker.undoTodayCompletion("Drink Water");

        // Verify the habit is not marked as completed
        assertFalse(habitTracker.isHabitCompletedToday("Drink Water"));
    }

    @Test
    public void US007_cannotUndoCompletionForNonExistentHabit() {
        // Attempt to undo the completion for a non-existent habit
        habitTracker.undoTodayCompletion("NonExistentHabit");

        // Verify no exception is thrown
        // (Assuming HabitTracker handles this gracefully)
    }

    @Test
    public void US007_cannotUndoCompletionIfReminderDisabled() {
        // Disable reminders
        habitTracker.setReminderEnabled(false);

        // Create a habit
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink at least 8 glasses of water daily");

        // Mark the habit as completed
        habitTracker.markHabitCompleted("Drink Water");

        // Undo the completion
        habitTracker.undoTodayCompletion("Drink Water");

        // Verify the habit is not marked as completed
        assertFalse(habitTracker.isHabitCompletedToday("Drink Water"));
    }

}

package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class US030Test {

@Test
    public void US030_CreateHabit_Success() {
        // Assume HabitTracker class has a method createHabit
        HabitTracker tracker = new HabitTracker();
        boolean result = tracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        assertTrue(result, "Habit creation should succeed");
    }

    @Test
    public void US030_CreateHabit_DuplicateName_Failure() {
        HabitTracker tracker = new HabitTracker();
        tracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        boolean result = tracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        assertFalse(result, "Habit creation with duplicate name should fail");
    }

    @Test
    public void US030_CreateHabit_EmptyName_Failure() {
        HabitTracker tracker = new HabitTracker();
        boolean result = tracker.createHabit("", "Health", "Daily", "Stay hydrated");
        assertFalse(result, "Habit creation with empty name should fail");
    }

    @Test
    public void US030_CompleteHabit_Success() {
        HabitTracker tracker = new HabitTracker();
        tracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        boolean result = tracker.completeHabit("Drink Water");
        assertTrue(result, "Habit completion should succeed");
    }

    @Test
    public void US030_CompleteHabit_MultipleTimes_Failure() {
        HabitTracker tracker = new HabitTracker();
        tracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        tracker.completeHabit("Drink Water");
        boolean result = tracker.completeHabit("Drink Water");
        assertFalse(result, "Habit completion multiple times should fail");
    }

    @Test
    public void US030_CompleteHabit_Undo_Success() {
        HabitTracker tracker = new HabitTracker();
        tracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        tracker.completeHabit("Drink Water");
        boolean result = tracker.undoCompletion("Drink Water");
        assertTrue(result, "Habit undo completion should succeed");
    }

    @Test
    public void US030_CompleteHabit_InvalidHabit_Failure() {
        HabitTracker tracker = new HabitTracker();
        boolean result = tracker.completeHabit("Invalid Habit");
        assertFalse(result, "Habit completion with invalid habit should fail");
    }

    @Test
    public void US030_CompleteHabit_OldRecord_Failure() {
        HabitTracker tracker = new HabitTracker();
        tracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        tracker.completeHabit("Drink Water");
        // Simulate time passing
        try {
            Thread.sleep(8 * 24 * 60 * 60 * 1000); // 8 days
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean result = tracker.completeHabit("Drink Water");
        assertFalse(result, "Habit completion for old record should fail");
    }

}

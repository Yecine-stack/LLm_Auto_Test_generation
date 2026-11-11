package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US026Test {

private HabitTracker habitTracker;

    @BeforeEach
    public void setUp() {
        habitTracker = new HabitTracker();
    }

    @Test
    public void US026_RecoverPreviouslySavedData() {
        // Create a habit and save it
        habitTracker.createHabit("Drink Water", "Daily", null);
        habitTracker.saveData();

        // Simulate application restart
        habitTracker = new HabitTracker();

        // Load data and verify habit is recovered
        assertTrue(habitTracker.getHabit("Drink Water") != null);
    }

    @Test
    public void US026_RecoverMultipleHabits() {
        // Create multiple habits and save them
        habitTracker.createHabit("Drink Water", "Daily", null);
        habitTracker.createHabit("Exercise", "Weekly", null);
        habitTracker.saveData();

        // Simulate application restart
        habitTracker = new HabitTracker();

        // Load data and verify all habits are recovered
        assertTrue(habitTracker.getHabit("Drink Water") != null);
        assertTrue(habitTracker.getHabit("Exercise") != null);
    }

    @Test
    public void US026_RecoverEmptyData() {
        // Simulate application restart
        habitTracker = new HabitTracker();

        // Load data and verify no habits are recovered
        assertNull(habitTracker.getHabit("Drink Water"));
    }

    @Test
    public void US026_RecoverDataAfterEditing() {
        // Create a habit, edit it, and save
        habitTracker.createHabit("Drink Water", "Daily", null);
        habitTracker.editHabit("Drink Water", "Drink Water", "Weekly", null);
        habitTracker.saveData();

        // Simulate application restart
        habitTracker = new HabitTracker();

        // Load data and verify habit is recovered with edited details
        Habit habit = habitTracker.getHabit("Drink Water");
        assertNotNull(habit);
        assertEquals("Drink Water", habit.getName());
        assertEquals("Weekly", habit.getFrequency());
    }

    @Test
    public void US026_RecoverDataAfterDeleting() {
        // Create a habit, delete it, and save
        habitTracker.createHabit("Drink Water", "Daily", null);
        habitTracker.deleteHabit("Drink Water");
        habitTracker.saveData();

        // Simulate application restart
        habitTracker = new HabitTracker();

        // Load data and verify habit is not recovered
        assertNull(habitTracker.getHabit("Drink Water"));
    }

}

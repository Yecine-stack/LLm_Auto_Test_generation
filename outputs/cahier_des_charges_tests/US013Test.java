package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US013Test {

private HabitTracker habitTracker;

    @BeforeEach
    public void setUp() {
        habitTracker = new HabitTracker();
    }

    @Test
    public void US013_DisplayTotalActiveHabits() {
        // Create a habit
        habitTracker.createHabit("Exercise", "Fitness", HabitFrequency.DAILY);

        // Verify that the total active habits is 1
        assertEquals(1, habitTracker.getTotalActiveHabits());
    }

    @Test
    public void US013_DisplayTotalActiveHabits_WithMultipleHabits() {
        // Create multiple habits
        habitTracker.createHabit("Exercise", "Fitness", HabitFrequency.DAILY);
        habitTracker.createHabit("Read", "Personal Development", HabitFrequency.DAILY);

        // Verify that the total active habits is 2
        assertEquals(2, habitTracker.getTotalActiveHabits());
    }

    @Test
    public void US013_DisplayTotalActiveHabits_AfterDeletingHabit() {
        // Create a habit
        habitTracker.createHabit("Exercise", "Fitness", HabitFrequency.DAILY);

        // Delete the habit
        habitTracker.deleteHabit("Exercise");

        // Verify that the total active habits is 0
        assertEquals(0, habitTracker.getTotalActiveHabits());
    }

    @Test
    public void US013_DisplayTotalActiveHabits_WithInactiveHabit() {
        // Create a habit
        habitTracker.createHabit("Exercise", "Fitness", HabitFrequency.DAILY);

        // Mark the habit as completed
        habitTracker.markHabitAsCompleted("Exercise");

        // Verify that the total active habits is 0
        assertEquals(0, habitTracker.getTotalActiveHabits());
    }

}

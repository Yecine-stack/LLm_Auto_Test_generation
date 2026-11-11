package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;

public class US015Test {

private HabitTracker habitTracker;

    @BeforeEach
    public void setUp() {
        habitTracker = new HabitTracker();
    }

    @Test
    public void US015_DisplayHabitsCompletedToday_NoHabitsCompleted() {
        // Arrange
        // No habits are completed

        // Act
        int habitsCompletedToday = habitTracker.getHabitsCompletedToday();

        // Assert
        assertEquals(0, habitsCompletedToday);
    }

    @Test
    public void US015_DisplayHabitsCompletedToday_OneHabitCompleted() {
        // Arrange
        habitTracker.completeHabit("Exercise");
        habitTracker.completeHabit("Read");

        // Act
        int habitsCompletedToday = habitTracker.getHabitsCompletedToday();

        // Assert
        assertEquals(2, habitsCompletedToday);
    }

    @Test
    public void US015_DisplayHabitsCompletedToday_HabitCompletedYesterday() {
        // Arrange
        habitTracker.completeHabit("Exercise");
        habitTracker.completeHabit("Read");
        habitTracker.undoCompletion("Exercise");

        // Act
        int habitsCompletedToday = habitTracker.getHabitsCompletedToday();

        // Assert
        assertEquals(1, habitsCompletedToday);
    }

    @Test
    public void US015_DisplayHabitsCompletedToday_HabitCompletedToday() {
        // Arrange
        habitTracker.completeHabit("Exercise");
        habitTracker.completeHabit("Read");

        // Act
        int habitsCompletedToday = habitTracker.getHabitsCompletedToday();

        // Assert
        assertEquals(2, habitsCompletedToday);
    }

}

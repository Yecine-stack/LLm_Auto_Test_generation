package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US006Test {

private HabitTracker habitTracker;

    @BeforeEach
    public void setUp() {
        habitTracker = new HabitTracker();
    }

    @Test
    public void US006_markHabitAsCompleted_success() {
        // Arrange
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water per day");

        // Act
        habitTracker.markHabitAsCompleted("Drink Water");

        // Assert
        assertTrue(habitTracker.isHabitCompletedToday("Drink Water"));
    }

    @Test
    public void US006_markHabitAsCompleted_twice_sameDay_failure() {
        // Arrange
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water per day");
        habitTracker.markHabitAsCompleted("Drink Water");

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> habitTracker.markHabitAsCompleted("Drink Water"));
    }

    @Test
    public void US006_markHabitAsCompleted_invalidHabit_failure() {
        // Arrange
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water per day");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> habitTracker.markHabitAsCompleted("Invalid Habit"));
    }

    @Test
    public void US006_undoHabitCompletion_success() {
        // Arrange
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water per day");
        habitTracker.markHabitAsCompleted("Drink Water");

        // Act
        habitTracker.undoHabitCompletion("Drink Water");

        // Assert
        assertFalse(habitTracker.isHabitCompletedToday("Drink Water"));
    }

    @Test
    public void US006_undoHabitCompletion_invalidHabit_failure() {
        // Arrange
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water per day");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> habitTracker.undoHabitCompletion("Invalid Habit"));
    }

    @Test
    public void US006_undoHabitCompletion_neverCompleted_failure() {
        // Arrange
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water per day");

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> habitTracker.undoHabitCompletion("Drink Water"));
    }

}

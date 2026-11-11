package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US012Test {

private HabitTracker habitTracker;

    @BeforeEach
    public void setUp() {
        habitTracker = new HabitTracker();
    }

    @Test
    public void US012_calculateTotalCompletions_NoHabits() {
        // Arrange
        // No habits are created

        // Act
        int totalCompletions = habitTracker.calculateTotalCompletions();

        // Assert
        assertEquals(0, totalCompletions);
    }

    @Test
    public void US012_calculateTotalCompletions_OneHabitCompletedOnce() {
        // Arrange
        habitTracker.createHabit("Exercise", "daily");
        habitTracker.markHabitCompleted("Exercise");

        // Act
        int totalCompletions = habitTracker.calculateTotalCompletions();

        // Assert
        assertEquals(1, totalCompletions);
    }

    @Test
    public void US012_calculateTotalCompletions_OneHabitCompletedMultipleTimes() {
        // Arrange
        habitTracker.createHabit("Exercise", "daily");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise");

        // Act
        int totalCompletions = habitTracker.calculateTotalCompletions();

        // Assert
        assertEquals(2, totalCompletions);
    }

    @Test
    public void US012_calculateTotalCompletions_MultipleHabitsCompleted() {
        // Arrange
        habitTracker.createHabit("Exercise", "daily");
        habitTracker.createHabit("Read", "daily");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Read");

        // Act
        int totalCompletions = habitTracker.calculateTotalCompletions();

        // Assert
        assertEquals(2, totalCompletions);
    }

    @Test
    public void US012_calculateTotalCompletions_HabitNotCompleted() {
        // Arrange
        habitTracker.createHabit("Exercise", "daily");

        // Act
        int totalCompletions = habitTracker.calculateTotalCompletions();

        // Assert
        assertEquals(0, totalCompletions);
    }

}

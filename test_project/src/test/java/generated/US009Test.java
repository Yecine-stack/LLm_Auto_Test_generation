package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US009Test {

private HabitTracker habitTracker;

    @BeforeEach
    public void setUp() {
        habitTracker = new HabitTracker();
    }

    @Test
    public void US009_CompletionDateIsRecordedAutomatically() {
        // Arrange
        String habitName = "Drink Water";
        habitTracker.createHabit(habitName, "Daily", null);

        // Act
        habitTracker.markHabitCompleted(habitName);

        // Assert
        assertNotNull(habitTracker.getHabitCompletionDate(habitName));
    }

    @Test
    public void US009_CompletionDateIsNotOverridden() {
        // Arrange
        String habitName = "Drink Water";
        habitTracker.createHabit(habitName, "Daily", null);
        habitTracker.markHabitCompleted(habitName);

        // Act
        habitTracker.markHabitCompleted(habitName);

        // Assert
        assertEquals(1, habitTracker.getHabitCompletionCount(habitName));
    }

    @Test
    public void US009_CompletionDateIsNotRecordedForNonExistentHabit() {
        // Arrange
        String habitName = "Drink Water";

        // Act & Assert
        assertThrows(HabitNotFoundException.class, () -> habitTracker.markHabitCompleted(habitName));
    }

    @Test
    public void US009_CompletionDateIsNotRecordedForEmptyHabitName() {
        // Arrange
        String habitName = "";

        // Act & Assert
        assertThrows(InvalidHabitNameException.class, () -> habitTracker.markHabitCompleted(habitName));
    }

}

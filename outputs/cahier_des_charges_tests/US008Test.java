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
    public void US008_CompletionDateIsRecordedAutomatically() {
        // Arrange
        String habitName = "Drink Water";
        habitTracker.createHabit(habitName, null, HabitFrequency.DAILY, null);

        // Act
        habitTracker.markHabitCompleted(habitName);

        // Assert
        assertNotNull(habitTracker.getHabit(habitName).getCompletionDate());
    }

    @Test
    public void US008_CompletionDateIsNotRecordedForNonExistentHabit() {
        // Arrange
        String habitName = "Drink Water";

        // Act & Assert
        assertThrows(HabitNotFoundException.class, () -> habitTracker.markHabitCompleted(habitName));
    }

    @Test
    public void US008_CompletionDateIsNotRecordedForAlreadyCompletedHabit() {
        // Arrange
        String habitName = "Drink Water";
        habitTracker.createHabit(habitName, null, HabitFrequency.DAILY, null);
        habitTracker.markHabitCompleted(habitName);

        // Act & Assert
        assertThrows(HabitAlreadyCompletedException.class, () -> habitTracker.markHabitCompleted(habitName));
    }

}

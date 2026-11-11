package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US005Test {

private HabitManager habitManager;

    @BeforeEach
    public void setUp() {
        habitManager = new HabitManager();
    }

    @Test
    public void US005_createHabitWithEmptyNameThrowsException() {
        // Arrange
        String habitName = "";
        String category = "Exercise";
        String description = "Daily workout";
        HabitFrequency frequency = HabitFrequency.DAILY;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            habitManager.createHabit(habitName, category, description, frequency);
        });
    }

    @Test
    public void US005_createHabitWithNonEmptyNameSucceeds() {
        // Arrange
        String habitName = "Exercise";
        String category = "Fitness";
        String description = "Daily workout";
        HabitFrequency frequency = HabitFrequency.DAILY;

        // Act
        habitManager.createHabit(habitName, category, description, frequency);

        // Assert
        assertTrue(habitManager.getHabitByName(habitName).isPresent());
    }

    @Test
    public void US005_createHabitWithDuplicateNameThrowsException() {
        // Arrange
        String habitName = "Exercise";
        String category = "Fitness";
        String description = "Daily workout";
        HabitFrequency frequency = HabitFrequency.DAILY;

        habitManager.createHabit(habitName, category, description, frequency);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            habitManager.createHabit(habitName, category, description, frequency);
        });
    }

}

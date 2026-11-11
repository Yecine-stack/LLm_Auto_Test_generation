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
        Frequency frequency = Frequency.DAILY;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            habitManager.createHabit(habitName, category, frequency);
        });
    }

    @Test
    public void US005_createHabitWithValidNameSucceeds() {
        // Arrange
        String habitName = "Exercise";
        String category = "Health";
        Frequency frequency = Frequency.DAILY;

        // Act
        habitManager.createHabit(habitName, category, frequency);

        // Assert
        assertTrue(habitManager.getHabitByName(habitName).isPresent());
    }

    @Test
    public void US005_createDuplicateHabitThrowsException() {
        // Arrange
        String habitName = "Exercise";
        String category = "Health";
        Frequency frequency = Frequency.DAILY;
        habitManager.createHabit(habitName, category, frequency);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            habitManager.createHabit(habitName, category, frequency);
        });
    }

}

package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US003Test {

private HabitManager habitManager;

    @BeforeEach
    public void setUp() {
        habitManager = new HabitManager();
    }

    @Test
    public void US003_DeleteHabit_ExistingHabit() {
        // Arrange
        String habitName = "Exercise";
        habitManager.createHabit(habitName, null, Frequency.DAILY, null);

        // Act
        habitManager.deleteHabit(habitName);

        // Assert
        assertFalse(habitManager.getHabit(habitName).isPresent());
    }

    @Test
    public void US003_DeleteHabit_NonExistentHabit() {
        // Arrange
        String habitName = "NonExistentHabit";

        // Act & Assert
        assertThrows(HabitNotFoundException.class, () -> habitManager.deleteHabit(habitName));
    }

    @Test
    public void US003_DeleteHabit_NullHabitName() {
        // Arrange

        // Act & Assert
        assertThrows(NullPointerException.class, () -> habitManager.deleteHabit(null));
    }

    @Test
    public void US003_DeleteHabit_EmptyHabitName() {
        // Arrange

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> habitManager.deleteHabit(""));
    }

}

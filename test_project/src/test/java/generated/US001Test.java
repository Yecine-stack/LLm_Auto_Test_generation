package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US001Test {

private HabitManager habitManager;

    @BeforeEach
    public void setUp() {
        habitManager = new HabitManager();
    }

    @Test
    public void US001_CreateHabit_Success() {
        // Arrange
        String habitName = "Drink Water";
        String category = "Health";
        HabitFrequency frequency = HabitFrequency.DAILY;
        String description = "Drink 8 glasses of water per day";

        // Act
        habitManager.createHabit(habitName, category, frequency, description);

        // Assert
        assertTrue(habitManager.getHabitByName(habitName).isPresent());
    }

    @Test
    public void US001_CreateHabit_DuplicateName_Failure() {
        // Arrange
        String habitName = "Drink Water";
        String category = "Health";
        HabitFrequency frequency = HabitFrequency.DAILY;
        String description = "Drink 8 glasses of water per day";

        habitManager.createHabit(habitName, category, frequency, description);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            habitManager.createHabit(habitName, category, frequency, description);
        });
    }

    @Test
    public void US001_CreateHabit_EmptyName_Failure() {
        // Arrange
        String habitName = "";
        String category = "Health";
        HabitFrequency frequency = HabitFrequency.DAILY;
        String description = "Drink 8 glasses of water per day";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            habitManager.createHabit(habitName, category, frequency, description);
        });
    }

}

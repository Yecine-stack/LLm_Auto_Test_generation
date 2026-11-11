package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class US021Test {

@Test
    public void US021_ShouldRestrictEditingOfHistoricalRecords() {
        // Arrange
        HabitTracker habitTracker = new HabitTracker();
        habitTracker.createHabit("Drink Water", "Health", HabitFrequency.DAILY);
        habitTracker.markHabitCompleted("Drink Water");
        habitTracker.advanceDay();
        habitTracker.markHabitCompleted("Drink Water");

        // Act
        habitTracker.editHabitDescription("Drink Water", "Drink plenty of water");
        habitTracker.editHabitCategory("Drink Water", "Hydration");

        // Assert
        assertFalse(habitTracker.canEditHabitDescription("Drink Water", "Drink plenty of water"));
        assertFalse(habitTracker.canEditHabitCategory("Drink Water", "Hydration"));
    }

    @Test
    public void US021_ShouldAllowEditingOfRecentRecords() {
        // Arrange
        HabitTracker habitTracker = new HabitTracker();
        habitTracker.createHabit("Drink Water", "Health", HabitFrequency.DAILY);
        habitTracker.markHabitCompleted("Drink Water");
        habitTracker.advanceDay();

        // Act
        habitTracker.editHabitDescription("Drink Water", "Drink plenty of water");
        habitTracker.editHabitCategory("Drink Water", "Hydration");

        // Assert
        assertTrue(habitTracker.canEditHabitDescription("Drink Water", "Drink plenty of water"));
        assertTrue(habitTracker.canEditHabitCategory("Drink Water", "Hydration"));
    }

    @Test
    public void US021_ShouldPreventEditingOfRecordsOlderThanSevenDays() {
        // Arrange
        HabitTracker habitTracker = new HabitTracker();
        habitTracker.createHabit("Drink Water", "Health", HabitFrequency.DAILY);
        habitTracker.markHabitCompleted("Drink Water");
        habitTracker.advanceDay();
        habitTracker.advanceDay();
        habitTracker.advanceDay();
        habitTracker.advanceDay();
        habitTracker.advanceDay();
        habitTracker.advanceDay();

        // Act
        habitTracker.editHabitDescription("Drink Water", "Drink plenty of water");
        habitTracker.editHabitCategory("Drink Water", "Hydration");

        // Assert
        assertFalse(habitTracker.canEditHabitDescription("Drink Water", "Drink plenty of water"));
        assertFalse(habitTracker.canEditHabitCategory("Drink Water", "Hydration"));
    }

}

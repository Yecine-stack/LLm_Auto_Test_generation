package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class US015Test {

@Test
    public void US015_DisplayHabitsMissedToday() {
        // Arrange
        HabitTracker habitTracker = new HabitTracker();
        habitTracker.createHabit("Drink Water", "Health", HabitFrequency.DAILY);
        habitTracker.createHabit("Read a Book", "Personal Development", HabitFrequency.DAILY);
        habitTracker.markHabitCompleted("Drink Water");
        habitTracker.markHabitCompleted("Read a Book");

        // Act
        habitTracker.markHabitCompleted("Drink Water"); // Duplicate completion
        habitTracker.markHabitMissed("Read a Book");

        // Assert
        assertEquals(1, habitTracker.getHabitsMissedToday());
    }

    @Test
    public void US015_DisplayHabitsMissedToday_NoMissedHabits() {
        // Arrange
        HabitTracker habitTracker = new HabitTracker();
        habitTracker.createHabit("Drink Water", "Health", HabitFrequency.DAILY);
        habitTracker.createHabit("Read a Book", "Personal Development", HabitFrequency.DAILY);
        habitTracker.markHabitCompleted("Drink Water");
        habitTracker.markHabitCompleted("Read a Book");

        // Act
        habitTracker.markHabitCompleted("Drink Water"); // Duplicate completion

        // Assert
        assertEquals(0, habitTracker.getHabitsMissedToday());
    }

    @Test
    public void US015_DisplayHabitsMissedToday_AllHabitsCompleted() {
        // Arrange
        HabitTracker habitTracker = new HabitTracker();
        habitTracker.createHabit("Drink Water", "Health", HabitFrequency.DAILY);
        habitTracker.createHabit("Read a Book", "Personal Development", HabitFrequency.DAILY);
        habitTracker.markHabitCompleted("Drink Water");
        habitTracker.markHabitCompleted("Read a Book");

        // Act
        habitTracker.markHabitCompleted("Drink Water");
        habitTracker.markHabitCompleted("Read a Book");

        // Assert
        assertEquals(0, habitTracker.getHabitsMissedToday());
    }

    @Test
    public void US015_DisplayHabitsMissedToday_InvalidHabitName() {
        // Arrange
        HabitTracker habitTracker = new HabitTracker();
        habitTracker.createHabit("Drink Water", "Health", HabitFrequency.DAILY);
        habitTracker.createHabit("Read a Book", "Personal Development", HabitFrequency.DAILY);
        habitTracker.markHabitCompleted("Drink Water");
        habitTracker.markHabitCompleted("Read a Book");

        // Act
        habitTracker.markHabitMissed("Invalid Habit");

        // Assert
        assertEquals(0, habitTracker.getHabitsMissedToday());
    }

}

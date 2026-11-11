package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;

public class US016Test {

private HabitTracker habitTracker;

    @BeforeEach
    public void setUp() {
        habitTracker = new HabitTracker();
    }

    @Test
    public void US016_DisplayHabitsMissedToday_NoMissedHabits() {
        // Arrange
        habitTracker.createHabit("Drink Water", "Daily", "Stay hydrated");
        habitTracker.markHabitCompleted("Drink Water");

        // Act
        int missedHabits = habitTracker.getHabitsMissedToday();

        // Assert
        assertEquals(0, missedHabits);
    }

    @Test
    public void US016_DisplayHabitsMissedToday_OneMissedHabit() {
        // Arrange
        habitTracker.createHabit("Drink Water", "Daily", "Stay hydrated");
        habitTracker.createHabit("Exercise", "Daily", "Stay fit");

        // Act
        int missedHabits = habitTracker.getHabitsMissedToday();

        // Assert
        assertEquals(1, missedHabits);
    }

    @Test
    public void US016_DisplayHabitsMissedToday_MultipleMissedHabits() {
        // Arrange
        habitTracker.createHabit("Drink Water", "Daily", "Stay hydrated");
        habitTracker.createHabit("Exercise", "Daily", "Stay fit");
        habitTracker.createHabit("Read Book", "Daily", "Expand knowledge");

        // Act
        int missedHabits = habitTracker.getHabitsMissedToday();

        // Assert
        assertEquals(3, missedHabits);
    }

    @Test
    public void US016_DisplayHabitsMissedToday_HabitCompletedToday() {
        // Arrange
        habitTracker.createHabit("Drink Water", "Daily", "Stay hydrated");
        habitTracker.markHabitCompleted("Drink Water");

        // Act
        int missedHabits = habitTracker.getHabitsMissedToday();

        // Assert
        assertEquals(0, missedHabits);
    }

    @Test
    public void US016_DisplayHabitsMissedToday_HabitCompletedYesterday() {
        // Arrange
        habitTracker.createHabit("Drink Water", "Daily", "Stay hydrated");
        habitTracker.markHabitCompleted("Drink Water");
        habitTracker.undoCompletion("Drink Water");

        // Act
        int missedHabits = habitTracker.getHabitsMissedToday();

        // Assert
        assertEquals(1, missedHabits);
    }

}

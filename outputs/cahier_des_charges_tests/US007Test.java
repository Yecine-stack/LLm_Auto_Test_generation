package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US007Test {

private HabitTracker habitTracker;

    @BeforeEach
    public void setUp() {
        habitTracker = new HabitTracker();
    }

    @Test
    public void US007_undosTodayCompletion() {
        // Arrange
        String habitName = "Drink Water";
        habitTracker.createHabit(habitName, null, HabitFrequency.DAILY, null);
        habitTracker.markHabitCompleted(habitName);

        // Act
        habitTracker.undoTodayCompletion(habitName);

        // Assert
        assertFalse(habitTracker.isHabitCompletedToday(habitName));
    }

    @Test
    public void US007_cannotUndoCompletionIfNotCompleted() {
        // Arrange
        String habitName = "Drink Water";
        habitTracker.createHabit(habitName, null, HabitFrequency.DAILY, null);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> habitTracker.undoTodayCompletion(habitName));
    }

    @Test
    public void US007_cannotUndoCompletionIfMoreThanOneDayAgo() {
        // Arrange
        String habitName = "Drink Water";
        habitTracker.createHabit(habitName, null, HabitFrequency.DAILY, null);
        habitTracker.markHabitCompleted(habitName);
        habitTracker.advanceDay();

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> habitTracker.undoTodayCompletion(habitName));
    }

}

package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US002Test {

private HabitManager habitManager;

    @BeforeEach
    public void setUp() {
        habitManager = new HabitManager();
    }

    @Test
    public void US002_editHabit_success() {
        // Arrange
        Habit habit = new Habit("Exercise", "Fitness", Frequency.DAILY, "Go for a run");
        habitManager.createHabit(habit);

        // Act
        habitManager.editHabit(habit.getId(), "Exercise", "Fitness", Frequency.WEEKLY, "Go for a run");

        // Assert
        Habit updatedHabit = habitManager.getHabit(habit.getId());
        assertEquals("Exercise", updatedHabit.getName());
        assertEquals("Fitness", updatedHabit.getCategory());
        assertEquals(Frequency.WEEKLY, updatedHabit.getFrequency());
        assertEquals("Go for a run", updatedHabit.getDescription());
    }

    @Test
    public void US002_editHabit_duplicateName_failure() {
        // Arrange
        Habit habit1 = new Habit("Exercise", "Fitness", Frequency.DAILY, "Go for a run");
        Habit habit2 = new Habit("Exercise", "Fitness", Frequency.DAILY, "Go for a run");
        habitManager.createHabit(habit1);

        // Act & Assert
        assertThrows(InvalidHabitException.class, () -> habitManager.createHabit(habit2));
    }

    @Test
    public void US002_editHabit_emptyName_failure() {
        // Arrange
        Habit habit = new Habit("Exercise", "Fitness", Frequency.DAILY, "Go for a run");
        habitManager.createHabit(habit);

        // Act & Assert
        assertThrows(InvalidHabitException.class, () -> habitManager.editHabit(habit.getId(), "", "Fitness", Frequency.DAILY, "Go for a run"));
    }

    @Test
    public void US002_editHabit_nullHabit_failure() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> habitManager.editHabit(null, "Exercise", "Fitness", Frequency.DAILY, "Go for a run"));
    }

    @Test
    public void US002_editHabit_nonExistentHabit_failure() {
        // Act & Assert
        assertThrows(HabitNotFoundException.class, () -> habitManager.editHabit(1L, "Exercise", "Fitness", Frequency.DAILY, "Go for a run"));
    }

}

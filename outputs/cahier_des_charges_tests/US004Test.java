package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US004Test {

private HabitManager habitManager;

    @BeforeEach
    public void setUp() {
        habitManager = new HabitManager();
    }

    @Test
    public void US004_CreateHabitWithUniqueName() {
        habitManager.createHabit("Drink Water", "Daily", "Stay hydrated");
        assertTrue(habitManager.doesHabitExist("Drink Water"));
    }

    @Test
    public void US004_CreateHabitWithDuplicateNameFails() {
        habitManager.createHabit("Exercise", "Daily", "Stay fit");
        assertThrows(IllegalArgumentException.class, () -> {
            habitManager.createHabit("Exercise", "Weekly", "Stay fit");
        });
    }

    @Test
    public void US004_CreateHabitWithEmptyNameFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            habitManager.createHabit("", "Daily", "Stay active");
        });
    }

    @Test
    public void US004_EditHabitNameToUniqueName() {
        habitManager.createHabit("Read Book", "Daily", "Expand knowledge");
        habitManager.editHabit("Read Book", "Read Daily", "Expand knowledge");
        assertTrue(habitManager.doesHabitExist("Read Daily"));
        assertFalse(habitManager.doesHabitExist("Read Book"));
    }

    @Test
    public void US004_EditHabitNameToDuplicateNameFails() {
        habitManager.createHabit("Drink Water", "Daily", "Stay hydrated");
        habitManager.createHabit("Exercise", "Daily", "Stay fit");
        assertThrows(IllegalArgumentException.class, () -> {
            habitManager.editHabit("Drink Water", "Exercise", "Stay fit");
        });
    }

    @Test
    public void US004_DeleteHabit() {
        habitManager.createHabit("Yoga", "Weekly", "Stay flexible");
        habitManager.deleteHabit("Yoga");
        assertFalse(habitManager.doesHabitExist("Yoga"));
    }

    @Test
    public void US004_DeleteNonExistentHabitFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            habitManager.deleteHabit("NonExistentHabit");
        });
    }

}

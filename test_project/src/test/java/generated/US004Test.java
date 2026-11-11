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
        habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        assertTrue(habitManager.habitExists("Drink Water"));
    }

    @Test
    public void US004_CreateHabitWithDuplicateName() {
        habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        assertThrows(IllegalArgumentException.class, () -> {
            habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        });
    }

    @Test
    public void US004_CreateHabitWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> {
            habitManager.createHabit("", "Health", "Daily", "Drink 8 glasses of water daily");
        });
    }

    @Test
    public void US004_EditHabitNameToUniqueName() {
        habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        habitManager.editHabit("Drink Water", "Drink Water Every Day");
        assertTrue(habitManager.habitExists("Drink Water Every Day"));
    }

    @Test
    public void US004_EditHabitNameToDuplicateName() {
        habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        habitManager.createHabit("Exercise", "Fitness", "Daily", "Exercise for 30 minutes daily");
        assertThrows(IllegalArgumentException.class, () -> {
            habitManager.editHabit("Drink Water", "Exercise");
        });
    }

    @Test
    public void US004_EditHabitNameToEmptyName() {
        habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        assertThrows(IllegalArgumentException.class, () -> {
            habitManager.editHabit("Drink Water", "");
        });
    }

    @Test
    public void US004_DeleteHabit() {
        habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        habitManager.deleteHabit("Drink Water");
        assertFalse(habitManager.habitExists("Drink Water"));
    }

    @Test
    public void US004_DeleteNonExistentHabit() {
        assertThrows(IllegalArgumentException.class, () -> {
            habitManager.deleteHabit("NonExistentHabit");
        });
    }

}

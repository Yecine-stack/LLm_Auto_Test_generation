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
    public void US002_editHabitName() {
        // Create a habit
        habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");

        // Edit the habit name
        habitManager.editHabit("Drink Water", "Drink 8 Glasses of Water Daily");

        // Verify the habit name is updated
        Habit habit = habitManager.getHabit("Drink 8 Glasses of Water Daily");
        assertNotNull(habit);
        assertEquals("Drink 8 Glasses of Water Daily", habit.getName());
    }

    @Test
    public void US002_editHabitCategory() {
        // Create a habit
        habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");

        // Edit the habit category
        habitManager.editHabit("Drink Water", "Health and Wellness");

        // Verify the habit category is updated
        Habit habit = habitManager.getHabit("Drink Water");
        assertNotNull(habit);
        assertEquals("Health and Wellness", habit.getCategory());
    }

    @Test
    public void US002_editHabitFrequency() {
        // Create a habit
        habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");

        // Edit the habit frequency
        habitManager.editHabit("Drink Water", "Weekly");

        // Verify the habit frequency is updated
        Habit habit = habitManager.getHabit("Drink Water");
        assertNotNull(habit);
        assertEquals("Weekly", habit.getFrequency());
    }

    @Test
    public void US002_editHabitDescription() {
        // Create a habit
        habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");

        // Edit the habit description
        habitManager.editHabit("Drink Water", "Drink 8 glasses of water daily, including water from fruits and vegetables");

        // Verify the habit description is updated
        Habit habit = habitManager.getHabit("Drink Water");
        assertNotNull(habit);
        assertEquals("Drink 8 glasses of water daily, including water from fruits and vegetables", habit.getDescription());
    }

    @Test
    public void US002_editHabitWithDuplicateName() {
        // Create a habit
        habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");

        // Attempt to edit the habit with a duplicate name
        assertThrows(IllegalArgumentException.class, () -> habitManager.editHabit("Drink Water", "Drink Coffee"));
    }

    @Test
    public void US002_editHabitWithEmptyName() {
        // Create a habit
        habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");

        // Attempt to edit the habit with an empty name
        assertThrows(IllegalArgumentException.class, () -> habitManager.editHabit("Drink Water", ""));
    }

}

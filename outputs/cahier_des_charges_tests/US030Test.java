package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class US030Test {

@Test
    public void US030_ValidationForDuplicateHabitNames() {
        // Assume HabitManager class exists with createHabit method
        HabitManager habitManager = new HabitManager();

        // Create a habit
        habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");

        // Attempt to create another habit with the same name
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        });

        // Verify the exception message
        assertEquals("Habit name must be unique", exception.getMessage());
    }

    @Test
    public void US030_ValidationForEmptyHabitName() {
        HabitManager habitManager = new HabitManager();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            habitManager.createHabit("", "Health", "Daily", "Drink 8 glasses of water daily");
        });

        assertEquals("Habit name cannot be empty", exception.getMessage());
    }

    @Test
    public void US030_ValidationForMultipleDailyCompletions() {
        HabitManager habitManager = new HabitManager();
        habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");

        habitManager.markHabitCompleted("Drink Water");
        habitManager.markHabitCompleted("Drink Water");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            habitManager.markHabitCompleted("Drink Water");
        });

        assertEquals("Cannot mark habit as completed more than once on the same day", exception.getMessage());
    }

    @Test
    public void US030_ValidationForUndoingCompletion() {
        HabitManager habitManager = new HabitManager();
        habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");

        habitManager.markHabitCompleted("Drink Water");
        habitManager.undoCompletion("Drink Water");

        // Verify that the habit is not marked as completed
        assertFalse(habitManager.isHabitCompleted("Drink Water"));
    }

    @Test
    public void US030_ValidationForEditingHabitDescription() {
        HabitManager habitManager = new HabitManager();
        habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");

        habitManager.editHabitDescription("Drink Water", "Drink 10 glasses of water daily");

        // Verify that the description is updated
        assertEquals("Drink 10 glasses of water daily", habitManager.getHabitDescription("Drink Water"));
    }

    @Test
    public void US030_ValidationForEditingHabitCategory() {
        HabitManager habitManager = new HabitManager();
        habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");

        habitManager.editHabitCategory("Drink Water", "Wellness");

        // Verify that the category is updated
        assertEquals("Wellness", habitManager.getHabitCategory("Drink Water"));
    }

    @Test
    public void US030_ValidationForEditingHabitTargetFrequency() {
        HabitManager habitManager = new HabitManager();
        habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");

        habitManager.editHabitTargetFrequency("Drink Water", "Weekly");

        // Verify that the target frequency is updated
        assertEquals("Weekly", habitManager.getHabitTargetFrequency("Drink Water"));
    }

    @Test
    public void US030_ValidationForEditingHabitName() {
        HabitManager habitManager = new HabitManager();
        habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");

        habitManager.editHabitName("Drink Water", "Stay Hydrated");

        // Verify that the name is updated
        assertEquals("Stay Hydrated", habitManager.getHabitName("Stay Hydrated"));
    }

    @Test
    public void US030_ValidationForDeletingHabit() {
        HabitManager habitManager = new HabitManager();
        habitManager.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");

        habitManager.deleteHabit("Drink Water");

        // Verify that the habit is deleted
        assertFalse(habitManager.doesHabitExist("Drink Water"));
    }

}

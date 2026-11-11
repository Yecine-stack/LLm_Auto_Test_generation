package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US027Test {

private HabitTracker habitTracker;

    @BeforeEach
    public void setUp() {
        habitTracker = new HabitTracker();
    }

    @Test
    public void US027_RecoverPreviouslySavedData() {
        // Simulate saving data
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        habitTracker.markHabitCompleted("Drink Water");
        habitTracker.saveData();

        // Simulate application restart
        habitTracker = new HabitTracker();

        // Verify data is recovered
        assertTrue(habitTracker.getHabit("Drink Water") != null);
        assertTrue(habitTracker.isHabitCompleted("Drink Water"));
    }

    @Test
    public void US027_RecoverPreviouslySavedDataWithMultipleHabits() {
        // Simulate saving data with multiple habits
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        habitTracker.createHabit("Read Book", "Personal Development", "Daily", "Read for at least 30 minutes");
        habitTracker.markHabitCompleted("Drink Water");
        habitTracker.markHabitCompleted("Read Book");
        habitTracker.saveData();

        // Simulate application restart
        habitTracker = new HabitTracker();

        // Verify data is recovered
        assertTrue(habitTracker.getHabit("Drink Water") != null);
        assertTrue(habitTracker.isHabitCompleted("Drink Water"));
        assertTrue(habitTracker.getHabit("Read Book") != null);
        assertTrue(habitTracker.isHabitCompleted("Read Book"));
    }

    @Test
    public void US027_RecoverPreviouslySavedDataWithStreaks() {
        // Simulate saving data with streaks
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        habitTracker.markHabitCompleted("Drink Water");
        habitTracker.markHabitCompleted("Drink Water");
        habitTracker.saveData();

        // Simulate application restart
        habitTracker = new HabitTracker();

        // Verify data is recovered
        assertTrue(habitTracker.getHabit("Drink Water") != null);
        assertEquals(2, habitTracker.getCurrentStreak("Drink Water"));
        assertEquals(2, habitTracker.getLongestStreak("Drink Water"));
    }

    @Test
    public void US027_RecoverPreviouslySavedDataWithCalendarHistory() {
        // Simulate saving data with calendar history
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        habitTracker.markHabitCompleted("Drink Water");
        habitTracker.saveData();

        // Simulate application restart
        habitTracker = new HabitTracker();

        // Verify data is recovered
        assertTrue(habitTracker.getHabit("Drink Water") != null);
        assertTrue(habitTracker.isHabitCompleted("Drink Water", LocalDate.now()));
    }

}

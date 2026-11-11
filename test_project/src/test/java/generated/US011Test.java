package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US011Test {

private HabitTracker habitTracker;

    @BeforeEach
    public void setUp() {
        habitTracker = new HabitTracker();
    }

    @Test
    public void US011_calculateLongestStreak_noHabits() {
        assertEquals(0, habitTracker.calculateLongestStreak());
    }

    @Test
    public void US011_calculateLongestStreak_singleHabitNoCompletion() {
        habitTracker.createHabit("Exercise", "daily");
        assertEquals(0, habitTracker.calculateLongestStreak());
    }

    @Test
    public void US011_calculateLongestStreak_singleHabitOneCompletion() {
        habitTracker.createHabit("Exercise", "daily");
        habitTracker.markHabitCompleted("Exercise");
        assertEquals(1, habitTracker.calculateLongestStreak());
    }

    @Test
    public void US011_calculateLongestStreak_multipleHabits() {
        habitTracker.createHabit("Exercise", "daily");
        habitTracker.createHabit("Read", "daily");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Read");
        assertEquals(1, habitTracker.calculateLongestStreak());
    }

    @Test
    public void US011_calculateLongestStreak_consecutiveCompletions() {
        habitTracker.createHabit("Exercise", "daily");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise");
        assertEquals(2, habitTracker.calculateLongestStreak());
    }

    @Test
    public void US011_calculateLongestStreak_nonConsecutiveCompletions() {
        habitTracker.createHabit("Exercise", "daily");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise", 2);
        assertEquals(1, habitTracker.calculateLongestStreak());
    }

    @Test
    public void US011_calculateLongestStreak_resetOnBreak() {
        habitTracker.createHabit("Exercise", "daily");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise", 3);
        assertEquals(1, habitTracker.calculateLongestStreak());
    }

    @Test
    public void US011_calculateLongestStreak_multipleLongestStreaks() {
        habitTracker.createHabit("Exercise", "daily");
        habitTracker.createHabit("Read", "daily");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise", 2);
        habitTracker.markHabitCompleted("Read");
        habitTracker.markHabitCompleted("Read", 2);
        assertEquals(2, habitTracker.calculateLongestStreak());
    }

}

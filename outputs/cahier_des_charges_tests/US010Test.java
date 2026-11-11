package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US010Test {

private HabitTracker habitTracker;

    @BeforeEach
    public void setUp() {
        habitTracker = new HabitTracker();
    }

    @Test
    public void US010_calculateLongestStreak_noHabits() {
        assertEquals(0, habitTracker.calculateLongestStreak());
    }

    @Test
    public void US010_calculateLongestStreak_singleHabitNoCompletion() {
        habitTracker.createHabit("Exercise", "Daily");
        assertEquals(0, habitTracker.calculateLongestStreak());
    }

    @Test
    public void US010_calculateLongestStreak_singleHabitOneCompletion() {
        habitTracker.createHabit("Exercise", "Daily");
        habitTracker.markHabitCompleted("Exercise");
        assertEquals(1, habitTracker.calculateLongestStreak());
    }

    @Test
    public void US010_calculateLongestStreak_singleHabitMultipleCompletions() {
        habitTracker.createHabit("Exercise", "Daily");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise");
        assertEquals(2, habitTracker.calculateLongestStreak());
    }

    @Test
    public void US010_calculateLongestStreak_multipleHabits() {
        habitTracker.createHabit("Exercise", "Daily");
        habitTracker.createHabit("Read", "Daily");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Read");
        assertEquals(1, habitTracker.calculateLongestStreak());
    }

    @Test
    public void US010_calculateLongestStreak_withBreaks() {
        habitTracker.createHabit("Exercise", "Daily");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise");
        assertEquals(7, habitTracker.calculateLongestStreak());
    }

    @Test
    public void US010_calculateLongestStreak_withLongBreak() {
        habitTracker.createHabit("Exercise", "Daily");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise");
        assertEquals(14, habitTracker.calculateLongestStreak());
    }

}

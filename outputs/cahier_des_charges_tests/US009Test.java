package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US009Test {

private HabitTracker habitTracker;

    @BeforeEach
    public void setUp() {
        habitTracker = new HabitTracker();
    }

    @Test
    public void US009_calculateCurrentStreak_whenNoHabitsCompleted_returnsZero() {
        assertEquals(0, habitTracker.calculateCurrentStreak());
    }

    @Test
    public void US009_calculateCurrentStreak_whenSingleHabitCompletedOnce_returnsOne() {
        habitTracker.markHabitAsCompleted("Habit1");
        assertEquals(1, habitTracker.calculateCurrentStreak());
    }

    @Test
    public void US009_calculateCurrentStreak_whenSingleHabitCompletedMultipleTimes_returnsNumberOfCompletions() {
        habitTracker.markHabitAsCompleted("Habit1");
        habitTracker.markHabitAsCompleted("Habit1");
        assertEquals(2, habitTracker.calculateCurrentStreak());
    }

    @Test
    public void US009_calculateCurrentStreak_whenMultipleHabitsCompleted_returnsLongestStreak() {
        habitTracker.markHabitAsCompleted("Habit1");
        habitTracker.markHabitAsCompleted("Habit2");
        habitTracker.markHabitAsCompleted("Habit1");
        assertEquals(2, habitTracker.calculateCurrentStreak());
    }

    @Test
    public void US009_calculateCurrentStreak_whenHabitCompletedAndThenSkipped_returnsZero() {
        habitTracker.markHabitAsCompleted("Habit1");
        habitTracker.markHabitAsSkipped("Habit1");
        assertEquals(0, habitTracker.calculateCurrentStreak());
    }

    @Test
    public void US009_calculateCurrentStreak_whenHabitCompletedOnDifferentDays_returnsLongestStreak() {
        habitTracker.markHabitAsCompleted("Habit1");
        habitTracker.advanceDay();
        habitTracker.markHabitAsCompleted("Habit1");
        assertEquals(2, habitTracker.calculateCurrentStreak());
    }

    @Test
    public void US009_calculateCurrentStreak_whenHabitCompletedAndThenReversed_returnsZero() {
        habitTracker.markHabitAsCompleted("Habit1");
        habitTracker.markHabitAsCompleted("Habit1");
        habitTracker.markHabitAsSkipped("Habit1");
        assertEquals(0, habitTracker.calculateCurrentStreak());
    }

}

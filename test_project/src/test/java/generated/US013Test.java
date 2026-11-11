package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;

public class US013Test {

private HabitTracker habitTracker;

    @BeforeEach
    public void setUp() {
        habitTracker = new HabitTracker();
    }

    @Test
    public void US013_calculateCompletionRate_noHabits() {
        assertEquals(0.0, habitTracker.calculateCompletionRate());
    }

    @Test
    public void US013_calculateCompletionRate_oneHabitCompleted() {
        habitTracker.createHabit("Exercise", "daily");
        habitTracker.markHabitCompleted("Exercise");
        assertEquals(1.0, habitTracker.calculateCompletionRate());
    }

    @Test
    public void US013_calculateCompletionRate_oneHabitMissed() {
        habitTracker.createHabit("Exercise", "daily");
        assertEquals(0.0, habitTracker.calculateCompletionRate());
    }

    @Test
    public void US013_calculateCompletionRate_multipleHabits() {
        habitTracker.createHabit("Exercise", "daily");
        habitTracker.createHabit("Read", "daily");
        habitTracker.markHabitCompleted("Exercise");
        assertEquals(0.5, habitTracker.calculateCompletionRate());
    }

    @Test
    public void US013_calculateCompletionRate_multipleHabitsCompleted() {
        habitTracker.createHabit("Exercise", "daily");
        habitTracker.createHabit("Read", "daily");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Read");
        assertEquals(1.0, habitTracker.calculateCompletionRate());
    }

    @Test
    public void US013_calculateCompletionRate_multipleHabitsMissed() {
        habitTracker.createHabit("Exercise", "daily");
        habitTracker.createHabit("Read", "daily");
        assertEquals(0.0, habitTracker.calculateCompletionRate());
    }

    @Test
    public void US013_calculateCompletionRate_withStreaks() {
        habitTracker.createHabit("Exercise", "daily");
        habitTracker.markHabitCompleted("Exercise");
        habitTracker.markHabitCompleted("Exercise");
        assertEquals(1.0, habitTracker.calculateCompletionRate());
    }

}

package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US014Test {

private HabitTracker habitTracker;

    @BeforeEach
    public void setUp() {
        habitTracker = new HabitTracker();
    }

    @Test
    public void US014_DisplayTotalActiveHabits_InitialCountIsZero() {
        assertEquals(0, habitTracker.getTotalActiveHabits());
    }

    @Test
    public void US014_DisplayTotalActiveHabits_IncreasesAfterAddingHabit() {
        habitTracker.addHabit("Exercise", "Fitness", HabitFrequency.DAILY);
        assertEquals(1, habitTracker.getTotalActiveHabits());
    }

    @Test
    public void US014_DisplayTotalActiveHabits_DecreasesAfterDeletingHabit() {
        habitTracker.addHabit("Exercise", "Fitness", HabitFrequency.DAILY);
        habitTracker.deleteHabit("Exercise");
        assertEquals(0, habitTracker.getTotalActiveHabits());
    }

    @Test
    public void US014_DisplayTotalActiveHabits_RemainsUnchangedAfterEditingHabit() {
        habitTracker.addHabit("Exercise", "Fitness", HabitFrequency.DAILY);
        habitTracker.editHabit("Exercise", "New Exercise", "Fitness", HabitFrequency.DAILY);
        assertEquals(1, habitTracker.getTotalActiveHabits());
    }

}

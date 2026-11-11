package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US026Test {

private HabitTracker habitTracker;

    @BeforeEach
    public void setUp() {
        habitTracker = new HabitTracker();
    }

    @Test
    public void US026_CreateHabit_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        assertTrue(habitTracker.getHabit("Drink Water") != null);
    }

    @Test
    public void US026_CreateHabit_DuplicateName_Failure() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        assertThrows(IllegalArgumentException.class, () -> {
            habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        });
    }

    @Test
    public void US026_CreateHabit_EmptyName_Failure() {
        assertThrows(IllegalArgumentException.class, () -> {
            habitTracker.createHabit("", "Health", "Daily", "Stay hydrated");
        });
    }

    @Test
    public void US026_EditHabit_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        habitTracker.editHabit("Drink Water", "Drink Water", "Health", "Weekly", "Stay hydrated more");
        assertTrue(habitTracker.getHabit("Drink Water").getCategory().equals("Health"));
        assertTrue(habitTracker.getHabit("Drink Water").getFrequency().equals("Weekly"));
    }

    @Test
    public void US026_EditHabit_ChangeName_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        habitTracker.editHabit("Drink Water", "Drink More Water", "Health", "Daily", "Stay hydrated more");
        assertTrue(habitTracker.getHabit("Drink More Water") != null);
    }

    @Test
    public void US026_DeleteHabit_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        habitTracker.deleteHabit("Drink Water");
        assertTrue(habitTracker.getHabit("Drink Water") == null);
    }

    @Test
    public void US026_CompleteHabit_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        habitTracker.completeHabit("Drink Water");
        assertTrue(habitTracker.getHabit("Drink Water").isCompletedToday());
    }

    @Test
    public void US026_CompleteHabit_MultipleTimes_Failure() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        habitTracker.completeHabit("Drink Water");
        habitTracker.completeHabit("Drink Water");
        assertFalse(habitTracker.getHabit("Drink Water").isCompletedToday());
    }

    @Test
    public void US026_UndoCompletion_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        habitTracker.completeHabit("Drink Water");
        habitTracker.undoCompletion("Drink Water");
        assertFalse(habitTracker.getHabit("Drink Water").isCompletedToday());
    }

    @Test
    public void US026_ViewStreaksAndStatistics_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        habitTracker.completeHabit("Drink Water");
        habitTracker.completeHabit("Drink Water");
        habitTracker.completeHabit("Drink Water");
        assertEquals(3, habitTracker.getTotalCompletions());
        assertEquals(3, habitTracker.getCurrentStreak());
        assertEquals(3, habitTracker.getLongestStreak());
        assertEquals(100.0, habitTracker.getCompletionRate());
    }

    @Test
    public void US026_ViewCalendarHistory_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        habitTracker.completeHabit("Drink Water");
        assertTrue(habitTracker.getCalendarHistory("Drink Water").containsKey(LocalDate.now()));
        assertTrue(habitTracker.getCalendarHistory("Drink Water").get(LocalDate.now()));
    }

    @Test
    public void US026_ViewCalendarHistory_OldRecords_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        habitTracker.completeHabit("Drink Water");
        LocalDate oldDate = LocalDate.now().minusDays(8);
        assertFalse(habitTracker.getCalendarHistory("Drink Water").containsKey(oldDate));
    }

    @Test
    public void US026_ConfigureReminder_Success() {
        habitTracker.setReminderTime(LocalTime.NOON);
        assertEquals(LocalTime.NOON, habitTracker.getReminderTime());
    }

}

package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US029Test {

private HabitTracker habitTracker;

    @BeforeEach
    public void setUp() {
        habitTracker = new HabitTracker();
    }

    @Test
    public void US029_CreateHabit_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        assertTrue(habitTracker.getHabit("Drink Water") != null);
    }

    @Test
    public void US029_CreateHabit_DuplicateName_Failure() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        assertThrows(IllegalArgumentException.class, () -> {
            habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        });
    }

    @Test
    public void US029_CreateHabit_EmptyName_Failure() {
        assertThrows(IllegalArgumentException.class, () -> {
            habitTracker.createHabit("", "Health", "Daily", "Stay hydrated");
        });
    }

    @Test
    public void US029_EditHabit_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        habitTracker.editHabit("Drink Water", "Drink Water", "Health", "Weekly", "Stay hydrated more");
        assertTrue(habitTracker.getHabit("Drink Water") != null);
    }

    @Test
    public void US029_DeleteHabit_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        habitTracker.deleteHabit("Drink Water");
        assertNull(habitTracker.getHabit("Drink Water"));
    }

    @Test
    public void US029_MarkHabitCompleted_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        habitTracker.markHabitCompleted("Drink Water");
        assertTrue(habitTracker.getHabit("Drink Water").isCompletedToday());
    }

    @Test
    public void US029_MarkHabitCompleted_MultipleTimes_Failure() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        habitTracker.markHabitCompleted("Drink Water");
        habitTracker.markHabitCompleted("Drink Water");
        assertFalse(habitTracker.getHabit("Drink Water").isCompletedToday());
    }

    @Test
    public void US029_MarkHabitCompleted_InvalidHabit_Failure() {
        assertThrows(IllegalArgumentException.class, () -> {
            habitTracker.markHabitCompleted("NonExistentHabit");
        });
    }

    @Test
    public void US029_UndoHabitCompletion_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        habitTracker.markHabitCompleted("Drink Water");
        habitTracker.undoHabitCompletion("Drink Water");
        assertFalse(habitTracker.getHabit("Drink Water").isCompletedToday());
    }

    @Test
    public void US029_UndoHabitCompletion_InvalidHabit_Failure() {
        assertThrows(IllegalArgumentException.class, () -> {
            habitTracker.undoHabitCompletion("NonExistentHabit");
        });
    }

    @Test
    public void US029_ViewStreaksAndStatistics_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        habitTracker.markHabitCompleted("Drink Water");
        habitTracker.markHabitCompleted("Drink Water");
        assertEquals(2, habitTracker.getCurrentStreak());
        assertEquals(2, habitTracker.getLongestStreak());
        assertEquals(2, habitTracker.getTotalCompletions());
        assertEquals(100.0, habitTracker.getCompletionRate());
    }

    @Test
    public void US029_ViewCalendarHistory_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        habitTracker.markHabitCompleted("Drink Water");
        assertTrue(habitTracker.getCalendarHistory("Drink Water").containsKey(LocalDate.now()));
    }

    @Test
    public void US029_ViewCalendarHistory_InvalidHabit_Failure() {
        assertThrows(IllegalArgumentException.class, () -> {
            habitTracker.getCalendarHistory("NonExistentHabit");
        });
    }

    @Test
    public void US029_ViewCalendarHistory_OldRecords_Failure() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Stay hydrated");
        habitTracker.markHabitCompleted("Drink Water");
        LocalDate oldDate = LocalDate.now().minusDays(8);
        assertThrows(IllegalArgumentException.class, () -> {
            habitTracker.getCalendarHistory("Drink Water").get(oldDate);
        });
    }

    @Test
    public void US029_ConfigureReminder_Success() {
        habitTracker.configureReminder("12:00");
        assertEquals("12:00", habitTracker.getReminderTime());
    }

    @Test
    public void US029_ConfigureReminder_InvalidTime_Failure() {
        assertThrows(IllegalArgumentException.class, () -> {
            habitTracker.configureReminder("25:00");
        });
    }

    @Test
    public void US029_ConfigureReminder_EmptyTime_Failure() {
        assertThrows(IllegalArgumentException.class, () -> {
            habitTracker.configureReminder("");
        });
    }

}

package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US018Test {

private CalendarHistory calendarHistory;

    @BeforeEach
    public void setUp() {
        calendarHistory = new CalendarHistory();
    }

    @Test
    public void US018_ViewCalendarForNewHabit() {
        // Create a new habit
        calendarHistory.createHabit("Drink Water", "Daily");

        // Mark the habit as completed
        calendarHistory.markHabitCompleted("Drink Water");

        // View the calendar
        CalendarView calendarView = calendarHistory.getCalendarView();

        // Verify the habit is marked as completed
        assertTrue(calendarView.isHabitCompleted("Drink Water", LocalDate.now()));
    }

    @Test
    public void US018_ViewCalendarForExistingHabit() {
        // Create a new habit
        calendarHistory.createHabit("Exercise", "Daily");

        // Mark the habit as completed
        calendarHistory.markHabitCompleted("Exercise");

        // View the calendar
        CalendarView calendarView = calendarHistory.getCalendarView();

        // Verify the habit is marked as completed
        assertTrue(calendarView.isHabitCompleted("Exercise", LocalDate.now()));
    }

    @Test
    public void US018_ViewCalendarForMissedHabit() {
        // Create a new habit
        calendarHistory.createHabit("Read Book", "Daily");

        // View the calendar
        CalendarView calendarView = calendarHistory.getCalendarView();

        // Verify the habit is marked as missed
        assertFalse(calendarView.isHabitCompleted("Read Book", LocalDate.now()));
    }

    @Test
    public void US018_ViewCalendarForHabitWithDifferentCategory() {
        // Create a new habit with a category
        calendarHistory.createHabit("Meditate", "Weekly");

        // Mark the habit as completed
        calendarHistory.markHabitCompleted("Meditate");

        // View the calendar
        CalendarView calendarView = calendarHistory.getCalendarView();

        // Verify the habit is marked as completed
        assertTrue(calendarView.isHabitCompleted("Meditate", LocalDate.now()));
    }

    @Test
    public void US018_ViewCalendarForHabitWithDescription() {
        // Create a new habit with a description
        calendarHistory.createHabit("Write Journal", "Daily", "Write 100 words");

        // Mark the habit as completed
        calendarHistory.markHabitCompleted("Write Journal");

        // View the calendar
        CalendarView calendarView = calendarHistory.getCalendarView();

        // Verify the habit is marked as completed
        assertTrue(calendarView.isHabitCompleted("Write Journal", LocalDate.now()));
    }

    @Test
    public void US018_ViewCalendarForHabitWithEmptyName() {
        // Attempt to create a habit with an empty name
        assertThrows(IllegalArgumentException.class, () -> {
            calendarHistory.createHabit("", "Daily");
        });
    }

    @Test
    public void US018_ViewCalendarForHabitWithDuplicateName() {
        // Create a new habit
        calendarHistory.createHabit("Drink Water", "Daily");

        // Attempt to create a habit with the same name
        assertThrows(IllegalArgumentException.class, () -> {
            calendarHistory.createHabit("Drink Water", "Daily");
        });
    }

    @Test
    public void US018_ViewCalendarForHabitWithInvalidCategory() {
        // Attempt to create a habit with an invalid category
        assertThrows(IllegalArgumentException.class, () -> {
            calendarHistory.createHabit("Exercise", "Invalid");
        });
    }

    @Test
    public void US018_ViewCalendarForHabitWithInvalidFrequency() {
        // Attempt to create a habit with an invalid frequency
        assertThrows(IllegalArgumentException.class, () -> {
            calendarHistory.createHabit("Read Book", "Invalid");
        });
    }

    @Test
    public void US018_ViewCalendarForHabitWithPastCompletion() {
        // Create a new habit
        calendarHistory.createHabit("Drink Water", "Daily");

        // Mark the habit as completed
        calendarHistory.markHabitCompleted("Drink Water");

        // Attempt to mark the habit as completed again
        assertThrows(IllegalArgumentException.class, () -> {
            calendarHistory.markHabitCompleted("Drink Water");
        });
    }

    @Test
    public void US018_ViewCalendarForHabitWithUndoCompletion() {
        // Create a new habit
        calendarHistory.createHabit("Exercise", "Daily");

        // Mark the habit as completed
        calendarHistory.markHabitCompleted("Exercise");

        // Undo the completion
        calendarHistory.undoHabitCompletion("Exercise");

        // View the calendar
        CalendarView calendarView = calendarHistory.getCalendarView();

        // Verify the habit is marked as missed
        assertFalse(calendarView.isHabitCompleted("Exercise", LocalDate.now()));
    }

    @Test
    public void US018_ViewCalendarForHabitWithUndoCompletionTwice() {
        // Create a new habit
        calendarHistory.createHabit("Read Book", "Daily");

        // Mark the habit as completed
        calendarHistory.markHabitCompleted("Read Book");

        // Undo the completion
        calendarHistory.undoHabitCompletion("Read Book");

        // Undo the completion again
        calendarHistory.undoHabitCompletion("Read Book");

        // View the calendar
        CalendarView calendarView = calendarHistory.getCalendarView();

        // Verify the habit is marked as completed
        assertTrue(calendarView.isHabitCompleted("Read Book", LocalDate.now()));
    }

    @Test
    public void US018_ViewCalendarForHabitWithUndoCompletionOnDifferentDay() {
        // Create a new habit
        calendarHistory.createHabit("Meditate", "Daily");

        // Mark the habit as completed
        calendarHistory.markHabitCompleted("Meditate");

        // Undo the completion on a different day
        calendarHistory.undoHabitCompletion("Meditate", LocalDate.now().plusDays(1));

        // View the calendar
        CalendarView calendarView = calendarHistory.getCalendarView();

        // Verify the habit is marked as missed
        assertFalse(calendarView.isHabitCompleted("Meditate", LocalDate.now()));
    }

    @Test
    public void US018_ViewCalendarForHabitWithUndoCompletionOnInvalidDay() {
        // Create a new habit
        calendarHistory.createHabit("Write Journal", "Daily");

        // Attempt to undo the completion on an invalid day
        assertThrows(IllegalArgumentException.class, () -> {
            calendarHistory.undoHabitCompletion("Write Journal", LocalDate.now().minusDays(8));
        });
    }

}

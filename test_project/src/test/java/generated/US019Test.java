package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US019Test {

private CalendarHistory calendarHistory;

    @BeforeEach
    public void setUp() {
        calendarHistory = new CalendarHistory();
    }

    @Test
    public void US019_ViewCalendarForNewHabit() {
        // Create a new habit
        calendarHistory.createHabit("Drink Water", "Daily");

        // Mark the habit as completed
        calendarHistory.markHabitCompleted("Drink Water");

        // View the calendar
        CalendarView calendarView = calendarHistory.getCalendarView();

        // Check if the habit is marked as completed
        assertTrue(calendarView.isHabitCompleted("Drink Water", LocalDate.now()));
    }

    @Test
    public void US019_ViewCalendarForExistingHabit() {
        // Create a new habit
        calendarHistory.createHabit("Exercise", "Daily");

        // Mark the habit as completed
        calendarHistory.markHabitCompleted("Exercise");

        // View the calendar
        CalendarView calendarView = calendarHistory.getCalendarView();

        // Check if the habit is marked as completed
        assertTrue(calendarView.isHabitCompleted("Exercise", LocalDate.now()));
    }

    @Test
    public void US019_ViewCalendarForMissedHabit() {
        // Create a new habit
        calendarHistory.createHabit("Read Book", "Daily");

        // View the calendar
        CalendarView calendarView = calendarHistory.getCalendarView();

        // Check if the habit is marked as missed
        assertFalse(calendarView.isHabitCompleted("Read Book", LocalDate.now()));
    }

    @Test
    public void US019_ViewCalendarForMultipleHabits() {
        // Create multiple habits
        calendarHistory.createHabit("Drink Water", "Daily");
        calendarHistory.createHabit("Exercise", "Daily");
        calendarHistory.createHabit("Read Book", "Daily");

        // Mark some habits as completed
        calendarHistory.markHabitCompleted("Drink Water");
        calendarHistory.markHabitCompleted("Read Book");

        // View the calendar
        CalendarView calendarView = calendarHistory.getCalendarView();

        // Check if the habits are marked as completed and missed
        assertTrue(calendarView.isHabitCompleted("Drink Water", LocalDate.now()));
        assertFalse(calendarView.isHabitCompleted("Exercise", LocalDate.now()));
        assertTrue(calendarView.isHabitCompleted("Read Book", LocalDate.now()));
    }

    @Test
    public void US019_ViewCalendarForPastHabit() {
        // Create a new habit
        calendarHistory.createHabit("Meditate", "Daily");

        // Mark the habit as completed
        calendarHistory.markHabitCompleted("Meditate");

        // Move to the past
        LocalDate pastDate = LocalDate.now().minusDays(8);

        // View the calendar
        CalendarView calendarView = calendarHistory.getCalendarView();

        // Check if the habit is marked as completed
        assertTrue(calendarView.isHabitCompleted("Meditate", pastDate));
    }

    @Test
    public void US019_ViewCalendarForInvalidHabitName() {
        // Attempt to view the calendar for an invalid habit name
        CalendarView calendarView = calendarHistory.getCalendarView();

        // Check if the habit is not marked as completed
        assertFalse(calendarView.isHabitCompleted("Invalid Habit", LocalDate.now()));
    }

}

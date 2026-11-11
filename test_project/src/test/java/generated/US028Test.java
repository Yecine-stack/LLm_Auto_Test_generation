package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US028Test {

private HabitTracker habitTracker;

    @BeforeEach
    public void setUp() {
        habitTracker = new HabitTracker();
    }

    @Test
    public void US028_CreateHabit_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        assertTrue(habitTracker.getHabit("Drink Water") != null);
    }

    @Test
    public void US028_CreateHabit_DuplicateName_Failure() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        assertThrows(IllegalArgumentException.class, () -> {
            habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        });
    }

    @Test
    public void US028_CreateHabit_EmptyName_Failure() {
        assertThrows(IllegalArgumentException.class, () -> {
            habitTracker.createHabit("", "Health", "Daily", "Drink 8 glasses of water daily");
        });
    }

    @Test
    public void US028_EditHabit_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        habitTracker.editHabit("Drink Water", "Drink 8 glasses of water daily", "Health", "Daily");
        assertEquals("Drink 8 glasses of water daily", habitTracker.getHabit("Drink Water").getDescription());
    }

    @Test
    public void US028_EditHabit_ChangeName_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        habitTracker.editHabit("Drink Water", "Drink 8 glasses of water daily", "Health", "Weekly");
        assertEquals("Weekly", habitTracker.getHabit("Drink Water").getFrequency());
    }

    @Test
    public void US028_EditHabit_ChangeCategory_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        habitTracker.editHabit("Drink Water", "Drink 8 glasses of water daily", "Fitness", "Daily");
        assertEquals("Fitness", habitTracker.getHabit("Drink Water").getCategory());
    }

    @Test
    public void US028_EditHabit_ChangeDescription_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        habitTracker.editHabit("Drink Water", "Drink 8 glasses of water daily", "Health", "Daily");
        assertEquals("Drink 8 glasses of water daily", habitTracker.getHabit("Drink Water").getDescription());
    }

    @Test
    public void US028_DeleteHabit_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        habitTracker.deleteHabit("Drink Water");
        assertNull(habitTracker.getHabit("Drink Water"));
    }

    @Test
    public void US028_CompleteHabit_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        habitTracker.completeHabit("Drink Water");
        assertTrue(habitTracker.getHabit("Drink Water").isCompletedToday());
    }

    @Test
    public void US028_CompleteHabit_MultipleTimes_Failure() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        habitTracker.completeHabit("Drink Water");
        habitTracker.completeHabit("Drink Water");
        assertFalse(habitTracker.getHabit("Drink Water").isCompletedToday());
    }

    @Test
    public void US028_UndoCompletion_Success() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        habitTracker.completeHabit("Drink Water");
        habitTracker.undoCompletion("Drink Water");
        assertFalse(habitTracker.getHabit("Drink Water").isCompletedToday());
    }

    @Test
    public void US028_CompleteHabit_OldRecord_Failure() {
        habitTracker.createHabit("Drink Water", "Health", "Daily", "Drink 8 glasses of water daily");
        habitTracker.completeHabit("Drink Water");
        habitTracker.undoCompletion("Drink Water");
        habitTracker.completeHabit("Drink Water");
        assertFalse(habitTracker.getHabit("Drink Water").isCompletedToday());
    }

}

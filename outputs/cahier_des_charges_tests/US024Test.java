package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class US024Test {

@Test
    public void US024_ChangeReminderTime_UpdatesImmediately() {
        // Arrange
        NotificationSettings settings = new NotificationSettings();
        LocalTime newReminderTime = LocalTime.of(10, 0);

        // Act
        settings.setReminderTime(newReminderTime);

        // Assert
        assertEquals(newReminderTime, settings.getReminderTime());
    }

    @Test
    public void US024_ChangeReminderTime_ToNull_ThrowsException() {
        // Arrange
        NotificationSettings settings = new NotificationSettings();
        LocalTime currentReminderTime = settings.getReminderTime();

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            settings.setReminderTime(null);
        });

        // Verify that the reminder time was not changed
        assertEquals(currentReminderTime, settings.getReminderTime());
    }

    @Test
    public void US024_ChangeReminderTime_ToSameValue_DoesNotChange() {
        // Arrange
        NotificationSettings settings = new NotificationSettings();
        LocalTime currentReminderTime = settings.getReminderTime();

        // Act
        settings.setReminderTime(currentReminderTime);

        // Assert
        assertEquals(currentReminderTime, settings.getReminderTime());
    }

}

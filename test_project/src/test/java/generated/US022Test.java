package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US022Test {

private NotificationConfig notificationConfig;

    @BeforeEach
    public void setUp() {
        notificationConfig = new NotificationConfig();
    }

    @Test
    public void US022_SetReminderTime_WithValidTime_ShouldSetReminderTime() {
        // Arrange
        LocalTime validTime = LocalTime.of(10, 0);

        // Act
        notificationConfig.setReminderTime(validTime);

        // Assert
        assertEquals(validTime, notificationConfig.getReminderTime());
    }

    @Test
    public void US022_SetReminderTime_WithNull_ShouldThrowNullPointerException() {
        // Arrange
        LocalTime nullTime = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            notificationConfig.setReminderTime(nullTime);
        });
    }

    @Test
    public void US022_SetReminderTime_WithInvalidTime_ShouldThrowIllegalArgumentException() {
        // Arrange
        LocalTime invalidTime = LocalTime.of(25, 0);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            notificationConfig.setReminderTime(invalidTime);
        });
    }

    @Test
    public void US022_GetReminderTime_WithNoSetTime_ShouldReturnDefaultTime() {
        // Arrange
        LocalTime defaultTime = LocalTime.NOON;

        // Act
        LocalTime actualTime = notificationConfig.getReminderTime();

        // Assert
        assertEquals(defaultTime, actualTime);
    }

}

package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US025Test {

private NotificationService notificationService;

    @BeforeEach
    public void setUp() {
        notificationService = new NotificationService();
    }

    @Test
    public void US025_ChangeReminderTime_ImmediatelyApplies() {
        // Arrange
        LocalTime newReminderTime = LocalTime.of(10, 0);

        // Act
        notificationService.setReminderTime(newReminderTime);

        // Assert
        assertEquals(newReminderTime, notificationService.getReminderTime());
    }

    @Test
    public void US025_ChangeReminderTime_NullTimeThrowsException() {
        // Arrange
        LocalTime newReminderTime = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            notificationService.setReminderTime(newReminderTime);
        });
    }

    @Test
    public void US025_ChangeReminderTime_EmptyTimeThrowsException() {
        // Arrange
        LocalTime newReminderTime = LocalTime.of(0, 0);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            notificationService.setReminderTime(newReminderTime);
        });
    }

}

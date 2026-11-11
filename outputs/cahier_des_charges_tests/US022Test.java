package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US022Test {

private NotificationService notificationService;

    @BeforeEach
    public void setUp() {
        notificationService = new NotificationService();
    }

    @Test
    public void US022_DisplayNotificationForDailyReminder_WhenReminderIsEnabled() {
        // Arrange
        boolean isReminderEnabled = true;
        String reminderTime = "10:00 AM";

        // Act
        notificationService.setReminder(isReminderEnabled, reminderTime);
        boolean isNotificationDisplayed = notificationService.displayNotification();

        // Assert
        assertTrue(isNotificationDisplayed);
    }

    @Test
    public void US022_NotDisplayNotificationForDailyReminder_WhenReminderIsDisabled() {
        // Arrange
        boolean isReminderEnabled = false;
        String reminderTime = "10:00 AM";

        // Act
        notificationService.setReminder(isReminderEnabled, reminderTime);
        boolean isNotificationDisplayed = notificationService.displayNotification();

        // Assert
        assertFalse(isNotificationDisplayed);
    }

    @Test
    public void US022_DisplayNotificationForDailyReminder_WhenReminderTimeChanges() {
        // Arrange
        boolean isReminderEnabled = true;
        String initialReminderTime = "10:00 AM";
        String updatedReminderTime = "11:00 AM";

        // Act
        notificationService.setReminder(isReminderEnabled, initialReminderTime);
        notificationService.setReminder(isReminderEnabled, updatedReminderTime);
        boolean isNotificationDisplayed = notificationService.displayNotification();

        // Assert
        assertTrue(isNotificationDisplayed);
    }

}

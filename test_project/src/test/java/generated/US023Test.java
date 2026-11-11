package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalTime;

public class US023Test {

private NotificationService notificationService;

    @BeforeEach
    public void setUp() {
        notificationService = new NotificationService();
    }

    @Test
    public void US023_DisplayReminderNotification_WhenReminderTimeIsSet() {
        LocalTime reminderTime = LocalTime.of(10, 0);
        notificationService.setReminderTime(reminderTime);
        assertTrue(notificationService.isReminderSet());
    }

    @Test
    public void US023_NotDisplayReminderNotification_WhenReminderTimeIsNotSet() {
        assertFalse(notificationService.isReminderSet());
    }

    @Test
    public void US023_DisplayReminderNotification_WhenReminderTimeIsChanged() {
        LocalTime initialReminderTime = LocalTime.of(10, 0);
        LocalTime newReminderTime = LocalTime.of(11, 0);
        notificationService.setReminderTime(initialReminderTime);
        notificationService.setReminderTime(newReminderTime);
        assertTrue(notificationService.isReminderSet());
    }

    @Test
    public void US023_NotDisplayReminderNotification_WhenReminderTimeIsDisabled() {
        LocalTime reminderTime = LocalTime.of(10, 0);
        notificationService.setReminderTime(reminderTime);
        notificationService.disableReminder();
        assertFalse(notificationService.isReminderSet());
    }

    @Test
    public void US023_DisplayReminderNotification_WhenReminderTimeIsSetToCurrentTime() {
        LocalTime currentTime = LocalTime.now();
        notificationService.setReminderTime(currentTime);
        assertTrue(notificationService.isReminderSet());
    }

}

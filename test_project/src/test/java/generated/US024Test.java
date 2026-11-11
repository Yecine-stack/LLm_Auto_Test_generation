package generated;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class US024Test {

private NotificationSettings notificationSettings;

    @BeforeEach
    public void setUp() {
        notificationSettings = new NotificationSettings();
    }

    @Test
    public void US024_DisableNotifications_InitialValue() {
        assertFalse(notificationSettings.isNotificationsEnabled());
    }

    @Test
    public void US024_DisableNotifications_EnableAndDisable() {
        notificationSettings.enableNotifications();
        assertTrue(notificationSettings.isNotificationsEnabled());
        notificationSettings.disableNotifications();
        assertFalse(notificationSettings.isNotificationsEnabled());
    }

    @Test
    public void US024_DisableNotifications_EnableTwice() {
        notificationSettings.enableNotifications();
        assertTrue(notificationSettings.isNotificationsEnabled());
        notificationSettings.enableNotifications();
        assertTrue(notificationSettings.isNotificationsEnabled());
    }

    @Test
    public void US024_DisableNotifications_DisableTwice() {
        notificationSettings.enableNotifications();
        assertTrue(notificationSettings.isNotificationsEnabled());
        notificationSettings.disableNotifications();
        assertFalse(notificationSettings.isNotificationsEnabled());
        notificationSettings.disableNotifications();
        assertFalse(notificationSettings.isNotificationsEnabled());
    }

    @Test
    public void US024_DisableNotifications_EnableAfterDisable() {
        notificationSettings.enableNotifications();
        assertTrue(notificationSettings.isNotificationsEnabled());
        notificationSettings.disableNotifications();
        assertFalse(notificationSettings.isNotificationsEnabled());
        notificationSettings.enableNotifications();
        assertTrue(notificationSettings.isNotificationsEnabled());
    }

}

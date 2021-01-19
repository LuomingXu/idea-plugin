package com.github.luomingxu.idea.util;

import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.Notifications;
import com.intellij.openapi.ui.MessageType;

public class NotifyUtil {

    private static final NotificationGroup notificationGroup =
            NotificationGroupManager.getInstance().getNotificationGroup("Luo Notification Group");

    public static void info(String msg) {
        Notifications.Bus.notify(notificationGroup.createNotification(msg, MessageType.INFO));
    }

    public static void err(String msg) {
        Notifications.Bus.notify(notificationGroup.createNotification(msg, MessageType.ERROR));
    }
}

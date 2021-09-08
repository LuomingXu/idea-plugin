package com.github.luomingxu.idea.util

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.Notifications
import com.intellij.openapi.ui.MessageType

object NotifyUtil {
    private val notificationGroup =
        NotificationGroupManager.getInstance()?.getNotificationGroup("Luo Notification Group")


    fun info(msg: String?) {
        msg?.let {
            if (notificationGroup != null) {
                Notifications.Bus.notify(notificationGroup.createNotification(it, MessageType.INFO))
            }
        }
    }

    fun err(msg: String?) {
        msg?.let {
            if (notificationGroup != null) {
                Notifications.Bus.notify(notificationGroup.createNotification(it, MessageType.ERROR))
            }
        }
    }
}
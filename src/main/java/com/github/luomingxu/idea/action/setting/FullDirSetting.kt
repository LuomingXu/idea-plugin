package com.github.luomingxu.idea.action.setting

import com.github.luomingxu.idea.settings.IdeaEnhanceState
import com.github.luomingxu.idea.util.NotifyUtil

class FullDirSetting : SettingAction() {
    override fun set(settings: IdeaEnhanceState?) {
        settings?.let {
            it.`is` = !it.`is`
            NotifyUtil.info("Current setting is: ${it.`is`}")
        }
    }
}
